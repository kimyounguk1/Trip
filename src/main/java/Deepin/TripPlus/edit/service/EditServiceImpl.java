package Deepin.TripPlus.edit.service;

import Deepin.TripPlus.exception.CustomException;
import Deepin.TripPlus.exception.ErrorCode;
import Deepin.TripPlus.auth.jwt.JWTUtil;
import Deepin.TripPlus.edit.dto.InquireDto;
import Deepin.TripPlus.edit.dto.NoticeDtDto;
import Deepin.TripPlus.edit.dto.NoticeDto;
import Deepin.TripPlus.edit.dto.SubmitDto;
import Deepin.TripPlus.entity.Inquire;
import Deepin.TripPlus.entity.Notice;
import Deepin.TripPlus.entity.User;
import Deepin.TripPlus.repository.SpringDataJpaInquireRepository;
import Deepin.TripPlus.repository.SpringDataJpaNoticeRepository;
import Deepin.TripPlus.repository.SpringDataJpaUserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class EditServiceImpl implements EditService {

    private final SpringDataJpaNoticeRepository editRepository;
    private final SpringDataJpaUserRepository userRepository;
    private final SpringDataJpaInquireRepository inquireRepository;
    private final JWTUtil jwtUtil;

    @Override
    public List<NoticeDto> noticeProcess() {

        List<Notice> notices = editRepository.findAll();

        return notices.stream()
                .map(notice ->new NoticeDto(
                        notice.getId(),
                        notice.getTitle(),
                        notice.getNoticeType(),
                        notice.getCreatedDate().toString()

                ))
                .collect(Collectors.toList());

    }

    @Override
    public NoticeDtDto noticeDtProcess(Long id) {

        Notice notice = editRepository.findById(id).orElseThrow(()->new CustomException(ErrorCode.NON_EXIST_NOTICE));

        String title = notice.getTitle();
        String content = notice.getContent();
        String date = notice.getCreatedDate().toString();
        String noticeType = notice.getNoticeType();

        NoticeDtDto noticeDt = new NoticeDtDto();

        noticeDt.setTitle(title);
        noticeDt.setContent(content);
        noticeDt.setDate(date);
        noticeDt.setNoticeType(noticeType);

        return noticeDt;
    }

    @Override
    public List<InquireDto> inquireProcess(HttpServletRequest request) {

        String email = emailProcess(request);

        User data = userRepository.findByEmailWithInquires(email);

        List<Inquire> inquires = data.getInquires();

        return inquires.stream()
                .map(inquire ->new InquireDto(
                        inquire.getId(),
                        inquire.getTitle(),
                        inquire.getContent(),
                        inquire.getCreatedDate().toString(),
                        inquire.isAnswered(),
                        inquire.getAnswer(),
                        inquire.getAnsweredDate().toString()
                ))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public User inquireSubmitProcess(HttpServletRequest request, SubmitDto submitDto) {

        User user = extracted(request);

        Inquire inquire = new Inquire();
        inquire.setTitle(submitDto.getTitle());
        inquire.setContent(submitDto.getContent());
        inquire.setAnswered(false);

        user.addInquire(inquire);

        inquireRepository.save(inquire);

        return user;

    }

    @Override
    @Transactional
    public User modifyUserProcess(HttpServletRequest request, String userTripType) {

        User user = extracted(request);

        user.setTripType(userTripType);

        return user;
    }

    @Override
    @Transactional
    public void accountDeleteProcess(HttpServletRequest request) {

        User user = extracted(request);

        userRepository.delete(user);

    }

    private User extracted(HttpServletRequest request) {
        String email = emailProcess(request);
        User user = userRepository.findByEmail(email);
        return user;
    }

    public String emailProcess(HttpServletRequest request){
        String authorization = request.getHeader("Authorization");

        String token = authorization.split(" ")[1];
        if (jwtUtil.isExpired(token)) {

            log.info("token expired");

            //토큰 만료
            throw new CustomException(ErrorCode.EXPIRED_TOKEN);
        }
        return jwtUtil.getEmail(token);
    }


}

package Deepin.TripPlus.admin.service;

import Deepin.TripPlus.admin.dto.ContentTrainDto;
import Deepin.TripPlus.admin.dto.CourseDetailDto;
import Deepin.TripPlus.admin.dto.InquireDto;
import Deepin.TripPlus.admin.dto.UserDto;
import Deepin.TripPlus.auth.dto.*;
import Deepin.TripPlus.edit.dto.InquireSaveDto;
import Deepin.TripPlus.edit.dto.NoticeSaveDto;
import Deepin.TripPlus.entity.*;
import Deepin.TripPlus.exception.CustomException;
import Deepin.TripPlus.exception.ErrorCode;
import Deepin.TripPlus.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final SpringDataJpaUserRepository userRepository;
    private final SpringDataJpaCourseDtRepository courseDtRepository;
    private final SpringDataJpaInquireRepository inquireRepository;
    private final SpringDataJpaNoticeRepository noticeRepository;
    private final SpringDataJpaModelRepository modelRepository;
    private final InquireRepository inquireDao;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public List<UserDto> usersProcess() {
        List<User> users = userRepository.findAll();
        List<UserDto> value = users.stream()
                .map(user -> new UserDto(
                        user.getId(),
                        user.getName(),
                        user.getEmail(),
                        user.getGender(),
                        user.getBirth().toString(),
                        user.isSuspended(),
                        user.getCreatedDate().toString()

                ))
                .collect(Collectors.toList());

        return value;

    }

    @Override
    public List<CourseDetailDto> courseDetailsProcess() {
        List<CourseDetail> courseDts = courseDtRepository.findAll();

        List<CourseDetailDto> value = courseDts.stream()
                .map(courseDt -> new CourseDetailDto(
                        courseDt.getId(),
                        courseDt.getPlaceName(),
                        courseDt.getPlaceAddress(),
                        courseDt.getPlaceType()
                ))
                .collect(Collectors.toList());
        return value;
    }

    @Override
    public List<InquireDto> inquiresProcess() {
        List<Inquire> inquires = inquireRepository.findAllWithUser();
        List<InquireDto> value = inquires.stream()
                .map(inquire -> new InquireDto(
                        inquire.getId(),
                        inquire.getUser().getId(),
                        inquire.getTitle(),
                        inquire.getContent(),
                        inquire.getCreatedDate().toString(),
                        inquire.getAnswer()
                ))
                .collect(Collectors.toList());

        return value;
    }

    @Override
    public AdminInquireDto adminInquireProcess(Long inquireId) {

        Inquire inquire = inquireRepository.findById(inquireId)
                .orElseThrow(()-> new CustomException(ErrorCode.NON_EXIST_INQUIRE));

        AdminInquireDto adminInquireDto = AdminInquireDto.from(inquire);

        return adminInquireDto;

    }

    @Override
    public List<Notice> noticesProcess() {

        List<Notice> notices = noticeRepository.findAll();

        return notices;
    }

    @Override
    public AdminNoticeDto noticeProcess(Long noticeId) {

        Notice notice = noticeRepository.findById(noticeId)
                .orElseThrow(()-> new CustomException(ErrorCode.NON_EXIST_NOTICE));

        AdminNoticeDto noticeDto = new AdminNoticeDto();

        noticeDto.setTitle(notice.getTitle());
        noticeDto.setContent(notice.getContent());
        noticeDto.setNoticeType(notice.getNoticeType());
        noticeDto.setCreateDate(notice.getCreatedDate().toString());

        return noticeDto;
    }

    @Override
    public AdminModelDto modelsProcess() {

        List<Model> surveyModels = modelRepository.findByModelType("survey");
        List<Model> recommendModels = modelRepository.findByModelType("recommend");

        AdminModelDto adminModelDto = new AdminModelDto();
        adminModelDto.setSurvey(surveyModels);
        adminModelDto.setRecommend(recommendModels);

        return adminModelDto;
    }

    @Override
    public List<UserDto> findUsersProcess(String username) {

        List<User> users = userRepository.findByNameContaining(username);

        List<UserDto> value = users.stream()
                .map(user -> new UserDto(
                        user.getId(),
                        user.getName(),
                        user.getEmail(),
                        user.getGender(),
                        user.getBirth().toString(),
                        user.isSuspended(),
                        user.getCreatedDate().toString()

                ))
                .collect(Collectors.toList());

        return value;

    }

    @Override
    public List<InquireDto> findInquiresProcess(FindInquireDto findInquireDto) {
        String title = findInquireDto.getTitle();
        String username = findInquireDto.getUsername();

        List<Inquire> inquires = inquireDao.findByTitleOrUserName(title, username);

        List<InquireDto> value = inquires.stream()
                .map(inquire -> new InquireDto(
                        inquire.getId(),
                        inquire.getUser().getId(),
                        inquire.getTitle(),
                        inquire.getContent(),
                        inquire.getCreatedDate().toString(),
                        inquire.getAnswer()
                ))
                .collect(Collectors.toList());

        return value;
    }

    @Override
    @Transactional
    public void inquireSaveProcess(Long inquireId, InquireSaveDto inquireSaveDto) {

        Inquire inquire = inquireRepository.findById(inquireId)
                .orElseThrow(()-> new CustomException(ErrorCode.NON_EXIST_INQUIRE));

        inquire.setAnswered(true);
        inquire.setAnswer(inquireSaveDto.getAnswer());
        inquire.setAnsweredDate(LocalDateTime.parse(inquireSaveDto.getAnswerDate()));

    }

    @Override
    @Transactional
    public void noticeSaveProcess(NoticeSaveDto noticeSaveDto) {

        Notice notice = new Notice();

        notice.setTitle(noticeSaveDto.getTitle());
        notice.setContent(noticeSaveDto.getContent());
        notice.setNoticeType(noticeSaveDto.getNoticeType());

        noticeRepository.save(notice);

    }

    @Override
    public List<Notice> findNoticeProcess(String title) {

        List<Notice> notices = noticeRepository.findByTitleContaining(title);

        return notices;
    }

    @Override
    @Transactional
    public User suspendUserProcess(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(()-> new CustomException(ErrorCode.NON_EXIST_USER));

        boolean suspended = user.isSuspended();

        user.setSuspended(!suspended);

        return user;
    }

    @Override
    @Transactional
    public Notice updateNoticeProcess(Long noticeId, AdminNoticeUpdateDto noticeUpdateDto) {

        Notice notice = noticeRepository.findById(noticeId)
                .orElseThrow(() -> new CustomException(ErrorCode.NON_EXIST_NOTICE));

        notice.setTitle(noticeUpdateDto.getTitle());
        notice.setContent(noticeUpdateDto.getContent());
        notice.setNoticeType(noticeUpdateDto.getNoticeType());

        return notice;
    }

    @Override
    @Transactional
    public void deleteUserProcess(Long userId) {

        userRepository.deleteById(userId);

    }

    @Override
    @Transactional
    public void deleteCourseDtProcess(Long courseDtId) {

        courseDtRepository.deleteById(courseDtId);

    }

    @Override
    @Transactional
    public void deleteNoticeProcess(Long noticeId) {

        noticeRepository.deleteById(noticeId);

    }

    @Override
    @Transactional
    public void registerProcess(RegisterDto registerDto) {
        String email = registerDto.getEmail();
        String password = registerDto.getPassword();
        String name = registerDto.getName();

        Boolean isExist = userRepository.existsByEmail(email);

        if(isExist) {
            throw new CustomException(ErrorCode.DUPLICATE_EMAIL);
        }

        User data = new User();
        data.setEmail(email);
        data.setPassword(bCryptPasswordEncoder.encode(password));
        data.setRole("ROLE_ADMIN");
        data.setName(name);
        data.setFirst(true);
        data.setSuspended(false);


        userRepository.save(data);
    }

    @Override
    @Transactional
    public void trainContentModelProcess(ContentTrainDto contentTrainDto) throws IOException, InterruptedException {
        String name = contentTrainDto.getName();
        int nEstimators = contentTrainDto.getNEstimators();
        Double learningRate = contentTrainDto.getLearningRate();
        int maxDepth = contentTrainDto.getMaxDepth();
        String information = contentTrainDto.getInformation();

        ProcessBuilder pb = new ProcessBuilder("python3", "모델파일.py",
                "--n_estimators", String.valueOf(nEstimators),
                "--learning_rate", String.valueOf(learningRate),
                "--max_depth", String.valueOf(maxDepth)
        );

        Process process = pb.start();

        int exitCode = process.waitFor();

        if (exitCode != 0) {
            throw new RuntimeException("모델 학습 실패: exitCode = " + exitCode);
        }

        Model model = new Model();
        model.setName(name);
        model.setNEstimators(nEstimators);
        model.setLearningRate(learningRate);
        model.setMaxDepth(maxDepth);
        model.setInformation(information);
        model.setModelType("CONTENT");

        modelRepository.save(model);

    }
}

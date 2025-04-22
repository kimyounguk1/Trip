package Deepin.TripPlus.admin.service;

import Deepin.TripPlus.auth.dto.*;
import Deepin.TripPlus.edit.dto.InquireSaveDto;
import Deepin.TripPlus.edit.dto.NoticeSaveDto;
import Deepin.TripPlus.entity.*;
import Deepin.TripPlus.exception.CustomException;
import Deepin.TripPlus.exception.ErrorCode;
import Deepin.TripPlus.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final SpringDataJpaUserRepository userRepository;
    private final SpringDataJpaCourseDtRepository courseDtRepository;
    private final SpringDataJpaInquireRepository inquireRepository;
    private final SpringDataJpaNoticeRepository noticeRepository;
    private final SpringDataJpaModelRepository modelRepository;
    private final InquireRepository inquireDao;

    @Override
    public List<User> usersProcess() {
        List<User> users = userRepository.findAll();
        return users;
    }

    @Override
    public List<CourseDetail> courseDetailsProcess() {
        List<CourseDetail> courseDts = courseDtRepository.findAll();
        return courseDts;
    }

    @Override
    public List<Inquire> inquiresProcess() {
        List<Inquire> inquires = inquireRepository.findAll();
        return inquires;
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
    public List<User> findUsersProcess(String username) {

        List<User> users = userRepository.findByNameContaining(username);

        return users;
    }

    @Override
    public List<Inquire> findInquiresProcess(FindInquireDto findInquireDto) {
        String title = findInquireDto.getTitle();
        String username = findInquireDto.getUsername();

        List<Inquire> inquires = inquireDao.findByTitleOrUserName(title, username);

        return inquires;
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
}

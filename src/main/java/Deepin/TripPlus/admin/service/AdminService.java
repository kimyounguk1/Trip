package Deepin.TripPlus.admin.service;

import Deepin.TripPlus.auth.dto.*;
import Deepin.TripPlus.edit.dto.InquireSaveDto;
import Deepin.TripPlus.edit.dto.NoticeSaveDto;
import Deepin.TripPlus.entity.*;

import java.util.List;

public interface AdminService {

    List<User> usersProcess();

    List<CourseDetail> courseDetailsProcess();

    List<Inquire> inquiresProcess();

    AdminInquireDto adminInquireProcess(Long inquireId);

    List<Notice> noticesProcess();

    AdminNoticeDto noticeProcess(Long noticeId);

    AdminModelDto modelsProcess();

    List<User> findUsersProcess(String username);

    List<Inquire> findInquiresProcess(FindInquireDto findInquireDto);

    void inquireSaveProcess(Long inquireId, InquireSaveDto inquireSaveDto);

    void noticeSaveProcess(NoticeSaveDto noticeSaveDto);

    List<Notice> findNoticeProcess(String title);

    User suspendUserProcess(Long userId);

    Notice updateNoticeProcess(Long noticeId, AdminNoticeUpdateDto noticeUpdateDto);

    void deleteUserProcess(Long userId);

    void deleteCourseDtProcess(Long courseDtId);

    void deleteNoticeProcess(Long noticeId);
}

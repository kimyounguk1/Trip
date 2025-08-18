package Deepin.TripPlus.admin.service;

import Deepin.TripPlus.admin.dto.*;
import Deepin.TripPlus.auth.dto.*;
import Deepin.TripPlus.edit.dto.InquireSaveDto;
import Deepin.TripPlus.edit.dto.NoticeSaveDto;
import Deepin.TripPlus.entity.*;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

public interface AdminService {

    List<UserDto> usersProcess();

    List<CourseDetailDto> courseDetailsProcess();

    List<InquireDto> inquiresProcess();

    AdminInquireDto adminInquireProcess(Long inquireId);

    List<Notice> noticesProcess();

    AdminNoticeDto noticeProcess(Long noticeId);

    AdminModelDto modelsProcess();

    List<UserDto> findUsersProcess(String username);

    List<InquireDto> findInquiresProcess(FindInquireDto findInquireDto);

    void inquireSaveProcess(Long inquireId, InquireSaveDto inquireSaveDto);

    void noticeSaveProcess(NoticeSaveDto noticeSaveDto);

    List<Notice> findNoticeProcess(String title);

    User suspendUserProcess(Long userId);

    Notice updateNoticeProcess(Long noticeId, AdminNoticeUpdateDto noticeUpdateDto);

    void deleteUserProcess(Long userId);

    void deleteCourseDtProcess(Long courseDtId);

    void deleteNoticeProcess(Long noticeId);

    void registerProcess(RegisterDto registerDto);

    void trainContentModelProcess(ContentTrainDto contentTrainDto) throws IOException, InterruptedException;

    void trainCooperationModelProcess(CooperationTrainDto cooperationTrainDto) throws IOException, InterruptedException;

    void applyModelProcess(ModelApplyDto modelApplyDto);
}

package Deepin.TripPlus.edit.service;

import Deepin.TripPlus.edit.dto.*;
import Deepin.TripPlus.entity.User;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface EditService {

    List<NoticeDto> noticeProcess();

    NoticeDtDto noticeDtProcess(Long id);

    List<InquireDto> inquireProcess(HttpServletRequest request);

    User inquireSubmitProcess(HttpServletRequest request, SubmitDto submitDto);

    User modifyUserProcess(HttpServletRequest request, ModifyDto modifyDto);

    void accountDeleteProcess(HttpServletRequest request);





}

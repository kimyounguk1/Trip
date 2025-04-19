package Deepin.TripPlus.edit.service;

import Deepin.TripPlus.edit.dto.InquireDto;
import Deepin.TripPlus.edit.dto.NoticeDtDto;
import Deepin.TripPlus.edit.dto.NoticeDto;
import Deepin.TripPlus.edit.dto.SubmitDto;
import Deepin.TripPlus.entity.User;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface EditService {

    List<NoticeDto> noticeProcess();

    NoticeDtDto noticeDtProcess(Long id);

    List<InquireDto> inquireProcess(HttpServletRequest request);

    User inquireSubmitProcess(HttpServletRequest request, SubmitDto submitDto);

    User modifyUserProcess(HttpServletRequest request, String userTripType);

    void accountDeleteProcess(HttpServletRequest request);





}

package Deepin.TripPlus.edit.service;

import Deepin.TripPlus.edit.dto.*;
import Deepin.TripPlus.entity.Notice;
import Deepin.TripPlus.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EditService {

    List<NoticeDto> noticeProcess();

    Page<NoticeDto> noticeProcess(Pageable pageable);

    NoticeDtDto noticeDtProcess(Long id);

    List<InquireDto> inquireProcess(HttpServletRequest request);

    User inquireSubmitProcess(HttpServletRequest request, SubmitDto submitDto);

    User modifyUserProcess(HttpServletRequest request, ModifyDto modifyDto);

    void accountDeleteProcess(HttpServletRequest request);

//    Object noticeProcess(Long lastId, int size);
}

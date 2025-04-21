package Deepin.TripPlus.edit.controller;

import Deepin.TripPlus.commonDto.ApiResponse;
import Deepin.TripPlus.edit.dto.InquireDto;
import Deepin.TripPlus.edit.dto.NoticeDtDto;
import Deepin.TripPlus.edit.dto.NoticeDto;
import Deepin.TripPlus.edit.dto.SubmitDto;
import Deepin.TripPlus.edit.service.EditService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/edit")
@RequiredArgsConstructor
public class EditController {

    private final EditService editService;

    @GetMapping("/notice")
    public ResponseEntity<ApiResponse<?>> noticePage(){

        List<NoticeDto> notice = editService.noticeProcess();

        return ResponseEntity.ok(ApiResponse.success(notice));
    }

    @GetMapping("/notice/{noticeId}")
    public ResponseEntity<ApiResponse<?>> noticeDtPage(@PathVariable("noticeId") Long noticeId){

        NoticeDtDto noticeDtDto = editService.noticeDtProcess(noticeId);

        return ResponseEntity.ok(ApiResponse.success(noticeDtDto));
    }

    @GetMapping("/inquire")
    public ResponseEntity<ApiResponse<?>> inquirePage(HttpServletRequest request){

        List<InquireDto> inquireDtos = editService.inquireProcess(request);

        return ResponseEntity.ok(ApiResponse.success(inquireDtos));
    }

    @PostMapping("/inquire/submit")
    public ResponseEntity<ApiResponse<?>> inquireSubmitProcess(HttpServletRequest request, @RequestBody SubmitDto submitDto){

        editService.inquireSubmitProcess(request, submitDto);

        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @PutMapping("/modifyUser")
    public ResponseEntity<ApiResponse<?>> modifyUserProcess(HttpServletRequest request, @RequestParam("userTripType") String userTripType){

        editService.modifyUserProcess(request, userTripType);

        return ResponseEntity.ok(ApiResponse.success(null));

    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse<?>> AccountDeleteProcess(HttpServletRequest request){

        editService.accountDeleteProcess(request);

        return ResponseEntity.ok(ApiResponse.success(null));
    }

}

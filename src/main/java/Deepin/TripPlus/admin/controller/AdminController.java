package Deepin.TripPlus.admin.controller;

import Deepin.TripPlus.admin.dto.*;
import Deepin.TripPlus.admin.service.AdminService;
import Deepin.TripPlus.auth.dto.*;
import Deepin.TripPlus.commonDto.ApiResponse;
import Deepin.TripPlus.edit.dto.InquireSaveDto;
import Deepin.TripPlus.edit.dto.NoticeSaveDto;
import Deepin.TripPlus.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/dashboard")
    public String dashboard(){
        return "dashboard";
    }

    @GetMapping("/users")
    public ResponseEntity<ApiResponse<?>> usersPage(){

        List<UserDto> users = adminService.usersProcess();

        return ResponseEntity.ok(ApiResponse.success(users));
    }

    @GetMapping("/courseDetails")  //dto로 반환
    public ResponseEntity<ApiResponse<?>> courseDetailsPage(){

        List<CourseDetailDto> courseDetails = adminService.courseDetailsProcess();

        return ResponseEntity.ok(ApiResponse.success(courseDetails));
    }

    @GetMapping("/inquires")   //dto로 반환
    public ResponseEntity<ApiResponse<?>> inquiresPage(){

        List<InquireDto> inquires = adminService.inquiresProcess();

        return ResponseEntity.ok(ApiResponse.success(inquires));
    }

    @GetMapping("/inquires/{inquireId}")
    public ResponseEntity<ApiResponse<?>> inquirePage(@PathVariable("inquireId") Long inquireId){

        AdminInquireDto adminInquireDto = adminService.adminInquireProcess(inquireId);

        return ResponseEntity.ok(ApiResponse.success(adminInquireDto));
    }

    @GetMapping("/notices")
    public ResponseEntity<ApiResponse<?>> noticesPage(){

        List<Notice> notices = adminService.noticesProcess();

        return ResponseEntity.ok(ApiResponse.success(notices));
    }

    @GetMapping("/notices/{noticeId}")
    public ResponseEntity<ApiResponse<?>> noticePage(@PathVariable("noticeId") Long noticeId){

        AdminNoticeDto adminNoticeDto = adminService.noticeProcess(noticeId);

        return ResponseEntity.ok(ApiResponse.success(adminNoticeDto));
    }

    @GetMapping("/models")
    public ResponseEntity<ApiResponse<?>> modelsPage(){

        AdminModelDto adminModelDto = adminService.modelsProcess();

        return ResponseEntity.ok(ApiResponse.success(adminModelDto));
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<?>> registerPage(@RequestBody RegisterDto registerDto){

        adminService.registerProcess(registerDto);

        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @PostMapping("/users/findUser")
    public ResponseEntity<ApiResponse<?>> findUser(@RequestParam("userName") String username){

        List<UserDto> users = adminService.findUsersProcess(username);

        return ResponseEntity.ok(ApiResponse.success(users));
    }

    @PostMapping("/inquires/findInquire")
    public ResponseEntity<ApiResponse<?>> findInquire(@RequestBody FindInquireDto findInquireDto){

        List<InquireDto> inquires = adminService.findInquiresProcess(findInquireDto);

        return ResponseEntity.ok(ApiResponse.success(inquires));
    }

    @PostMapping("/inquires/{inquireId}")
    public ResponseEntity<ApiResponse<?>> inquireSaveProcess(@PathVariable("inquireId") Long inquireId
    , @RequestBody InquireSaveDto inquireSaveDto){

        adminService.inquireSaveProcess(inquireId, inquireSaveDto);

        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @PostMapping("/notices/save")
    public ResponseEntity<ApiResponse<?>> saveNoticeProcess(@RequestBody NoticeSaveDto noticeSaveDto){

        adminService.noticeSaveProcess(noticeSaveDto);

        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @PostMapping("/notices/findNotice")
    public ResponseEntity<ApiResponse<?>> findNoticeProcess(@RequestParam("title") String title){

        List<Notice> notices = adminService.findNoticeProcess(title);

        return ResponseEntity.ok(ApiResponse.success(notices));
    }

    @PostMapping("/models/apply")
    public ResponseEntity<ApiResponse<?>> applyModelProcess(@RequestBody ModelApplyDto modelApplyDto){

        adminService.applyModelProcess(modelApplyDto);

        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @PostMapping("/models/content/train")
    public ResponseEntity<ApiResponse<?>> trainContentModelProcess(@RequestBody ContentTrainDto contentTrainDto) throws IOException, InterruptedException {

        adminService.trainContentModelProcess(contentTrainDto);

        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @PostMapping("/models/cooperation/train")
    public ResponseEntity<ApiResponse<?>> trainModelProcess(@RequestBody CooperationTrainDto cooperationTrainDto) throws IOException, InterruptedException {

        adminService.trainCooperationModelProcess(cooperationTrainDto);

        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @PutMapping("/users/deactive/{userId}")
    public ResponseEntity<ApiResponse<?>> SuspendUserProcess(@PathVariable("userId") Long userId){

        User user = adminService.suspendUserProcess(userId);

        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @PutMapping("/notices/update/{noticeId}")
    public ResponseEntity<ApiResponse<?>> updateNoticeProcess(@PathVariable("noticeId") Long noticeId
    , @RequestBody AdminNoticeUpdateDto adminNoticeUpdateDto){

        adminService.updateNoticeProcess(noticeId, adminNoticeUpdateDto);

        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @DeleteMapping("/users/delete/{userId}")
    public ResponseEntity<ApiResponse<?>> deleteUserProcess(@PathVariable("userId") Long userId){

        adminService.deleteUserProcess(userId);

        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @DeleteMapping("/courses/delete/{courseDtId}")
    public ResponseEntity<ApiResponse<?>> deleteCourseProcess(@PathVariable("courseDtId") Long courseDtId){

        adminService.deleteCourseDtProcess(courseDtId);

        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @DeleteMapping("/notices/delete/{noticeId}")
    public ResponseEntity<ApiResponse<?>> deleteNoticeProcess(@PathVariable("noticeId") Long noticeId){

        adminService.deleteNoticeProcess(noticeId);

        return ResponseEntity.ok(ApiResponse.success(null));
    }
}

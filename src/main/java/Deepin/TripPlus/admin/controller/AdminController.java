package Deepin.TripPlus.admin.controller;

import Deepin.TripPlus.admin.service.AdminService;
import Deepin.TripPlus.commenDto.ApiResponse;
import Deepin.TripPlus.entity.CourseDetail;
import Deepin.TripPlus.entity.Inquire;
import Deepin.TripPlus.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

        List<User> users = adminService.UsersProcess();

        return ResponseEntity.ok(ApiResponse.success(users));
    }

    @GetMapping("/courseDetails")
    public ResponseEntity<ApiResponse<?>> courseDetailsPage(){

        List<CourseDetail> courseDetails = adminService.CourseDetailsProcess();

        return ResponseEntity.ok(ApiResponse.success(courseDetails));
    }

    @GetMapping("/inquires")
    public ResponseEntity<ApiResponse<?>> inquiresPage(){

        List<Inquire> inquires = adminService.InquiresProcess();

        return ResponseEntity.ok(ApiResponse.success(inquires));
    }

    @GetMapping("/inquires/{inquireId}")
    public String inquirePage(@PathVariable("inquireId") int inquireId){
        return "inquirePage";
    }

    @GetMapping("/notices")
    public String noticesPage(){
        return "noticesPage";
    }

    @GetMapping("/notices/{noticeId}")
    public String noticeUpdatePage(@PathVariable("noticeId") int noticeId){
        return "noticeUpdatePage";
    }

    @GetMapping("/models")
    public String modelsPage(){
        return "modelsPage";
    }

    @PostMapping("/users/findUser")
    public String findUser(){
        return "findUser";
    }

    @PostMapping("/inquires/findInquire")
    public String findInquire(){
        return "findInquire";
    }

    @PostMapping("/inquires/{inquireId}")
    public String inquireSaveProcess(@PathVariable("inquireId") int inquireId){
        return "inquireSaveProcess";
    }

    @PostMapping("/notices/save")
    public String saveNoticeProcess(){
        return "saveNotice";
    }

    @PostMapping("/notices/findNotice")
    public String findNoticeProcess(){
        return "findNotice";
    }

    @PostMapping("/models/apply")
    public String applyModelProcess(){
        return "applyModel";
    }

    @PostMapping("/models/train")
    public String trainModelProcess(){
        return "trainModel";
    }

    @PutMapping("/users/deactive/{userId}")
    public String deactiveUserProcess(@PathVariable("usreId") int usreId){
        return "deactiveUser";
    }

    @PutMapping("/notices/update/{noticeId}")
    public String updateNoticeProcess(@PathVariable("noticeId") int noticeId){
        return "updateNotice";
    }

    @DeleteMapping("/users/delete/{userId}")
    public String deleteUserProcess(@PathVariable("userId") int userId){
        return "deleteUser";
    }

    @DeleteMapping("/courses/delete/{courseDtId}")
    public String deleteCourseProcess(@PathVariable("courseDtId") int courseDtId){
        return "deleteCourse";
    }

    @DeleteMapping("/notices/delete/{noticeId}")
    public String deleteNoticeProcess(@PathVariable("noticeId") int noticeId){
        return "deleteNotice";
    }
}

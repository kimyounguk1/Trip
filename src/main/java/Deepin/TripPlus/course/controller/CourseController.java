package Deepin.TripPlus.course.controller;

import Deepin.TripPlus.auth.dto.CourseDto;
import Deepin.TripPlus.course.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/course")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @GetMapping("")
    public String coursePage(){
        return "Course Page";
    }

    @GetMapping("/dt")
    public String courseDtPage(){
        return "Course DT Page";
    }

    @PostMapping("/survey")
    public String courseSurveyProcess(){
        return "Course Survey";
    }

    @PostMapping("/save")
    public String courseSaveProcess(){
        return "Course Save";
    }

    @PostMapping("/recommend")
    public String courseRecommendProcess(){
        return "Course Recommend";
    }

    // ✅ 여행 코스 리스트 조회 기능 추가
    @GetMapping("/list")
    public ResponseEntity<Map<String, Object>> getCourses(@RequestParam Long userId) {
        List<CourseDto> courseList = courseService.getCoursesByUserId(userId);

        Map<String, Object> response = new HashMap<>();
        response.put("resultCode", 200);
        response.put("resultMessage", "Success");
        response.put("data", courseList);

        return ResponseEntity.ok(response);
    }
}

package Deepin.TripPlus.course.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/course")
public class CourseController {

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

}

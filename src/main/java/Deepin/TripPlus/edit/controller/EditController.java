package Deepin.TripPlus.edit.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/edit")
public class EditController {

    @GetMapping("/notice")
    public String noticePage(){
        return "notice Page";
    }

    @GetMapping("/notice/{noticeId}")
    public String noticePage(@PathVariable("noticeId") int noticeId){
        return "noticeId Page";
    }

    @GetMapping("/inquire")
    public String inquirePage(){
        return "inquire Page";
    }

    @PostMapping("/inquire/submit")
    public String inquireSubmitProcess(){
        return "inquire Submit Process";
    }

    @PutMapping("/modifyUser")
    public String modifyUserProcess(){
        return "modify User Process";
    }

    @DeleteMapping("/delete")
    public String AccountDeleteProcess(){
        return "delete Account Process";
    }

}

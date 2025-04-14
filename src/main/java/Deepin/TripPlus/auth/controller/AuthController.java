package Deepin.TripPlus.auth.controller;

import Deepin.TripPlus.auth.dto.LoginDto;
import Deepin.TripPlus.auth.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final LoginService loginService;

    @PostMapping("/register")
    public String registerProcess(@ModelAttribute LoginDto loginDto) {

        loginService.joinProcess(loginDto);

        return "ok";
    }

    @GetMapping("/logout")
    public String logoutProcess(){
        return "logout Process";
    }

    @GetMapping("/home")
    public String homePage(){
        return "home";
    }

}

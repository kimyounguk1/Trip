package Deepin.TripPlus.auth.controller;

import Deepin.TripPlus.commonDto.ApiResponse;
import Deepin.TripPlus.auth.dto.HomeDto;
import Deepin.TripPlus.auth.dto.OnboardingDto;
import Deepin.TripPlus.auth.dto.RegisterDto;
import Deepin.TripPlus.auth.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<?>> registerProcess(@ModelAttribute RegisterDto registerDto) {

        authService.registerProcess(registerDto);

        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @PostMapping("/onboarding")
    public ResponseEntity<ApiResponse<?>> onboardingProcess(@ModelAttribute OnboardingDto onboardingDto, HttpServletRequest request) {

        authService.onboardingProcess(onboardingDto, request);

        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @GetMapping("/logout") //프론트에서 header의 authentication을 삭제
    public ResponseEntity<ApiResponse<?>> logoutProcess(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        if(authorization == null) {
            return ResponseEntity.ok(ApiResponse.success(null));
        }else {
            return ResponseEntity.status(404).body(ApiResponse.error(404,"Logout Failed"));
        }
    }

    @GetMapping("/home")
    public ResponseEntity<ApiResponse<?>> homePage(HttpServletRequest request){
        HomeDto homeDto = authService.homeProcess(request);

        return ResponseEntity.ok(ApiResponse.success(homeDto));
    }

}

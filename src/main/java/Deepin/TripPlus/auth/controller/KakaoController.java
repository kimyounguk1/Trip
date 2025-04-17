package Deepin.TripPlus.auth.controller;

import Deepin.TripPlus.auth.dto.KakaoUser;
import Deepin.TripPlus.auth.dto.LoginResponse;
import Deepin.TripPlus.auth.jwt.JWTUtil;
import Deepin.TripPlus.auth.service.KakaoService;
import Deepin.TripPlus.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("/auth")
public class KakaoController {

    public KakaoController(KakaoService kakaoService, JWTUtil jwtUtil) {
        this.kakaoService = kakaoService;
        this.jwtUtil = jwtUtil;
    }

    private final KakaoService kakaoService;
    private final JWTUtil jwtUtil;


    @GetMapping("/kakao")
    public ResponseEntity<LoginResponse> kakaoLogin(@RequestParam String code, HttpServletRequest request, HttpServletResponse response) {
        try{
            // 현재 도메인 확인
            String currentDomain = request.getServerName();
            return ResponseEntity.ok(kakaoService.kakaoLogin(code, currentDomain, response));
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Item Not Found");
        }
    }
}

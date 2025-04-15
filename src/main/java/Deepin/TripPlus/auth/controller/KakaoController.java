package Deepin.TripPlus.auth.controller;

import Deepin.TripPlus.auth.jwt.JWTUtil;
import Deepin.TripPlus.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class KakaoController {

    private final JWTUtil jwtUtil;

    public KakaoController(JWTUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/oauth2/success")
    public ResponseEntity<?> oauth2Success(@AuthenticationPrincipal OAuth2User oAuth2User) {
        User user = (User) oAuth2User.getAttributes().get("user");

        String jwt = jwtUtil.createJwt(user.getEmail(), user.getRole(), 60 * 60 * 1000L);

        return ResponseEntity.ok().body(Map.of("token", jwt));
    }
}

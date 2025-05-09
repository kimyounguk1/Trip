package Deepin.TripPlus.auth.jwt;

import Deepin.TripPlus.redis.entity.RefreshEntity;
import Deepin.TripPlus.redis.repository.TokenRepository;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReissueController {

    private final JWTUtil jwtUtil;
    private final TokenRepository tokenRepository;

    public ReissueController(final JWTUtil jwtUtil, TokenRepository tokenRepository) {
        this.jwtUtil = jwtUtil;
        this.tokenRepository = tokenRepository;
    }

    //jwt에서 (만료등) 예외가 발생했을 때 프론트에서 여기로 연결해서 새로운 AccessToken 발급
    @PostMapping("/reissue")
    public ResponseEntity<?> reissue(HttpServletRequest request, HttpServletResponse response) {
        //refresh가 쿠키에 존재한다면
        String refresh = null;
        Cookie[] cookies = request.getCookies();
        for(Cookie cookie : cookies) {
            if(cookie.getName().equals("refresh")) {
                refresh = cookie.getValue();
            }
        }

        //쿠키가 없으면 bad request
        if(refresh == null) {
            return new ResponseEntity<>("refresh token null", HttpStatus.BAD_REQUEST);
        }

        //만료 되었을 때
        try{
            jwtUtil.isExpired(refresh);
        }catch(ExpiredJwtException e) {
            return new ResponseEntity<>("refresh token expired", HttpStatus.BAD_REQUEST);
        }

        String category = jwtUtil.getCategory(refresh);
        //refresh가 아닌 경우
        if(!category.equals("refresh")){
            return new ResponseEntity<>("refresh token invalid", HttpStatus.BAD_REQUEST);
        }

        //서버에 refresh토큰이 없으면 예외
        Boolean isExist = tokenRepository.existsByRefresh(refresh);

        if(isExist){
            return new ResponseEntity<>("invalid refresh token", HttpStatus.BAD_REQUEST);
        }

        String email = jwtUtil.getEmail(refresh);
        String role = jwtUtil.getRole(refresh);

        //새로운 Jwt생성
        String newAccess = jwtUtil.createJwt("access", email, role, 600000L);
        String newRefresh = jwtUtil.createJwt("refresh", email, role, 600000L);


        //이전 토큰 삭제 및 새로운 refreshToken저장
        tokenRepository.deleteByRefresh(refresh);
        tokenRepository.save(new RefreshEntity(email, newRefresh, 10000000L));

        //refresh로 요청이 들어왔을 때 새로운 refresh 쿠키 전달
        response.setHeader("Authorization", "Bearer " + newAccess);
        response.addCookie(createCookie("refresh", newRefresh));

        return new ResponseEntity<>(HttpStatus.OK);
    }

    private Cookie createCookie(String key, String value){
        Cookie cookie = new Cookie(key, value);
        //cookie.setPath("/");
        //cookie.setSecure(true);
        cookie.setMaxAge(24*60*60);
        cookie.setHttpOnly(true);
        return cookie;
    }
}

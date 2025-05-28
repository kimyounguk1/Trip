package Deepin.TripPlus.auth.jwt;

import Deepin.TripPlus.auth.dto.CustomUserDetails;
import Deepin.TripPlus.auth.dto.LoginRequest;
import Deepin.TripPlus.redis.entity.RefreshEntity;
import Deepin.TripPlus.redis.repository.TokenRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.UUID;


public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;
    private final TokenRepository tokenRepository;


    public LoginFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil, TokenRepository tokenRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.tokenRepository = tokenRepository;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        // 클라이언트 요청에서 email, password 추출 (username = email)
//        String email = obtainUsername(request); // 내부적으로 email을 의미
//        String password = obtainPassword(request);

        // JSON 요청 파싱
        ObjectMapper objectMapper = new ObjectMapper();
        LoginRequest loginRequest = null;
        try {
            loginRequest = objectMapper.readValue(request.getInputStream(), LoginRequest.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // email과 password 추출
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();
        // 인증 토큰 생성 (email, password 담기), DB와 비교되는 값
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(email, password, null);

        // AuthenticationManager를 통해 인증 수행 → UserDetailsService 호출됨
        return authenticationManager.authenticate(authToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) {
        // 인증 성공 시 사용자 정보 추출
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

        // 내부적으로 email을 반환하는 getUsername()
        String email = customUserDetails.getUsername();

        // 권한 정보 추출
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String role = auth.getAuthority();

        // JWT 생성
        String token = jwtUtil.createJwt("access", email, role, 60 * 60 * 10L * 1000); //10시간
        String refresh = jwtUtil.createJwt("refresh", email, role, 86400000L);

        //refresh토큰을 redis에 추가
        RefreshEntity refreshToken = new RefreshEntity(email, refresh, Long.parseLong("10000"));
        tokenRepository.save(refreshToken);

        // 헤더에 토큰 추가 (Bearer {token})
        response.addHeader("Authorization", "Bearer " + token);
//        response.setHeader("access", token);
        response.addCookie(createCookie("refresh", refresh));
        response.setStatus(HttpStatus.OK.value());
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
        // 실패 시 처리 로직 (필요시 커스텀)
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }

    private Cookie createCookie(String key, String value) {

        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(24*60*60);
        //cookie.setSecure(true); //https 통신
        cookie.setPath("/"); //쿠키범위
        cookie.setHttpOnly(true); //xss 공격 예방
        return cookie;
    }




}

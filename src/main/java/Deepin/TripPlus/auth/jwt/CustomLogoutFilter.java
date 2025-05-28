package Deepin.TripPlus.auth.jwt;

import Deepin.TripPlus.redis.repository.TokenRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Slf4j
public class CustomLogoutFilter extends GenericFilterBean {

    private final JWTUtil jwtUtil;
    private final TokenRepository tokenRepository;
    private final RedisTemplate<String, String> redisTemplate;

    public CustomLogoutFilter(JWTUtil jwtUtil, TokenRepository tokenRepository, RedisTemplate<String, String> redisTemplate) {
        this.jwtUtil = jwtUtil;
        this.tokenRepository = tokenRepository;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        doFilter((HttpServletRequest) servletRequest, (HttpServletResponse) servletResponse, filterChain);
    }

    private void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        String requestUri = request.getRequestURI();
        if(!requestUri.matches("^/auth/logout$")){
            filterChain.doFilter(request, response);
            return;
        }
        String requestMethod = request.getMethod();
        if(!requestMethod.equals("POST")){
            filterChain.doFilter(request, response);
            return;
        }

        String refresh = null;
        Cookie[] cookies = request.getCookies();
        for(Cookie cookie : cookies){
            if(cookie.getName().equals("refresh")){
                refresh = cookie.getValue();
                log.info("refreshToken={}",refresh);
            }
        }
        if(refresh == null){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        try{
            jwtUtil.isExpired(refresh);
        }catch(Exception e){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        String category = jwtUtil.getCategory(refresh);
        if (!category.equals("refresh")) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        boolean isExist = tokenRepository.existsByRefresh(refresh);
        log.info("존재여부{}",isExist);
        if(!isExist){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        tokenRepository.deleteByRefresh(refresh);

        String authorization = request.getHeader("Authorization");

        String token = authorization.split(" ")[1];

        //만료 시간
        Date expiration = jwtUtil.getExpiration(token);
        long now = System.currentTimeMillis();
        long expTime = expiration.getTime();
        long diff = (expTime - now)/1000;

        //redis에 블랙리스트 저장
        redisTemplate.opsForValue().set("blackList:"+token, "logout", diff, TimeUnit.SECONDS);

        Cookie cookie = new Cookie("refresh", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
        response.setStatus(HttpServletResponse.SC_OK);
    }
}

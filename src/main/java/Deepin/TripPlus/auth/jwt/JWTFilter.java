package Deepin.TripPlus.auth.jwt;

import Deepin.TripPlus.auth.dto.CustomUserDetails;
import Deepin.TripPlus.entity.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

@Slf4j
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;
    private final RedisTemplate<String, String> redisTemplate;

    public JWTFilter(JWTUtil jwtUtil, RedisTemplate<String, String> redisTemplate) {
        this.jwtUtil = jwtUtil;
        this.redisTemplate = redisTemplate;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authorization = request.getHeader("Authorization");

        if(authorization == null || !authorization.startsWith("Bearer ")) {

            //다음체인으로 연결
            filterChain.doFilter(request, response);

            //조건에 해당되면 메소드 종료(필수)
            return;

        }

        String token = authorization.split(" ")[1];

        if (redisTemplate.hasKey("blackList:"+token)){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

            return;
        }

        if (jwtUtil.isExpired(token)) {

            System.out.println("token expired");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            filterChain.doFilter(request, response);

            //조건이 해당되면 메소드 종료 (필수)
            return;
        }

        String category = jwtUtil.getCategory(token);

        if(!category.equals("access")){
            //body에 응답 작성
            PrintWriter writer = response.getWriter();
            writer.print("invalid access token");

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        //토큰에서 username과 role 획득
        String username = jwtUtil.getEmail(token);
        String role = jwtUtil.getRole(token);

        //userEntity를 생성하여 값 set
        User userEntity = new User();
        userEntity.setEmail(username);
        userEntity.setPassword("temppassword");
        userEntity.setRole(role);

        //UserDetails에 회원 정보 객체 담기
        CustomUserDetails customUserDetails = new CustomUserDetails(userEntity);

        //스프링 시큐리티 인증 토큰 생성
        Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
        //세션에 사용자 등록
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }
}

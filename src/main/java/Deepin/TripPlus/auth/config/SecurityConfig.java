package Deepin.TripPlus.auth.config;

import Deepin.TripPlus.auth.jwt.*;
import Deepin.TripPlus.auth.service.CustomOAuth2UserService;
import Deepin.TripPlus.redis.repository.TokenRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;
    private final JWTUtil jwtUtil;
    private final TokenRepository tokenRepository;
    private final RedisTemplate<String, String> redisTemplate;
    private final CustomOAuth2UserService customOAuth2UserService;
    // private final CustomSuccessHandler customSuccessHandler; // <-- 이제 생성자에서 주입받을 필요 없음

    public SecurityConfig(AuthenticationConfiguration authenticationConfiguration, JWTUtil jwtUtil, TokenRepository tokenRepository, RedisTemplate<String, String> redisTemplate, CustomOAuth2UserService customOAuth2UserService) { // <-- 생성자 파라미터에서 CustomSuccessHandler 제거
        this.jwtUtil = jwtUtil;
        this.authenticationConfiguration = authenticationConfiguration;
        this.tokenRepository = tokenRepository;
        this.redisTemplate = redisTemplate;
        this.customOAuth2UserService = customOAuth2UserService;
        // this.customSuccessHandler = customSuccessHandler; // <-- 제거
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // CustomSuccessHandler를 빈으로 등록하는 Bean 메서드 추가
    @Bean
    public CustomSuccessHandler customSuccessHandler(JWTUtil jwtUtil) { // JWTUtil은 Spring이 이미 빈으로 가지고 있을 것이므로 주입받음
        return new CustomSuccessHandler(jwtUtil);
    }

    @Bean
    public SecurityFilterChain fillerChain(HttpSecurity http) throws Exception {

        LoginFilter loginFilter = new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil, tokenRepository);
        loginFilter.setFilterProcessesUrl("/auth/login"); // 로그인 경로 변경!

        http
                .csrf((auth)->auth.disable());
        http
                .formLogin((auth)->auth.disable());
        http
                .httpBasic((auth)->auth.disable());
        http
                .authorizeHttpRequests((auth)-> auth
                        .requestMatchers(
                                "/actuator/**",
                                "/auth/**", "/login",
                                "/v3/api-docs/**",     // Swagger JSON 문서 경로
                                "/swagger-ui/**",      // Swagger UI 리소스 경로
                                "/swagger-ui.html",     // Swagger HTML 진입점
                                "/course/**",
                                "/error/**",
                                "/admin/register",
                                "/reissue"
                        ).permitAll()
                        .requestMatchers("/home", "/course/**", "/rating", "/courseDt", "/edit/**").hasAnyRole("CLIENT", "ADMIN")
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated());
        http
                .addFilterBefore(new JWTFilter(jwtUtil, redisTemplate), LoginFilter.class);
        http
                .addFilterAt(loginFilter, UsernamePasswordAuthenticationFilter.class);
        http
                .sessionManagement((session)->session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http
                .addFilterBefore(new CustomLogoutFilter(jwtUtil, tokenRepository, redisTemplate), LoginFilter.class);
        http
                .oauth2Login((oauth2) -> oauth2
                        .userInfoEndpoint((userInfoEndpointConfig) -> userInfoEndpointConfig
                                .userService(customOAuth2UserService))
                        .successHandler(customSuccessHandler(jwtUtil)) // <-- 여기서 직접 생성한 빈을 사용
                );


        return http.build();
    }
}
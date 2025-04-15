package Deepin.TripPlus.auth.config;

import Deepin.TripPlus.auth.jwt.JWTFilter;
import Deepin.TripPlus.auth.jwt.JWTUtil;
import Deepin.TripPlus.auth.jwt.LoginFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;
    private final JWTUtil jwtUtil;

    public SecurityConfig(AuthenticationConfiguration authenticationConfiguration, JWTUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
        this.authenticationConfiguration = authenticationConfiguration;
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain fillerChain(HttpSecurity http) throws Exception {

        LoginFilter loginFilter = new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil);
        loginFilter.setFilterProcessesUrl("/auth/login"); // 로그인 경로 변경!

        http
                .csrf((auth)->auth.disable());
        http
                .formLogin((auth)->auth.disable());
        http
                .httpBasic((auth)->auth.disable());
        http
                .oauth2Login(oauth2 -> oauth2
                        .defaultSuccessUrl("/auth/oauth2/success", true)  // 로그인 성공 후 리다이렉트 URL
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(customOAuth2UserService())  // 커스텀 OAuth2UserService 설정
                        )
                )
                .authorizeHttpRequests((auth)-> auth
                        .requestMatchers(
                                "/auth/**", "/login",  // 너가 원래 열어둔 경로
                                "/v3/api-docs/**",     // Swagger JSON 문서 경로
                                "/swagger-ui/**",      // Swagger UI 리소스 경로
                                "/swagger-ui.html"     // Swagger HTML 진입점
                        ).permitAll()
                        .requestMatchers("/home", "/course/**", "/edit/**", "/rating", "/courseDt").hasRole("CLIENT")
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated());
        http
                .addFilterBefore(new JWTFilter(jwtUtil), LoginFilter.class);
        http
                .addFilterAt(loginFilter, UsernamePasswordAuthenticationFilter.class);
        http
                .sessionManagement((session)->session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
}

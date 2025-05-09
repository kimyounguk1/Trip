package Deepin.TripPlus.auth.config;

import Deepin.TripPlus.auth.jwt.CustomLogoutFilter;
import Deepin.TripPlus.auth.jwt.JWTFilter;
import Deepin.TripPlus.auth.jwt.JWTUtil;
import Deepin.TripPlus.auth.jwt.LoginFilter;
import Deepin.TripPlus.redis.repository.TokenRepository;
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
import org.springframework.security.web.authentication.logout.LogoutFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;
    private final JWTUtil jwtUtil;
    private final TokenRepository tokenRepository;

    public SecurityConfig(AuthenticationConfiguration authenticationConfiguration, JWTUtil jwtUtil, TokenRepository tokenRepository) {
        this.jwtUtil = jwtUtil;
        this.authenticationConfiguration = authenticationConfiguration;
        this.tokenRepository = tokenRepository;
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
                                "/auth/**", "/login",
                                "/v3/api-docs/**",     // Swagger JSON 문서 경로
                                "/swagger-ui/**",      // Swagger UI 리소스 경로
                                "/swagger-ui.html",     // Swagger HTML 진입점
                                "/course/**",
                                "/admin/register",
                                "/reissue"
                        ).permitAll()
                        .requestMatchers("/home", "/course/**", "/rating", "/courseDt", "/edit/**").hasAnyRole("CLIENT", "ADMIN")
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated());
        http
                .addFilterBefore(new JWTFilter(jwtUtil), LoginFilter.class);
        http
                .addFilterAt(loginFilter, UsernamePasswordAuthenticationFilter.class);
        http
                .sessionManagement((session)->session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http
                .addFilterBefore(new CustomLogoutFilter(jwtUtil, tokenRepository), LoginFilter.class);


        return http.build();
    }
}

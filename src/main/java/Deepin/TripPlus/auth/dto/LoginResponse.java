package Deepin.TripPlus.auth.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginResponse {
    private String email;
    private AuthTokens token;

    public LoginResponse(Long id, String nickname, String email, AuthTokens token) {
        this.email = email;
        this.token = token;
    }

    public LoginResponse(String kakaoEmail, AuthTokens bearer) {
        this.email = kakaoEmail;
        this.token = bearer;
    }
}

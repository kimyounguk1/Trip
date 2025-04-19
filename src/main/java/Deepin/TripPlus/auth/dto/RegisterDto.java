package Deepin.TripPlus.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterDto {

    @NotBlank(message = "필수로 입력해야 합니다.")
    @Email(message = "올바른 이메일 형식이 아닙니다.")
    private String email;
    @NotBlank(message = "필수로 입력해야 합니다.")
    private String password;
    @NotBlank(message = "필수로 입력해야 합니다.")
    private String name;
}

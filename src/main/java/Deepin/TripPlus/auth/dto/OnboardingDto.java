package Deepin.TripPlus.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Getter
public class OnboardingDto {

    @NotBlank(message = "필수로 입력해야 합니다.")
    private String gender;

    @NotBlank(message = "필수로 입력해야 합니다.")
    private List<String> userTripType;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotBlank(message = "필수로 입력해야 합니다.")
    private Date birth;
}

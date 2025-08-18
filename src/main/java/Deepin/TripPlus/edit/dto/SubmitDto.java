package Deepin.TripPlus.edit.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class SubmitDto {

    @NotBlank(message = "필수로 입력해야 합니다.")
    private String title;

    @NotBlank(message = "필수로 입력해야 합니다.")
    private String content;

}

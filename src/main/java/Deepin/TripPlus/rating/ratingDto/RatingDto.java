package Deepin.TripPlus.rating.ratingDto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RatingDto {

    @NotBlank(message = "필수로 입력해야 합니다.")
    private Long courseId;

    @NotBlank(message = "필수로 입력해야 합니다.")
    private Double score;

}

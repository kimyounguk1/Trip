package Deepin.TripPlus.edit.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class InquireDto {

    private Long id;

    private String title;

    private String content;

    private String createdDate;

    private boolean isAnswered;

    private String answer;

    private String answerDate;
}

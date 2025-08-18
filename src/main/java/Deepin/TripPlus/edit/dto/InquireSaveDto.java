package Deepin.TripPlus.edit.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class InquireSaveDto {

    private Long inquireId;

    private String answer;

    private String answerDate;
}

package Deepin.TripPlus.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class InquireDto {

    private Long id;

    private Long userId;

    private String title;

    private String content;

    private String createDate;

    private String isAnswered;

}

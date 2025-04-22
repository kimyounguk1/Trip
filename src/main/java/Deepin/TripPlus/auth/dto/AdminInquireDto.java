package Deepin.TripPlus.auth.dto;

import Deepin.TripPlus.entity.Inquire;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdminInquireDto {

    private Long inquireId;

    private Long userId;

    private String title;

    private String content;

    private String createDate;

    private Boolean isAnswered;

    private String answer;

    private String answeredDate;

    public static AdminInquireDto from(Inquire inquire) {
        AdminInquireDto dto = new AdminInquireDto();
        dto.setInquireId(inquire.getId());
        dto.setUserId(inquire.getUser().getId());
        dto.setTitle(inquire.getTitle());
        dto.setContent(inquire.getContent());
        dto.setCreateDate(inquire.getCreatedDate().toString());
        dto.setAnswer(inquire.getAnswer());
        dto.setAnsweredDate(LocalDateTime.now().toString());
        return dto;
    }

}

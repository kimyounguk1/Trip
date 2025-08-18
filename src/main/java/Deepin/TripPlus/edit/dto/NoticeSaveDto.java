package Deepin.TripPlus.edit.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class NoticeSaveDto {

    private String title;

    private String content;

    private String noticeType;
}

package Deepin.TripPlus.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdminNoticeUpdateDto {

    private Long id;

    private String title;

    private String content;

    private String noticeType;

}

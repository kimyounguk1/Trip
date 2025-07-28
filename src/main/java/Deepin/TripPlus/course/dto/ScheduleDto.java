package Deepin.TripPlus.course.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScheduleDto {
    private String title;
    private double mapx;
    private double mapy;
    private String addr;
    private String tag;
}


package Deepin.TripPlus.course.dto;

import lombok.*;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContentDto {
    private String area_name;
    private Map<String, List<ScheduleDto>> schedule;
}



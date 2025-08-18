package Deepin.TripPlus.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourseDto {
    private Long courseId;
    private String title;
    private String area;
    private String tripType;
    private String startDate;
    private String endDate;
}
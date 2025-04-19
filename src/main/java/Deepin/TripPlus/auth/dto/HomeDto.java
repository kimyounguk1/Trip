package Deepin.TripPlus.auth.dto;

import Deepin.TripPlus.entity.CourseDetail;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class HomeDto {

    private Boolean isFirst;
    private List<CourseDto> course;

}

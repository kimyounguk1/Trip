package Deepin.TripPlus.auth.dto;

import Deepin.TripPlus.entity.CourseDetail;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class HomeDto {

    private Boolean isFirst;
    private List<CourseDto> course;

}

package Deepin.TripPlus.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class CourseDetailDto {

    private Long courseDetailId;

    private String placeName;

    private String placeAddress;

    private String placeType;
}

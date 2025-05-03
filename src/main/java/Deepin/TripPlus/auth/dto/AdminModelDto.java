package Deepin.TripPlus.auth.dto;

import Deepin.TripPlus.admin.dto.ContentModelDto;
import Deepin.TripPlus.admin.dto.CooperationModelDto;
import Deepin.TripPlus.entity.Model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdminModelDto {

    private List<ContentModelDto> CONTENT;

    private List<CooperationModelDto> COOPERATION;
}

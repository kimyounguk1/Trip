package Deepin.TripPlus.auth.dto;

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

    private List<Model> survey;

    private List<Model> recommend;
}

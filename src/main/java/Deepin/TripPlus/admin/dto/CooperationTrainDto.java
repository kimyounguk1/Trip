package Deepin.TripPlus.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter
@AllArgsConstructor
@NoArgsConstructor
public class CooperationTrainDto {

    private String name;

    private int nEstimators;

    private int maxDepth;

    private int minSamplesSplit;

    private String information;
}

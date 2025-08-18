package Deepin.TripPlus.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
public class ContentTrainDto {

    private String name;

    private int nEstimators;

    private Double learningRate;

    private int maxDepth;

    private String information;

}

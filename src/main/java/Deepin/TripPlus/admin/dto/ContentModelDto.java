package Deepin.TripPlus.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContentModelDto {

    private Long modelId;

    private String name;

    private String modelType;

    private String information;

    private int nEstimators;

    private int maxDepth;

    private Double learningRate;

    private Double rate;

    private String createDate;
}

package Deepin.TripPlus.course.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContentInput {

    private int gender;                 // 성별 (예: 1 = 남성, 2 = 여성)
    private int age;                   // 나이
    private String start_date;         // 여행 시작일 (yyyy-MM-dd)
    private String end_date;           // 여행 종료일 (yyyy-MM-dd)
    private int means_tp;              // 교통 수단 (예: 1 = 자가용, 2 = 대중교통 등)
    private int person;                // 인원 수
    private String place;              // 여행지 (예: "서울")
    private String travel_type;        // 여행 유형 (예: "1;3;4")
    private Map<String, Double> rain_rate; // 날짜별 강수 확률
}

package com.example.travel_system.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TourResponse {
    private Integer tourId;
    private String tourName;
    private Integer numberOfDay;
    private String startPlace;
    private String endPlace;
    private String image;
    private String tourIntroduce;
    private String tourSchedule;
    private String tourInclude;
    private String tourNonInclude;
    private Double rate;
    private Integer status;
    private String agentName;
    private List<DepartureResponse> departures;
}
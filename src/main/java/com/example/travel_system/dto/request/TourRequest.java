package com.example.travel_system.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TourRequest {
    private Integer travelAgentId;
    private String tourName;
    private Integer numberOfDay;
    private String startPlace;
    private String endPlace;
    private String image;
    private String tourIntroduce;
    private String tourSchedule;
    private String tourInclude;
    private String tourNonInclude;
}
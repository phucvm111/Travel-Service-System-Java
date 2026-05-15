package com.example.travel_system.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DepartureResponse {
    private Integer departureId;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer capacity;
    private Integer availableSeat;
    private Double adultPrice;
    private Double childPrice;
    private String status;
}
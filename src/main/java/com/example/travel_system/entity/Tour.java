package com.example.travel_system.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Table(name = "tour")
@Getter
@Setter
@NoArgsConstructor
public class Tour {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tour_id")
    private Integer tourId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "travel_agent_id")
    private TravelAgent travelAgent;

    @Column(name = "tour_name")
    private String tourName;

    @Column(name = "number_of_day")
    private Integer numberOfDay;

    @Column(name = "start_place")
    private String startPlace;

    @Column(name = "end_place")
    private String endPlace;

    @Column(name = "image")
    private String image;

    @Column(name = "tour_introduce", columnDefinition = "TEXT")
    private String tourIntroduce;

    @Column(name = "tour_schedule", columnDefinition = "TEXT")
    private String tourSchedule;

    @Column(name = "tour_include", columnDefinition = "TEXT")
    private String tourInclude;

    @Column(name = "tour_non_include", columnDefinition = "TEXT")
    private String tourNonInclude;

    @Column(name = "rate")
    private Double rate;

    @Column(name = "status")
    private Integer status;

    @OneToMany(mappedBy = "tour", fetch = FetchType.LAZY)
    private List<TourDeparture> departures;
}
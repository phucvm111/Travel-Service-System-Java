package com.example.travel_system.repository;

import com.example.travel_system.entity.TourDeparture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TourDepartureRepository extends JpaRepository<TourDeparture, Integer> {
    List<TourDeparture> findByTour_TourId(Integer tourId);
    List<TourDeparture> findByTour_TourIdAndStatus(Integer tourId, String status);
}
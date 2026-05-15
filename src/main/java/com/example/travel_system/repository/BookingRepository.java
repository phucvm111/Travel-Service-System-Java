package com.example.travel_system.repository;

import com.example.travel_system.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Integer> {
    List<Booking> findByUserUserId(Integer userId);
    List<Booking> findByDepartureDepartureId(Integer departureId);
}
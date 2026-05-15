package com.example.travel_system.repository;

import com.example.travel_system.entity.Tour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TourRepository extends JpaRepository<Tour, Integer> {

    // Tìm tour theo agent
    List<Tour> findByTravelAgent_TravelAgentId(Integer agentId);

    // Tìm tour đang active (status = 1)
    List<Tour> findByStatus(Integer status);

    // Tìm tour theo tên (search)
    List<Tour> findByTourNameContainingIgnoreCase(String keyword);

    // Tìm tour theo điểm đi
    List<Tour> findByStartPlaceContainingIgnoreCase(String startPlace);

    // Tìm tour theo số ngày
    List<Tour> findByNumberOfDay(Integer numberOfDay);

    // Query tùy chỉnh: tìm tour có departure còn chỗ
    @Query("""
        SELECT DISTINCT t FROM Tour t
        JOIN t.departures d
        WHERE d.availableSeat > 0
        AND d.status = 'active'
        AND t.status = 1
    """)
    List<Tour> findToursWithAvailableSeats();
}
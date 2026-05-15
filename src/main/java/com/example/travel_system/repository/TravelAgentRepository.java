package com.example.travel_system.repository;

import com.example.travel_system.entity.TravelAgent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface TravelAgentRepository extends JpaRepository<TravelAgent, Integer> {
    Optional<TravelAgent> findByUser_UserId(Integer userId);
}
package com.example.travel_system.repository;

import com.example.travel_system.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<AppUser, Integer> {
    Optional<AppUser> findByGmail(String gmail);
    boolean existsByGmail(String gmail);
}
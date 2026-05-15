package com.example.travel_system.controller;

import com.example.travel_system.entity.AppUser;
import com.example.travel_system.entity.Booking;
import com.example.travel_system.repository.BookingRepository;
import com.example.travel_system.repository.TourRepository;
import com.example.travel_system.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserRepository userRepository;
    private final TourRepository tourRepository;
    private final BookingRepository bookingRepository;

    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalUsers", userRepository.count());
        stats.put("totalTours", tourRepository.count());
        stats.put("totalBookings", bookingRepository.count());
        double revenue = bookingRepository.findAll()
                .stream()
                .filter(b -> b.getStatus() == 1)
                .mapToDouble(Booking::getTotalPrice)
                .sum();
        stats.put("totalRevenue", revenue);
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/users")
    public ResponseEntity<List<Map<String, Object>>> getAllUsers() {
        List<AppUser> users = userRepository.findAll();
        List<Map<String, Object>> result = users.stream().map(u -> {
            Map<String, Object> map = new HashMap<>();
            map.put("userId", u.getUserId());
            map.put("gmail", u.getGmail());
            map.put("firstName", u.getFirstName());
            map.put("lastName", u.getLastName());
            map.put("phone", u.getPhone());
            map.put("status", u.getStatus());
            try {
                map.put("roleName", u.getRole().getRoleName());
            } catch (Exception e) {
                map.put("roleName", "N/A");
            }
            return map;
        }).collect(java.util.stream.Collectors.toList());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/bookings")
    public ResponseEntity<List<Map<String, Object>>> getAllBookings() {
        List<Booking> bookings = bookingRepository.findAll();
        List<Map<String, Object>> result = bookings.stream().map(b -> {
            Map<String, Object> map = new HashMap<>();
            map.put("bookId", b.getBookId());
            map.put("bookCode", b.getBookCode());
            map.put("firstName", b.getFirstName());
            map.put("lastName", b.getLastName());
            map.put("totalPrice", b.getTotalPrice());
            map.put("status", b.getStatus());
            map.put("bookDate", b.getBookDate());
            map.put("numberAdult", b.getNumberAdult());
            map.put("numberChildren", b.getNumberChildren());
            try {
                map.put("tourName", b.getDeparture().getTour().getTourName());
            } catch (Exception e) {
                map.put("tourName", "N/A");
            }
            return map;
        }).collect(java.util.stream.Collectors.toList());
        return ResponseEntity.ok(result);
    }

    @PutMapping("/users/{userId}/toggle")
    public ResponseEntity<Map<String, Object>> toggleUserStatus(@PathVariable Integer userId) {
        AppUser user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setStatus(user.getStatus() == 1 ? 0 : 1);
        userRepository.save(user);
        Map<String, Object> res = new HashMap<>();
        res.put("userId", userId);
        res.put("status", user.getStatus());
        return ResponseEntity.ok(res);
    }
}
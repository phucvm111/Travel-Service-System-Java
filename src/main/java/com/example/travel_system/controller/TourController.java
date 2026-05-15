package com.example.travel_system.controller;

import com.example.travel_system.dto.request.TourRequest;
import com.example.travel_system.dto.response.TourResponse;
import com.example.travel_system.service.TourService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/tours")
@RequiredArgsConstructor
public class TourController {

    private final TourService tourService;

    // GET /api/tours — lấy tất cả tour (public)
    @GetMapping
    public ResponseEntity<List<TourResponse>> getAllTours() {
        return ResponseEntity.ok(tourService.getAllTours());
    }

    // GET /api/tours/{id} — lấy chi tiết tour (public)
    @GetMapping("/{id}")
    public ResponseEntity<TourResponse> getTourById(@PathVariable Integer id) {
        return ResponseEntity.ok(tourService.getTourById(id));
    }

    // GET /api/tours/search?keyword=xxx — tìm kiếm (public)
    @GetMapping("/search")
    public ResponseEntity<List<TourResponse>> searchTours(
            @RequestParam String keyword) {
        return ResponseEntity.ok(tourService.searchTours(keyword));
    }

    // GET /api/tours/agent/{agentId} — tour theo agent (public)
    @GetMapping("/agent/{agentId}")
    public ResponseEntity<List<TourResponse>> getToursByAgent(
            @PathVariable Integer agentId) {
        return ResponseEntity.ok(tourService.getToursByAgent(agentId));
    }

    // POST /api/tours — tạo tour (Agent only)
    @PostMapping
    public ResponseEntity<TourResponse> createTour(
            @RequestBody TourRequest request) {
        return ResponseEntity.ok(tourService.createTour(request));
    }

    // PUT /api/tours/{id} — cập nhật tour (Agent only)
    @PutMapping("/{id}")
    public ResponseEntity<TourResponse> updateTour(
            @PathVariable Integer id,
            @RequestBody TourRequest request) {
        return ResponseEntity.ok(tourService.updateTour(id, request));
    }

    // DELETE /api/tours/{id} — xóa tour (Agent only)
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTour(@PathVariable Integer id) {
        tourService.deleteTour(id);
        return ResponseEntity.ok("Xóa tour thành công!");
    }
}
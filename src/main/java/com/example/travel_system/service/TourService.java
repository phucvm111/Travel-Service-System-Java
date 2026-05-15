package com.example.travel_system.service;

import com.example.travel_system.dto.request.TourRequest;
import com.example.travel_system.dto.response.DepartureResponse;
import com.example.travel_system.dto.response.TourResponse;
import com.example.travel_system.entity.Tour;
import com.example.travel_system.entity.TravelAgent;
import com.example.travel_system.repository.TourDepartureRepository;
import com.example.travel_system.repository.TourRepository;
import com.example.travel_system.repository.TravelAgentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TourService {

    private final TourRepository tourRepository;
    private final TourDepartureRepository departureRepository;
    private final TravelAgentRepository agentRepository;

    // ===== Lấy tất cả tour =====
    public List<TourResponse> getAllTours() {
        return tourRepository.findByStatus(1)
                .stream()
                .map(this::toTourResponse)
                .collect(Collectors.toList());
    }

    // ===== Lấy tour theo ID =====
    public TourResponse getTourById(Integer id) {
        Tour tour = tourRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tour không tồn tại: " + id));
        return toTourResponse(tour);
    }

    // ===== Tìm kiếm tour theo tên =====
    public List<TourResponse> searchTours(String keyword) {
        return tourRepository.findByTourNameContainingIgnoreCase(keyword)
                .stream()
                .map(this::toTourResponse)
                .collect(Collectors.toList());
    }

    // ===== Lấy tour theo agent =====
    public List<TourResponse> getToursByAgent(Integer agentId) {
        return tourRepository.findByTravelAgent_TravelAgentId(agentId)
                .stream()
                .map(this::toTourResponse)
                .collect(Collectors.toList());
    }

    // ===== Tạo tour mới (Agent) =====
    public TourResponse createTour(TourRequest request) {
        TravelAgent agent = agentRepository.findById(request.getTravelAgentId())
                .orElseThrow(() -> new RuntimeException("Agent không tồn tại"));

        Tour tour = new Tour();
        tour.setTravelAgent(agent);
        tour.setTourName(request.getTourName());
        tour.setNumberOfDay(request.getNumberOfDay());
        tour.setStartPlace(request.getStartPlace());
        tour.setEndPlace(request.getEndPlace());
        tour.setImage(request.getImage());
        tour.setTourIntroduce(request.getTourIntroduce());
        tour.setTourSchedule(request.getTourSchedule());
        tour.setTourInclude(request.getTourInclude());
        tour.setTourNonInclude(request.getTourNonInclude());
        tour.setStatus(1);

        return toTourResponse(tourRepository.save(tour));
    }

    // ===== Cập nhật tour =====
    public TourResponse updateTour(Integer id, TourRequest request) {
        Tour tour = tourRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tour không tồn tại: " + id));

        tour.setTourName(request.getTourName());
        tour.setNumberOfDay(request.getNumberOfDay());
        tour.setStartPlace(request.getStartPlace());
        tour.setEndPlace(request.getEndPlace());
        tour.setImage(request.getImage());
        tour.setTourIntroduce(request.getTourIntroduce());
        tour.setTourSchedule(request.getTourSchedule());
        tour.setTourInclude(request.getTourInclude());
        tour.setTourNonInclude(request.getTourNonInclude());

        return toTourResponse(tourRepository.save(tour));
    }

    // ===== Xóa tour (soft delete) =====
    public void deleteTour(Integer id) {
        Tour tour = tourRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tour không tồn tại: " + id));
        tour.setStatus(0); // Soft delete
        tourRepository.save(tour);
    }

    // ===== Helper: Convert Entity → DTO =====
    private TourResponse toTourResponse(Tour tour) {
        List<DepartureResponse> departures = departureRepository
                .findByTour_TourId(tour.getTourId())
                .stream()
                .map(d -> new DepartureResponse(
                        d.getDepartureId(),
                        d.getStartDate(),
                        d.getEndDate(),
                        d.getCapacity(),
                        d.getAvailableSeat(),
                        d.getAdultPrice(),
                        d.getChildPrice(),
                        d.getStatus()
                ))
                .collect(Collectors.toList());

        return new TourResponse(
                tour.getTourId(),
                tour.getTourName(),
                tour.getNumberOfDay(),
                tour.getStartPlace(),
                tour.getEndPlace(),
                tour.getImage(),
                tour.getTourIntroduce(),
                tour.getTourSchedule(),
                tour.getTourInclude(),
                tour.getTourNonInclude(),
                tour.getRate(),
                tour.getStatus(),
                tour.getTravelAgent() != null
                        ? tour.getTravelAgent().getTravelAgentName()
                        : null,
                departures
        );
    }
}
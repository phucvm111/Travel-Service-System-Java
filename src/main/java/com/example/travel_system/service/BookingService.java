package com.example.travel_system.service;

import com.example.travel_system.dto.request.BookingRequest;
import com.example.travel_system.dto.response.BookingResponse;
import com.example.travel_system.entity.*;
import com.example.travel_system.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final TourDepartureRepository tourDepartureRepository;
    private final VoucherRepository voucherRepository;
    private final PaymentMethodRepository paymentMethodRepository;
    private final UserRepository userRepository;

    @Transactional
    public BookingResponse createBooking(BookingRequest request, String gmail) {
        // 1. Lấy user
        AppUser user = userRepository.findByGmail(gmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 2. Lấy departure
        TourDeparture departure = tourDepartureRepository.findById(request.getDepartureId())
                .orElseThrow(() -> new RuntimeException("Departure not found"));

        // 3. Kiểm tra còn chỗ không
        int totalPeople = request.getNumberAdult() + request.getNumberChildren();
        if (departure.getAvailableSeat() < totalPeople) {
            throw new RuntimeException("Not enough seats. Available: " + departure.getAvailableSeat());
        }

        // 4. Tính giá gốc
        double rawPrice = (departure.getAdultPrice() * request.getNumberAdult())
                + (departure.getChildPrice() * request.getNumberChildren());

        // 5. Áp voucher nếu có
        Voucher voucher = null;
        double totalPrice = rawPrice;

        if (request.getVoucherCode() != null && !request.getVoucherCode().isBlank()) {
            voucher = voucherRepository.findByVoucherCode(request.getVoucherCode())
                    .orElseThrow(() -> new RuntimeException("Voucher not found"));

            // Validate voucher
            LocalDate today = LocalDate.now();
            if (voucher.getStatus() != 1)
                throw new RuntimeException("Voucher is inactive");
            if (today.isBefore(voucher.getStartDate()) || today.isAfter(voucher.getEndDate()))
                throw new RuntimeException("Voucher expired");
            if (voucher.getQuantity() <= 0)
                throw new RuntimeException("Voucher out of stock");
            if (rawPrice < voucher.getMinDiscountAmount())
                throw new RuntimeException("Order total too low for this voucher. Minimum: " + voucher.getMinDiscountAmount());

            // Tính discount
            double discount = rawPrice * voucher.getPercentDiscount() / 100;
            discount = Math.min(discount, voucher.getMaxDiscountAmount());
            totalPrice = rawPrice - discount;

            // Giảm số lượng voucher
            voucher.setQuantity(voucher.getQuantity() - 1);
            voucherRepository.save(voucher);
        }

        // 6. Lấy payment method
        PaymentMethod paymentMethod = paymentMethodRepository.findById(request.getPaymentMethodId())
                .orElseThrow(() -> new RuntimeException("Payment method not found"));

        // 7. Tạo booking
        Booking booking = new Booking();
        booking.setUser(user);
        booking.setDeparture(departure);
        booking.setVoucher(voucher);
        booking.setPaymentMethod(paymentMethod);
        booking.setNumberAdult(request.getNumberAdult());
        booking.setNumberChildren(request.getNumberChildren());
        booking.setFirstName(request.getFirstName());
        booking.setLastName(request.getLastName());
        booking.setPhone(request.getPhone());
        booking.setGmail(request.getGmail());
        booking.setNote(request.getNote());
        booking.setTotalPrice(totalPrice);
        booking.setBookDate(LocalDate.now());
        booking.setStatus(1); // confirmed
        booking.setBookCode(generateBookCode());

        // 8. Giảm available seat
        departure.setAvailableSeat(departure.getAvailableSeat() - totalPeople);
        tourDepartureRepository.save(departure);

        Booking saved = bookingRepository.save(booking);
        return toResponse(saved);
    }

    public List<BookingResponse> getMyBookings(String gmail) {
        AppUser user = userRepository.findByGmail(gmail)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return bookingRepository.findByUserUserId(user.getUserId())
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    public BookingResponse getBookingById(Integer bookId, String gmail) {
        Booking booking = bookingRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        // Chỉ cho xem booking của chính mình
        if (!booking.getUser().getGmail().equals(gmail)) {
            throw new RuntimeException("Access denied");
        }
        return toResponse(booking);
    }

    @Transactional
    public BookingResponse cancelBooking(Integer bookId, String gmail) {
        Booking booking = bookingRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        if (!booking.getUser().getGmail().equals(gmail)) {
            throw new RuntimeException("Access denied");
        }
        if (booking.getStatus() == 0) {
            throw new RuntimeException("Booking already cancelled");
        }

        // Hoàn lại ghế
        TourDeparture departure = booking.getDeparture();
        int totalPeople = booking.getNumberAdult() + booking.getNumberChildren();
        departure.setAvailableSeat(departure.getAvailableSeat() + totalPeople);
        tourDepartureRepository.save(departure);

        booking.setStatus(0); // cancelled
        return toResponse(bookingRepository.save(booking));
    }

    private Long generateBookCode() {
        return 100000000L + new Random().nextInt(900000000);
    }

    private BookingResponse toResponse(Booking b) {
        return BookingResponse.builder()
                .bookId(b.getBookId())
                .bookCode(b.getBookCode())
                .tourName(b.getDeparture().getTour().getTourName())
                .startDate(b.getDeparture().getStartDate())
                .endDate(b.getDeparture().getEndDate())
                .numberAdult(b.getNumberAdult())
                .numberChildren(b.getNumberChildren())
                .totalPrice(b.getTotalPrice())
                .status(b.getStatus() == 1 ? "confirmed" : "cancelled")
                .voucherCode(b.getVoucher() != null ? b.getVoucher().getVoucherCode() : null)
                .paymentMethod(b.getPaymentMethod().getMethodName())
                .bookDate(b.getBookDate())
                .build();
    }
}
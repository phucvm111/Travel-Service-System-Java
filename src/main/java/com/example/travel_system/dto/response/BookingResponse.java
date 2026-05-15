package com.example.travel_system.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter @Setter @Builder
public class BookingResponse {
    private Integer bookId;
    private Long bookCode;
    private String tourName;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer numberAdult;
    private Integer numberChildren;
    private Double totalPrice;
    private String status;
    private String voucherCode;
    private String paymentMethod;
    private LocalDate bookDate;
}
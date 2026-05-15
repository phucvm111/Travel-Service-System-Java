package com.example.travel_system.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BookingRequest {
    private Integer departureId;
    private Integer numberAdult;
    private Integer numberChildren;
    private String voucherCode;   // nullable
    private Integer paymentMethodId;
    private String firstName;
    private String lastName;
    private String phone;
    private String gmail;
    private String note;
}
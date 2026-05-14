package com.example.travel_system.dto.request;


import lombok.Data;

@Data
public class RegisterRequest {
    private String gmail;
    private String password;
    private String firstName;
    private String lastName;
    private String phone;
}

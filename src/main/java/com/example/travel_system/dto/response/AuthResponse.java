package com.example.travel_system.dto.response;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private String gmail;
    private String fullName;
    private String role;
    private Integer userId;
}

package com.example.travel_system.service;

import com.example.travel_system.dto.request.*;
import com.example.travel_system.dto.response.AuthResponse;
import com.example.travel_system.entity.*;
import com.example.travel_system.repository.*;
import com.example.travel_system.security.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDate;
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsServiceImpl userDetailsService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse login(LoginRequest request) {
        // Xác thực username/password
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getGmail(), request.getPassword())
        );

        // Load user để generate token
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getGmail());
        String token = jwtTokenProvider.generateToken(userDetails);

        // Lấy thông tin user để trả về
        AppUser user = userRepository.findByGmail(request.getGmail()).get();

        return new AuthResponse(
                token,
                user.getGmail(),
                user.getFirstName() + " " + user.getLastName(),
                user.getRole().getRoleName(),
                user.getUserId()
        );
    }

    public void register(RegisterRequest request) {
        if (userRepository.existsByGmail(request.getGmail())) {
            throw new RuntimeException("Email đã tồn tại!");
        }

        // Mặc định role Tourist (role_id = 2)
        Role touristRole = roleRepository.findById(2)
                .orElseThrow(() -> new RuntimeException("Role không tồn tại"));

        AppUser user = new AppUser();
        user.setGmail(request.getGmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPhone(request.getPhone());
        user.setRole(touristRole);
        user.setStatus(1);
        user.setBalance(BigDecimal.ZERO);
        user.setCreateAt(LocalDate.now());
        user.setUpdateAt(LocalDate.now());

        userRepository.save(user);
    }
}

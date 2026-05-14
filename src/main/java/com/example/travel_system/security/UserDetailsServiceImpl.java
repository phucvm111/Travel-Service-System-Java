package com.example.travel_system.security;

import com.example.travel_system.entity.AppUser;
import com.example.travel_system.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String gmail) throws UsernameNotFoundException {
        AppUser user = userRepository.findByGmail(gmail)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "User không tồn tại: " + gmail));

        return new org.springframework.security.core.userdetails.User(
                user.getGmail(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority(
                        "ROLE_" + user.getRole().getRoleName()))
        );
    }
}
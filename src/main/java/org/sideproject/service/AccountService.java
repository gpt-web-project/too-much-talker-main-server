package org.sideproject.service;

import lombok.RequiredArgsConstructor;
import org.sideproject.model.dto.request.LoginRequest;
import org.sideproject.model.entity.User;
import org.sideproject.repository.UserRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AccountService {
    private final UserRepository userRepository;

    public long login(LoginRequest loginRequest) {
        User user = userRepository.findByUsername(loginRequest.getUsername());
        if (user != null && user.getPassword().equals(loginRequest.getPassword()) && user.isActive()) {
            // Optionally log login attempt, including initialLoginIp
            return user.getId();
        } else {
            // Handle login failure (throw exception or return a specific code)
            return -1L; // Example: returning -1 to indicate login failure
        }
    }


}

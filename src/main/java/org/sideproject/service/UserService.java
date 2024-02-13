package org.sideproject.service;

import lombok.RequiredArgsConstructor;
import org.sideproject.model.entity.User;
import org.sideproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new RuntimeException("User not found with id: " + userId)
        );
    }

    public void withdrawalUser(Long userId) {
        User user = getUserById(userId);
        user.withdrawal();
        userRepository.save(user);
    }
}


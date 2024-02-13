package org.sideproject.service;

import lombok.RequiredArgsConstructor;
import org.sideproject.model.dto.request.LoginRequest;
import org.sideproject.model.entity.User;
import org.sideproject.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AccountService {
    private final UserRepository userRepository;

    public long login(LoginRequest loginRequest) {
        Optional<User> entity = userRepository.findByUsername(loginRequest.getUsername());
        User user = entity.get();
        if (user != null && user.getPassword().equals(loginRequest.getPassword()) && user.isActive()) {
            return user.getId();
        } else {
            throw new RuntimeException("로그인 실패");
        }
    }

    @Transactional
    public void createAccount(LoginRequest loginRequest) {
        String username = loginRequest.getUsername();
        // username 중복 검사
        userRepository.findByUsername(username).ifPresent(user -> {
            throw new RuntimeException("이미 존재하는 사용자명입니다.");
        });

        // 나머지 회원가입 로직...
        String password = loginRequest.getPassword();
        String createdIp = loginRequest.getInitialLoginIp();
        User newUser = new User(username, password, createdIp);
        userRepository.save(newUser);
    }

}

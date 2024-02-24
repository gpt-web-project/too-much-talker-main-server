package org.sideproject.model.dto.response;

import lombok.Getter;

@Getter
public class LoginResponse {
    long userId;
    String username;

    public LoginResponse(long userId) {
        this.userId = userId;
    }

    public LoginResponse(long userId,String username) {
        this.userId = userId;
        this.username = username;
    }
}

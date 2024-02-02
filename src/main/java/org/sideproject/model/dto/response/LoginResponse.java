package org.sideproject.model.dto.response;

import lombok.Getter;

@Getter
public class LoginResponse {
    long userId;

    public LoginResponse(long userId) {
        this.userId = userId;
    }
}

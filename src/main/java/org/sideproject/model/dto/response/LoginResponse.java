package org.sideproject.model.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
public class LoginResponse {
    long userId;

    public LoginResponse(long userId){
        this.userId = userId;
    }
}

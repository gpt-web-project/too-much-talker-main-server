package org.sideproject.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Null;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LoginRequest {
    @NotEmpty
    private String username;
    @NotEmpty
    private String password;

    private String initialLoginIp; // Todo. ip get
}

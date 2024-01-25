package org.sideproject.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.sideproject.model.dto.request.LoginRequest;
import org.sideproject.model.dto.response.LoginResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/account")
@Api(value = "/account")
public class AccountController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ApiOperation(value="로그인 API"
            , notes="토큰 로그인은 추후 구현하는걸로합니당. 일단은 브라우저에 유저아이디값 저장하는걸로"
            , response = LoginResponse.class)
    @ResponseBody
    @GetMapping("/login")
    private ResponseEntity login(@RequestBody LoginRequest loginRequest){
        long tempId = 0L;
        return new ResponseEntity(
                new LoginResponse(tempId),
                HttpStatus.OK
        );
    }

}

package org.sideproject.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.sideproject.model.dto.request.LoginRequest;
import org.sideproject.model.dto.response.LoginResponse;
import org.sideproject.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/account")
@Api(value = "/account", tags = "사용자 계정 관련 기능")
public class AccountController {
    private final AccountService accountService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ApiOperation(value="로그인 API"
            , notes="토큰 로그인은 추후 구현하는걸로합니당. 일단은 브라우저에 유저아이디값 저장하는걸로" +
            ", 추후 요청을 너무 많이 보내는 유저는 벤먹이기위해 최초 로그인 ip도 같이보내주세요"
            , response = LoginResponse.class)
    @PostMapping("/login")
    private ResponseEntity login(@Valid @RequestBody LoginRequest loginRequest){
        long userId = accountService.login(loginRequest);
        return new ResponseEntity(
                new LoginResponse(userId),
                HttpStatus.OK
        );
    }

    @ApiOperation(value="회원가입 API"
            , notes=""
            , response = LoginResponse.class)
    @PostMapping("/create-account")
    private ResponseEntity createAccount(@Valid @RequestBody LoginRequest loginRequest){
        accountService.createAccount(loginRequest);
        return new ResponseEntity(
                HttpStatus.OK
        );
    }

    @ApiOperation(value="로그인 이력 관리 API"
            , notes="당장 사용하지 않을거지만 이후 로그인IP를 관리할 예정입니다.(mvp이후)"
            , response = LoginResponse.class)
    @PostMapping("/login-history")
    private ResponseEntity countLoginHistory(@Valid @RequestBody LoginRequest loginRequest){
        long tempId = 0L;
        return new ResponseEntity(
                new LoginResponse(tempId),
                HttpStatus.OK
        );
    }

}

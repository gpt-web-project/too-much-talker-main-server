package org.sideproject.controller;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/resume")
@Api(value = "/resume", tags = "사용자 계정 관련 기능")
public class ResumeController {
}

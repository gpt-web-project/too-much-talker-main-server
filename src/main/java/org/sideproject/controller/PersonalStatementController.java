package org.sideproject.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.sideproject.model.dto.response.ResumeResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/application")
@Api(value = "/application", tags = "자기소개서 작성 관련 기능")
public class PersonalStatementController {

    @ApiOperation(value = "기업 정보 입력하기(캡쳐로 업로드), PNG & JPG 만 가능",
            notes = "",
            response = ResumeResponse.class)
    @PostMapping("/job-postings/pdf-auto-map")
    private ResponseEntity pdfToJob(@RequestParam("file") MultipartFile file) {
        // 파이썬 서버로 요청
        return new ResponseEntity<>(
                HttpStatus.OK
        );
    }

    @ApiOperation(value = "기업 정보 입력하기(URL 업로드)",
            notes = "",
            response = ResumeResponse.class)
    @PostMapping("/job-postings/url-auto-map")
    private ResponseEntity urlToJob(@RequestParam("file") MultipartFile file) {
        // 파이썬 서버로 요청
        return new ResponseEntity<>(
                HttpStatus.OK
        );
    }

}

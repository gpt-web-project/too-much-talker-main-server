package org.sideproject.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.sideproject.model.dto.EachUserJobApplyQuestion;
import org.sideproject.model.dto.request.JobApplicationRequest;
import org.sideproject.model.dto.response.CompanyDetailsResponse;
import org.sideproject.model.dto.response.JobAnswerResponse;
import org.sideproject.model.metadata.Message;
import org.sideproject.service.ApplyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/application")
@Api(value = "/application", tags = "자기소개서 작성 관련 기능")
public class ApplyController {
    private final ApplyService applyService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ApiOperation(value = "기업 정보 입력하기(캡쳐로 업로드), PNG & JPG 만 가능",
            notes = "지원하지않는 기능 (mvp이후 동작함)",
            response = CompanyDetailsResponse.class)
    @PostMapping("/job-postings/pdf-auto-map")
    private ResponseEntity pdfToJob(@RequestParam("file") MultipartFile file) {
        // 파이썬 서버로 요청
        CompanyDetailsResponse result = new CompanyDetailsResponse("A","A","A","A");
        return new ResponseEntity<>(
                result,
                HttpStatus.OK
        );
    }

    @ApiOperation(value = "기업 정보 입력하기(URL 업로드)",
            notes = "지원하지않는 기능 (추후 지원 가능성있음)")
    @PostMapping("/job-postings/url-auto-map")
    private ResponseEntity urlToJob(@RequestParam("file") MultipartFile file) {
        // 파이썬 서버로 요청
        return new ResponseEntity<>(
                HttpStatus.OK
        );
    }

    @ApiOperation(value = "자기소개서 요청 정보 일괄 저장",
            notes = "기업정보와 자기소개 질문을 일괄 저장하는 기능")
    @PostMapping("")
    private ResponseEntity saveJobInfo(@Valid @RequestBody JobApplicationRequest jobInfoApplyRequest) {
        applyService.saveJobInfo(jobInfoApplyRequest);
        return new ResponseEntity<>(
                Message.OK
                ,HttpStatus.OK
        );
    }

    @ApiOperation(value = "자기소개서 작성 결과 조회",
            notes = "자기소개서 작성 요청 결과 조회",
            response = JobAnswerResponse.class)
    @GetMapping("/{userId}")
    public ResponseEntity<List<JobAnswerResponse>> getApplyAnswerForQuestion(@PathVariable("userId") Long userId) {
        List<JobAnswerResponse> jobAnswerResponses = applyService.getAnswer(userId);
        return new ResponseEntity<>(jobAnswerResponses, HttpStatus.OK);
    }

}

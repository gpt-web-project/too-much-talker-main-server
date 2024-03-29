package org.sideproject.controller;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.sideproject.model.dto.EachResume;
import org.sideproject.model.dto.request.ResumeRequest;
import org.sideproject.model.dto.response.ResumeResponse;
import org.sideproject.model.metadata.Message;
import org.sideproject.model.metadata.WorkCode;
import org.sideproject.service.ApplyService;
import org.sideproject.service.ResumeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import io.swagger.annotations.ApiOperation;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/resumes")
@Api(value = "/resumes", tags = "이력서 작성 관련 기능")
public class ResumeController {
    private final ResumeService resumeService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ApiOperation(value = "기존에 작성한 이력서 업로드 시 자동 매핑, PDF 파일만 가능",
            notes = "",
            response = ResumeResponse.class)
    @PostMapping("/file-upload")
    private ResponseEntity pdfToResume(@RequestParam("file") MultipartFile file) {


        return new ResponseEntity<>(
                HttpStatus.OK
        );
    }

    @ApiOperation(value = "작성한 이력사항 저장하기",
            notes = "날짜데이터는 일자는 저장하지않지만 default값을 yyyy-mm-dd로 주세요 (dd는 01로 통일), \"endDate\": \"2022-06-01\" 이렇게 주시라는")
    @PostMapping("")
    private ResponseEntity saveUserResume(@Valid @RequestBody ResumeRequest resumeRequest) {
        resumeService.saveUserResume(resumeRequest);
        return new ResponseEntity<>(
                Message.OK
                ,HttpStatus.OK
        );
    }

    @ApiOperation(value = "이력사항 조회하기",
            notes = "// 경험 유형 코드\n" +
                    "    EDUCATION(\"학력사항\", \"EDUCATION\"),\n" +
                    "    EXPERIENCE(\"경력사항\", \"EXPERIENCE\"),\n" +
                    "    LICENSES(\"자격증\", \"LICENSES\"),\n" +
                    "    ACTIVITY(\"학내외활동사항\", \"ACTIVITY\"),\n" +
                    "    ETC(\"기타\", \"ETC\");",
            response = ResumeResponse.class)
    @GetMapping("/{userId}")
    private ResponseEntity getUserResume(@PathVariable("userId") Long userId) {

        ResumeResponse result = resumeService.getUserResume(userId);

        return new ResponseEntity<>(
                result,
                HttpStatus.OK
        );
    }
}

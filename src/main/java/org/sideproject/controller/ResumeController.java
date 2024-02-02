package org.sideproject.controller;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.sideproject.model.dto.EachResume;
import org.sideproject.model.dto.request.ResumeRequest;
import org.sideproject.model.dto.response.ResumeResponse;
import org.sideproject.model.metadata.WorkCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import io.swagger.annotations.ApiOperation;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/resumes")
@Api(value = "/resumes", tags = "이력서 작성 관련 기능")
public class ResumeController {

    @ApiOperation(value = "기존에 작성한 이력서 업로드 시 자동 매핑, PDF 파일만 가능",
            notes = "",
            response = ResumeResponse.class)
    @PostMapping("/file-upload")
    private ResponseEntity pdfToResume(@RequestParam("file") MultipartFile file) {
        // 파이썬 서버로 외부 요청 필요
        // 여기에 파일 처리 로직을 구현하세요.

        // 목 데이터 생성
        List<EachResume> mockUserResumeList = Arrays.asList(
                new EachResume(1L,1L, "대학교", "학생","", LocalDate.of(2018, 3, 1), LocalDate.of(2022, 2, 28), WorkCode.EDUCATION),
                new EachResume(1L,2L, "XYZ 회사", "개발자","", LocalDate.of(2022, 3, 1), null, WorkCode.EXPERIENCE)
        );

        ResumeResponse mockResponse = new ResumeResponse(mockUserResumeList);

        return new ResponseEntity<>(
                mockResponse,
                HttpStatus.OK
        );
    }

    @ApiOperation(value = "작성한 이력사항 저장하기",
            notes = "")
    @PostMapping("")
    private ResponseEntity saveUserResume(@RequestBody ResumeRequest resumeRequest) {

        return new ResponseEntity<>(
                HttpStatus.OK
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

        List<EachResume> mockUserResumeList = Arrays.asList(
                new EachResume(1L,1L, "대학교", "학생","", LocalDate.of(2018, 3, 1), LocalDate.of(2022, 2, 28), WorkCode.EDUCATION),
                new EachResume(1L,2L, "XYZ 회사", "개발자","", LocalDate.of(2022, 3, 1), null, WorkCode.EXPERIENCE)
        );

        ResumeResponse mockResponse = new ResumeResponse(mockUserResumeList);

        return new ResponseEntity<>(
                mockResponse,
                HttpStatus.OK
        );
    }
}

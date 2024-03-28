package org.sideproject.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        // RuntimeException이 발생했을 때 클라이언트에 전달할 메시지와 HTTP 상태 코드 설정
        System.out.println(ex.getMessage());
        return new ResponseEntity(
                "에러 발생"
                , HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<String> handleDataAccessException(DataAccessException ex) {
        System.out.println(ex.getMessage());
        return new ResponseEntity(
                "데이터베이스 에러 발생"
                , HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @ExceptionHandler(InvalidDataAccessResourceUsageException.class)
    public ResponseEntity<String> InvalidDataAccessResourceUsageException(InvalidDataAccessResourceUsageException ex) {
        // 찾는데이터가 없을땐 not found
        System.out.println(ex.getMessage());
        return new ResponseEntity(
                "잘못된 데이터 요청입니다."
                , HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> IllegalArgumentException(IllegalArgumentException ex) {
        // 찾는데이터가 없을땐 not found
        System.out.println(ex.getMessage());
        return new ResponseEntity(
                "형식에 맞지 않는 데이터 요청입니다."
                , HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(JsonProcessingException.class)
    public ResponseEntity<String> JsonProcessingException(JsonProcessingException ex) {
        System.out.println(ex.getMessage());
        return new ResponseEntity(
                "잘못된 JSON 형식입니다"
                , HttpStatus.BAD_REQUEST
        );
    }
}

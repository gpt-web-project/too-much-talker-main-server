package org.sideproject.config;

import org.sideproject.model.metadata.Message;
import org.springframework.dao.DataAccessException;
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
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<String> handleDataAccessException(DataAccessException ex) {
        // 찾는데이터가 없을땐 not found
        System.out.println(ex.getMessage());
        return new ResponseEntity(
                HttpStatus.NOT_FOUND
        );
    }
}

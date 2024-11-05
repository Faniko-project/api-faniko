package com.faniko.api_faniko.rest.config;

import com.faniko.api_faniko.utils.dto.error.ErrorRecord;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ResponseStatusExceptionControllerAdvice {
    @Autowired
    private HttpServletRequest request;

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<?> handleResponseStatusException(ResponseStatusException ex) {
        int status = ex.getStatusCode().value();
        String error = HttpStatus.valueOf(status).getReasonPhrase();
        String message = ex.getReason();
        String path = request.getRequestURI();

        ErrorRecord errorRecord = new ErrorRecord(
                LocalDateTime.now(),
                status,
                error,
                message,
                path);

        return ResponseEntity.status(ex.getStatusCode()).body(errorRecord);
    }
}

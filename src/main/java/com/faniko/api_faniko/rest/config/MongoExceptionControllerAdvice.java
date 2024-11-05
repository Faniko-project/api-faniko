package com.faniko.api_faniko.rest.config;

import com.faniko.api_faniko.utils.dto.error.ErrorRecord;
import com.mongodb.MongoWriteException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestControllerAdvice
public class MongoExceptionControllerAdvice {
    @Autowired
    private HttpServletRequest request;

    @ExceptionHandler(MongoWriteException.class)
    public ResponseEntity<ErrorRecord> handleMongoWriteException(MongoWriteException ex) {
        ErrorRecord errorRecord = new ErrorRecord(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                extractErrorMessage(ex.getMessage()),
                request.getRequestURI()
        );

        return ResponseEntity.badRequest().body(errorRecord);
    }

    /**
     * Extracts the error message from the full message of the exception.
     *
     * @param fullMessage the full message of the exception {@link String}
     * @return the error message {@link  String}
     */
    private String extractErrorMessage(String fullMessage) {
        Pattern pattern = Pattern.compile("WriteError\\{code=\\d+, message='(.+?)', details=\\{\\}\\}");
        Matcher matcher = pattern.matcher(fullMessage);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return "Unknown error";
    }
}

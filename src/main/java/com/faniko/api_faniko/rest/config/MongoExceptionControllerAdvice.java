package com.faniko.api_faniko.rest.config;

import com.mongodb.MongoWriteException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

@RestControllerAdvice
public class MongoExceptionControllerAdvice {

    @ExceptionHandler(MongoWriteException.class)
    public ResponseEntity<HashMap<String, String>> handleMongoWriteException(MongoWriteException ex) {
        HashMap<String, String> errors = new HashMap<>();
        errors.put("detail", ex.getMessage());
        errors.put("code", String.valueOf(ex.getCode()));

        return ResponseEntity.badRequest().body(errors);
    }
}

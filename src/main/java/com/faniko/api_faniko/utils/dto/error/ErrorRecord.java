package com.faniko.api_faniko.utils.dto.error;

import java.time.LocalDateTime;

/**
 * ErrorRecord is a DTO class that represents an error record.
 * It contains the timestamp, status, error, message, and path of the error.
 */
public record ErrorRecord(
        LocalDateTime timestamp,
        Integer status,
        String error,
        String message,
        String path
) {}

package com.ginda.tictactoe.exception.controlleradvice;

import com.ginda.tictactoe.exception.BadRequestException;
import com.ginda.tictactoe.exception.ConflictException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import java.time.Clock;
import java.time.ZonedDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class GlobalControllerExceptionHandler {

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(value = ConflictException.class)
    public ResponseEntity<Map<String, Object>> handleConflictException(ConflictException exception, WebRequest request) {
        Map<String, Object> errorResponse = new LinkedHashMap<>();
        errorResponse.put("timestamp", ZonedDateTime.now(Clock.systemUTC()));
        errorResponse.put("status", 409);
        errorResponse.put("error", "Conflict");
        errorResponse.put("message", exception.getMessage());
        errorResponse.put("path", request.getContextPath());
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    };

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(value = ConflictException.class)
    public ResponseEntity<Map<String, Object>> handleBadRequestException(BadRequestException exception, WebRequest request) {
        Map<String, Object> errorResponse = new LinkedHashMap<>();
        errorResponse.put("timestamp", ZonedDateTime.now(Clock.systemUTC()));
        errorResponse.put("status", 400);
        errorResponse.put("error", "Bad Request");
        errorResponse.put("message", exception.getMessage());
        errorResponse.put("path", request.getContextPath());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    };

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Map<String, Object>> handleInternalException(Exception exception, WebRequest request) {
        log.error(exception.getMessage(), exception);
        Map<String, Object> errorResponse = new LinkedHashMap<>();
        errorResponse.put("timestamp", ZonedDateTime.now(Clock.systemUTC()));
        errorResponse.put("status", 500);
        errorResponse.put("error", "InternalServerError");
        errorResponse.put("message", exception.getMessage());
        errorResponse.put("path", request.getContextPath());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    };
}

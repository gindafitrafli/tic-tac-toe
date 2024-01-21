package com.ginda.tictactoe.exception.controlleradvice;

import com.ginda.tictactoe.exception.BadRequestException;
import com.ginda.tictactoe.exception.ConflictException;
import com.ginda.tictactoe.exception.NotFoundException;
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

    @ExceptionHandler
    public ResponseEntity<Map<String, Object>> handle(Throwable exception, WebRequest request) {

        if (exception instanceof ConflictException){
            return handleConflictException((ConflictException) exception, request);
        } else if (exception instanceof BadRequestException) {
            return handleBadRequestException((BadRequestException) exception, request);
        } else if (exception instanceof NotFoundException) {
            return handleNotFoundException((NotFoundException) exception, request);
        } else {
            return handleInternalException(exception, request);
        }
    };
    
    public ResponseEntity<Map<String, Object>> handleConflictException(ConflictException exception, WebRequest request) {
        Map<String, Object> errorResponse = new LinkedHashMap<>();
        errorResponse.put("timestamp", ZonedDateTime.now(Clock.systemUTC()));
        errorResponse.put("status", 409);
        errorResponse.put("error", "Conflict");
        errorResponse.put("message", exception.getMessage());
        errorResponse.put("path", request.getContextPath());
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    };

    public ResponseEntity<Map<String, Object>> handleBadRequestException(BadRequestException exception, WebRequest request) {
        Map<String, Object> errorResponse = new LinkedHashMap<>();
        errorResponse.put("timestamp", ZonedDateTime.now(Clock.systemUTC()));
        errorResponse.put("status", 400);
        errorResponse.put("error", "Bad Request");
        errorResponse.put("message", exception.getMessage());
        errorResponse.put("path", request.getContextPath());
        errorResponse.put("field", exception.getFieldName());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    };

    public ResponseEntity<Map<String, Object>> handleNotFoundException(NotFoundException exception, WebRequest request) {
        Map<String, Object> errorResponse = new LinkedHashMap<>();
        errorResponse.put("timestamp", ZonedDateTime.now(Clock.systemUTC()));
        errorResponse.put("status", 404);
        errorResponse.put("error", "Not Found");
        errorResponse.put("message", exception.getMessage());
        errorResponse.put("path", request.getContextPath());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    };


    public ResponseEntity<Map<String, Object>> handleInternalException(Throwable throwable, WebRequest request) {
        log.error(throwable.getMessage(), throwable);
        Map<String, Object> errorResponse = new LinkedHashMap<>();
        errorResponse.put("timestamp", ZonedDateTime.now(Clock.systemUTC()));
        errorResponse.put("status", 500);
        errorResponse.put("error", "Internal Server Error");
        errorResponse.put("message", throwable.getMessage());
        errorResponse.put("path", request.getContextPath());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    };
}

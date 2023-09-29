package com.dmtryii.usersystem.exception.handle_exception;

import com.dmtryii.usersystem.exception.UserAgeException;
import com.dmtryii.usersystem.exception.UserNotCreatedException;
import com.dmtryii.usersystem.exception.UserNotFountException;
import com.dmtryii.usersystem.exception.UserNotUpdateException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserAgeException.class)
    public ResponseEntity<ErrorObject> handle(UserAgeException ex,
                                              WebRequest request) {
        return handle_BAD_REQUEST(ex, request);
    }

    @ExceptionHandler(UserNotFountException.class)
    public ResponseEntity<ErrorObject> handle(UserNotFountException ex,
                                              WebRequest request) {
        return handle_NOT_FOUND(ex, request);
    }

    @ExceptionHandler(UserNotCreatedException.class)
    public ResponseEntity<ErrorObject> handle(UserNotCreatedException ex,
                                              WebRequest request) {
        return handle_BAD_REQUEST(ex, request);
    }

    @ExceptionHandler(UserNotUpdateException.class)
    public ResponseEntity<ErrorObject> handle(UserNotUpdateException ex,
                                              WebRequest request) {
        return handle_BAD_REQUEST(ex, request);
    }

    private ResponseEntity<ErrorObject> handle_NOT_FOUND(RuntimeException ex,
                                                         WebRequest request) {
        return handleMethod(ex, request,
                HttpStatus.NOT_FOUND
        );
    }

    private ResponseEntity<ErrorObject> handle_BAD_REQUEST(RuntimeException ex,
                                                           WebRequest request) {
        return handleMethod(ex, request,
                HttpStatus.BAD_REQUEST
        );
    }

    private ResponseEntity<ErrorObject> handleMethod(RuntimeException ex,
                                                     WebRequest request,
                                                     HttpStatus httpStatus) {
        ErrorObject errorObject = ErrorObject.builder()
                .timestamp(new Date())
                .status(httpStatus.value())
                .error(ex.getMessage())
                .path(request.getDescription(false))
                .build();
        return new ResponseEntity<>(errorObject, httpStatus);
    }
}

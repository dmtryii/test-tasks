package com.dmtryii.internetshop.exception.handle_exception;

import com.dmtryii.internetshop.exception.NegativeQuantityException;
import com.dmtryii.internetshop.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handle(ResourceNotFoundException ex,
                                    WebRequest request) {
        return handleMethod(
                ex, request, HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(NegativeQuantityException.class)
    public ResponseEntity<?> handle(NegativeQuantityException ex,
                                    WebRequest request) {
        return handleMethod(
                ex, request, HttpStatus.BAD_REQUEST
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

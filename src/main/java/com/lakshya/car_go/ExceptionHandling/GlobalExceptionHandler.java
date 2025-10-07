package com.lakshya.car_go.ExceptionHandling;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.lakshya.car_go.dto.ErrorResponsDTO;

@RestControllerAdvice
public class GlobalExceptionHandler {
    // define the exception
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponsDTO> handleResourceNotFound(ResourceNotFoundException ex){
        ErrorResponsDTO error =  new ErrorResponsDTO(ex.getMessage(), HttpStatus.NOT_FOUND.value(),"Not Found", Instant.now());
        return new ResponseEntity<>(error , HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<ErrorResponsDTO> handleInvalidInput(InvalidInputException ex){
        ErrorResponsDTO error = new ErrorResponsDTO(ex.getMessage(), HttpStatus.BAD_REQUEST.value(),"Bad Request", Instant.now());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({DataConflictException.class,CarUnavailableException.class})
    public ResponseEntity<ErrorResponsDTO> handleConflictException(RuntimeException ex){
        ErrorResponsDTO error = new ErrorResponsDTO(ex.getMessage(),HttpStatus.CONFLICT.value(),"CONFLICT",Instant.now());
        return new ResponseEntity<>(error , HttpStatus.CONFLICT);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponsDTO> handleGenerics(Exception ex){
        ErrorResponsDTO error = new ErrorResponsDTO(ex.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR.value(),"Internal Server Error",Instant.now());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

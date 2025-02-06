package com.example.demo.exceptions;

import com.example.demo.dto.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> handleValdationExceptions(MethodArgumentNotValidException ex){
        Map<String,String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error->errors.put(error.getField(),error.getDefaultMessage()));
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorDto> handleValidationException(ValidationException ex){
        ErrorDto errorDto = new ErrorDto(ex.getMessage(),"VALIDATION_ERROR");
        return new ResponseEntity<>(errorDto,HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(DataMismatchException.class)
    public ResponseEntity<ErrorDto> handleDataMismatchException(DataMismatchException ex){
        ErrorDto errorDto = new ErrorDto(ex.getMessage(),"DATA_MISMATCH_ERROR");
        return new ResponseEntity<>(errorDto,HttpStatus.CONFLICT);
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> handleGenericException(Exception ex){
        ErrorDto errorDto = new ErrorDto(ex.getMessage(),"INTERNAL_SERVER_ERROR");
        return new ResponseEntity<>(errorDto,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

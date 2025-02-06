package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

//@Getter
//@Setter

public class ErrorDto {
    private String message;
    private String errorCode;

    public ErrorDto(String message, String errorCode) {
        this.message = message;
        this.errorCode = errorCode;
    }
    public ErrorDto(){
        
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}

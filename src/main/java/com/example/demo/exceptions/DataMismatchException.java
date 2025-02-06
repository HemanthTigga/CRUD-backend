package com.example.demo.exceptions;

public class DataMismatchException extends RuntimeException{
    public DataMismatchException(String message){
        super(message);
    }
}

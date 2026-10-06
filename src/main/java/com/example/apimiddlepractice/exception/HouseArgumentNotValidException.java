package com.example.apimiddlepractice.exception;

public class HouseArgumentNotValidException extends RuntimeException {
    public HouseArgumentNotValidException(String message) {
        super(message);
    }
}

package com.example.apimiddlepractice.exception.handler;


import com.example.apimiddlepractice.exception.HouseArgumentNotValidException;
import com.example.apimiddlepractice.exception.HouseNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HouseNotFoundException.class)
    public ResponseEntity<String> handleHouseNotFoundException(HouseNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
//        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(HouseArgumentNotValidException.class)
    public ResponseEntity<String> noteArgumentNotValidException(HouseArgumentNotValidException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }


}

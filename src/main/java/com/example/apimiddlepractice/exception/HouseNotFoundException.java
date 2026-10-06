package com.example.apimiddlepractice.exception;

public class HouseNotFoundException extends RuntimeException {
    public HouseNotFoundException(long id) {
        super("指定されたIDのノートが見つかりません:" + id);
    }
}

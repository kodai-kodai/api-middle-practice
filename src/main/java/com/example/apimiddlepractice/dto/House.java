package com.example.apimiddlepractice.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class House {
    private long houseId;
    private int amount;
    private String type;
    private String content;
    private String category;
    private Date transDate;
    private LocalDateTime createdAt;
}

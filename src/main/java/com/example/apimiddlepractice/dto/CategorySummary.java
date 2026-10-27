package com.example.apimiddlepractice.dto;

import lombok.Data;

@Data
public class CategorySummary {
    private String type;
    private String category;
    private long totalAmount;
}

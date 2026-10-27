package com.example.apimiddlepractice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MonthlySummary {
    private String month;
    private long income;
    private long expense;
    private long balance;
}

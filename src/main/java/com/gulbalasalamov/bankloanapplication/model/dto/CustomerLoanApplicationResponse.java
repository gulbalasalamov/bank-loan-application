package com.gulbalasalamov.bankloanapplication.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CustomerLoanApplicationResponse {
    private String firstName;
    private String description;
}

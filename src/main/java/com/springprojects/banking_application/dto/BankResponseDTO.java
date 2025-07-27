package com.springprojects.banking_application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class BankResponseDTO {

    private String responseCode;
    private String responseMessage;
    private AccountInfo accountInfo;
}

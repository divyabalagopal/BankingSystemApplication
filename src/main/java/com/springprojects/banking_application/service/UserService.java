package com.springprojects.banking_application.service;

import com.springprojects.banking_application.dto.*;

public interface UserService {

   BankResponseDTO createAccount(UserRequestDTO userRequestDTO);

   BankResponseDTO balanceEnquiry(EnquiryRequestDTO enquiryRequestDTO);

   String nameEnquiry(EnquiryRequestDTO enquiryRequestDTO);

   BankResponseDTO creditRequest(CreditDebitRequestDTO creditDebitRequestDTO);

   BankResponseDTO debitRequest(CreditDebitRequestDTO creditDebitRequestDTO);

   BankResponseDTO transferRequest(TransferDTO transfer);
}

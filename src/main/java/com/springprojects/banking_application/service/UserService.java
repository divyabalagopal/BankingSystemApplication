package com.springprojects.banking_application.service;

import com.springprojects.banking_application.dto.BankResponseDTO;
import com.springprojects.banking_application.dto.CreditDebitRequestDTO;
import com.springprojects.banking_application.dto.EnquiryRequestDTO;
import com.springprojects.banking_application.dto.UserRequestDTO;

public interface UserService {

   BankResponseDTO createAccount(UserRequestDTO userRequestDTO);

   BankResponseDTO balanceEnquiry(EnquiryRequestDTO enquiryRequestDTO);

   String nameEnquiry(EnquiryRequestDTO enquiryRequestDTO);

   BankResponseDTO creditRequest(CreditDebitRequestDTO creditDebitRequestDTO);

   BankResponseDTO debitRequest(CreditDebitRequestDTO creditDebitRequestDTO);

}

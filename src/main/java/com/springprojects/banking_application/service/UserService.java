package com.springprojects.banking_application.service;

import com.springprojects.banking_application.dto.BankResponseDTO;
import com.springprojects.banking_application.dto.UserRequestDTO;

public interface UserService {

   BankResponseDTO createAccount(UserRequestDTO userRequestDTO);

}

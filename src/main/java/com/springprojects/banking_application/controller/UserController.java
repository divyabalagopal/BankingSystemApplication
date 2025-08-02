package com.springprojects.banking_application.controller;

import com.springprojects.banking_application.dto.BankResponseDTO;
import com.springprojects.banking_application.dto.CreditDebitRequestDTO;
import com.springprojects.banking_application.dto.EnquiryRequestDTO;
import com.springprojects.banking_application.dto.UserRequestDTO;
import com.springprojects.banking_application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/newUser")
    public BankResponseDTO createAccount(@RequestBody UserRequestDTO userRequestDTO)
    {
        return userService.createAccount(userRequestDTO);
    }

    @GetMapping("/balanceEnquiry")
    public BankResponseDTO balanceEnquiry(@RequestBody EnquiryRequestDTO enquiryRequest)
    {
        return userService.balanceEnquiry(enquiryRequest);
    }

    @GetMapping("/nameEnquiry")
    public String nameEnquiry(@RequestBody EnquiryRequestDTO enquiryRequestDTO)
    {
        return userService.nameEnquiry(enquiryRequestDTO);
    }

    @PostMapping("/credit")
    public BankResponseDTO creditIntoAccount(@RequestBody CreditDebitRequestDTO creditDebitRequestDTO)
    {
        return userService.creditRequest(creditDebitRequestDTO);
    }

    @PostMapping("/debit")
    public BankResponseDTO debitIntoAccount(@RequestBody CreditDebitRequestDTO creditDebitRequestDTO)
    {
        return userService.debitRequest(creditDebitRequestDTO);
    }
}

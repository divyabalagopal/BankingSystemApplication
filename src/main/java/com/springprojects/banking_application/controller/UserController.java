package com.springprojects.banking_application.controller;

import com.springprojects.banking_application.dto.BankResponseDTO;
import com.springprojects.banking_application.dto.UserRequestDTO;
import com.springprojects.banking_application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}

package com.springprojects.banking_application.service;

import com.springprojects.banking_application.dto.AccountInfo;
import com.springprojects.banking_application.dto.BankResponseDTO;
import com.springprojects.banking_application.dto.UserRequestDTO;
import com.springprojects.banking_application.entity.User;
import com.springprojects.banking_application.repository.userRepo;
import com.springprojects.banking_application.utils.AccountUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    userRepo userRepo;
    @Override
    public BankResponseDTO createAccount(UserRequestDTO userRequestDTO) {

        /* Create an account -- saving a new user into the DB*/

        //Check if user already exists & has an account

        if(userRepo.existsByEmail(userRequestDTO.getEmail()))
        {
            return BankResponseDTO.builder().responseCode(AccountUtils.ACCOUNT_EXISTS_CODE)
                            .responseMessage(AccountUtils.ACCOUNT_EXISTS_MESSAGE)
                    .accountInfo(null)
                    .build();
        }

        User newUser = User.builder().firstName(userRequestDTO.getFirstName())
                .lastName(userRequestDTO.getLastName())
                .otherName(userRequestDTO.getOtherName())
                .gender(userRequestDTO.getGender())
                .address(userRequestDTO.getAddress())
                .stateOfOrigin(userRequestDTO.getStateOfOrigin())
                .accountNumber(AccountUtils.generateAccountNumber())
                .accountBalance(BigDecimal.ZERO)
                .email(userRequestDTO.getEmail())
                .phoneNumber(userRequestDTO.getPhoneNumber())
                .alternativePhoneNumber(userRequestDTO.getAlternativePhoneNumber())
                .status("ACTIVE")
                .build();

        User savedUser = userRepo.save(newUser);
        return BankResponseDTO.builder()
                        .responseCode(AccountUtils.ACCOUNT_CREATION_CODE)
                                .responseMessage(AccountUtils.ACCOUNT_CREATION_MESSAGE)
                .accountInfo(AccountInfo.builder()
                        .accountBalance(savedUser.getAccountBalance())
                        .accountNumber(savedUser.getAccountNumber())
                        .accountName(savedUser.getFirstName() + " " + savedUser.getLastName()+" " + savedUser.getOtherName())
                        .build()).build();
    }
}

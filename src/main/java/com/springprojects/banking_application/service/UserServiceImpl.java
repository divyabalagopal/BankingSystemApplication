package com.springprojects.banking_application.service;

import com.springprojects.banking_application.dto.*;
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

    @Autowired
    EmailService emailService;
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

        //Send email Alerts
        EmailDetailsDTO emailDetailsDTO = EmailDetailsDTO.builder().recipient(savedUser.getEmail())
                .subject("ACCOUNT CREATION SUCCESSFUL")
                .messageBody("Congratulations, your account has been created successfully created.\n Find your account details:\n Account name: "+savedUser.getFirstName() +" "+savedUser.getLastName()+" "+savedUser.getOtherName() +"\nAccount Number: " + savedUser.getAccountNumber())
                .build();
        emailService.sendEmailAlert(emailDetailsDTO);

        return BankResponseDTO.builder()
                        .responseCode(AccountUtils.ACCOUNT_CREATION_CODE)
                                .responseMessage(AccountUtils.ACCOUNT_CREATION_MESSAGE)
                .accountInfo(AccountInfo.builder()
                        .accountBalance(savedUser.getAccountBalance())
                        .accountNumber(savedUser.getAccountNumber())
                        .accountName(savedUser.getFirstName() + " " + savedUser.getLastName()+" " + savedUser.getOtherName())
                        .build()).build();
    }

    @Override
    public BankResponseDTO balanceEnquiry(EnquiryRequestDTO enquiryRequest) {
        //check if the provided account number exists in DB
        //we have a repo method that checks, existsByAccountNumber
        boolean isAccountExists = userRepo.existsByAccountNumber(enquiryRequest.getAccountNumber());

        if(!isAccountExists)
        {
            return BankResponseDTO.builder()
                    .responseCode(AccountUtils.ACCOUNT_DOES_NOT_EXIST_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_DOES_NOT_EXIST_MESSAGE)
                    .accountInfo(null)
                    .build();
        }

        //Retrieve an object of user and return a response with balance, account number, account name and success messages
        User foundUser = userRepo.findByAccountNumber(enquiryRequest.getAccountNumber());
        return BankResponseDTO.builder()
                .responseCode(AccountUtils.ACCOUNT_FOUND_CODE)
                .responseMessage(AccountUtils.ACCOUNT_FOUND_MESSAGE)
                .accountInfo(AccountInfo.builder()
                        .accountBalance(foundUser.getAccountBalance())
                        .accountNumber(enquiryRequest.getAccountNumber())
                        .accountName(STR."\{foundUser.getFirstName()} \{foundUser.getLastName()} \{foundUser.getOtherName()}")
                        .build()).build();
    }

    //find the name associated with account number
    @Override
    public String nameEnquiry(EnquiryRequestDTO enquiryRequest) {
        boolean isAccountExists= userRepo.existsByAccountNumber(enquiryRequest.getAccountNumber());

        if(!isAccountExists)
        {
            return AccountUtils.ACCOUNT_DOES_NOT_EXIST_MESSAGE;
        }

        User foundUser = userRepo.findByAccountNumber(enquiryRequest.getAccountNumber());
        return STR."\{foundUser.getFirstName()} \{foundUser.getLastName()} \{foundUser.getOtherName()}";
        
    }

    @Override
    public BankResponseDTO creditRequest(CreditDebitRequestDTO creditDebitRequestDTO) {
        //check if the account exists
        boolean isAccountExists = userRepo.existsByAccountNumber(creditDebitRequestDTO.getAccountNumber());
        if(!isAccountExists)
        {
            return BankResponseDTO.builder()
                    .responseCode(AccountUtils.ACCOUNT_DOES_NOT_EXIST_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_DOES_NOT_EXIST_MESSAGE)
                    .accountInfo(null)
                    .build();
        }

        User creditToUserAccount = userRepo.findByAccountNumber(creditDebitRequestDTO.getAccountNumber());
        //update the account_balance field in db
        creditToUserAccount.setAccountBalance(creditToUserAccount.getAccountBalance().add(creditDebitRequestDTO.getAmount()));
        userRepo.save(creditToUserAccount);
        return BankResponseDTO.builder()
                .responseCode(AccountUtils.ACCOUNT_CREDIT_SUCCESS)
                .responseMessage(AccountUtils.ACCOUNT_CREDIT_SUCCESS_MESSAGE)
                .accountInfo(AccountInfo.builder()
                        .accountName(STR."\{creditToUserAccount.getFirstName()} \{creditToUserAccount.getLastName()} \{creditToUserAccount.getOtherName()}")
                        .accountNumber(creditToUserAccount.getAccountNumber())
                        .accountBalance(creditToUserAccount.getAccountBalance()).build())
                .build();
    }

    public BankResponseDTO debitRequest(CreditDebitRequestDTO debitRequestDTO)
    {
        //check if the account exists
        boolean isAccountExists = userRepo.existsByAccountNumber(debitRequestDTO.getAccountNumber());
        if(!isAccountExists)
        {
            return BankResponseDTO.builder()
                    .responseCode(AccountUtils.ACCOUNT_DOES_NOT_EXIST_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_DOES_NOT_EXIST_MESSAGE)
                    .accountInfo(null)
                    .build();
        }


        BigDecimal amount = debitRequestDTO.getAmount();
        User debitFromUserAccount = userRepo.findByAccountNumber(debitRequestDTO.getAccountNumber());
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {

            return BankResponseDTO.builder()
                    .responseCode(AccountUtils.ACCOUNT_DEBIT_FAILED_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_DEBIT_FAILED_MESSAGE)
                    .accountInfo(AccountInfo.builder()
                            .accountName(STR."\{debitFromUserAccount.getFirstName()} \{debitFromUserAccount.getLastName()} \{debitFromUserAccount.getOtherName()}")
                            .accountBalance(debitFromUserAccount.getAccountBalance())
                            .accountNumber(debitRequestDTO.getAccountNumber())
                            .build())
                    .build();
        }
        BigDecimal currentBalance = debitFromUserAccount.getAccountBalance();
        if (currentBalance == null) {
            // defensive default
            currentBalance = BigDecimal.ZERO;
        }

        if(currentBalance.compareTo(amount)<0)
        {
            return BankResponseDTO.builder()
                    .responseMessage("Insufficent Balance")
                    .responseCode("500")
                    .accountInfo(AccountInfo.builder()
                            .accountNumber(debitRequestDTO.getAccountNumber())
                            .accountName(STR."\{debitFromUserAccount.getFirstName()} \{debitFromUserAccount.getLastName()}\{debitFromUserAccount.getOtherName()}")
                            .accountBalance(debitFromUserAccount.getAccountBalance())
                            .build())
                    .build();
        }

        debitFromUserAccount.setAccountBalance(debitFromUserAccount.getAccountBalance().subtract(debitRequestDTO.getAmount()));
        userRepo.save(debitFromUserAccount);

        return BankResponseDTO.builder()
                .responseCode(AccountUtils.ACCOUNT_DEBIT_SUCCESS_CODE)
                .responseMessage(AccountUtils.ACCOUNT_DEBIT_SUCCESS_MESSAGE)
                .accountInfo(AccountInfo.builder()
                        .accountName(STR."\{debitFromUserAccount.getFirstName()} \{debitFromUserAccount.getLastName()} \{debitFromUserAccount.getOtherName()}")
                        .accountBalance(debitFromUserAccount.getAccountBalance())
                        .accountNumber(debitRequestDTO.getAccountNumber())
                        .build())
                .build();
    }
}

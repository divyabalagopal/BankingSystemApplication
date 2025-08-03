package com.springprojects.banking_application.service;

import com.springprojects.banking_application.dto.TransactionDTO;
import com.springprojects.banking_application.entity.Transactions;
import org.springframework.stereotype.Service;


public interface TransactionService {

    void saveTransaction(TransactionDTO transactionDTO);
}

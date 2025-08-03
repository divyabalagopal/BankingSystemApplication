package com.springprojects.banking_application.service;

import com.springprojects.banking_application.dto.TransactionDTO;
import com.springprojects.banking_application.entity.Transactions;
import com.springprojects.banking_application.repository.TransactionalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements  TransactionService{

    @Autowired
    TransactionalRepository transactionalRepository;
    @Override
    public void saveTransaction(TransactionDTO transactionDTO) {

        Transactions transaction = Transactions.builder()
                .transactionalType(transactionDTO.getTransactionalType())
                .accountNumber(transactionDTO.getAccountNumber())
                .amount(transactionDTO.getAmount())
                .status("SUCCESS")
                .build();

        transactionalRepository.save(transaction);

        System.out.println("Transaction is successful");

    }
}

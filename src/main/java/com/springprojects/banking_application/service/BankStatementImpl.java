package com.springprojects.banking_application.service;

import com.springprojects.banking_application.entity.Transactions;
import com.springprojects.banking_application.repository.TransactionalRepository;
import jakarta.transaction.Transaction;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoLocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
@AllArgsConstructor
public class BankStatementImpl {

    /**
     * Retreieve the list of trasactions within a date range,
     * given an account number*/

    private TransactionalRepository transactionalRepository;

    public List<Transactions> generateStatement(String accountNumber, String startDate, String endDate)
    {
        //filter all records of this account number
        LocalDate start = LocalDate.parse(startDate, DateTimeFormatter.ISO_DATE);
        LocalDate end = LocalDate.parse(endDate,DateTimeFormatter.ISO_DATE);

        //Convert the string request into local date format
        List<Transactions> transactionsList = transactionalRepository.findAll().stream().filter(transactions -> transactions.getAccountNumber().equals(accountNumber))
                .filter(transactions -> transactions.getDateTime().isAfter(start.atStartOfDay())).filter(transactions -> transactions.getDateTime().isBefore(end.atStartOfDay())).toList();

        return transactionsList;

    }
    /**
     * Generate a pdf file of transactions
     * send the file via email
     */
}

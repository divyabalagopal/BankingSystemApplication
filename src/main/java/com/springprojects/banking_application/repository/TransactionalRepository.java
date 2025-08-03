package com.springprojects.banking_application.repository;

import com.springprojects.banking_application.entity.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionalRepository extends JpaRepository<Transactions, String> {

}

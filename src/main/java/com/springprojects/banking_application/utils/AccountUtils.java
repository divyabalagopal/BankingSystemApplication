package com.springprojects.banking_application.utils;

import java.time.Year;

public class AccountUtils {

    public static final String ACCOUNT_CREATION_CODE = "200";
    public static final String ACCOUNT_CREATION_MESSAGE="New account has been created successfully!";

    public static final String ACCOUNT_DOES_NOT_EXIST_CODE = "404";
    public static final String ACCOUNT_DOES_NOT_EXIST_MESSAGE="There is no account with the entered Account Number. Please re-enter a valid account number!";
    public static final String ACCOUNT_EXISTS_CODE = "001";
    public static final String ACCOUNT_EXISTS_MESSAGE="User Already has an account!";

    public static final String ACCOUNT_FOUND_CODE = "200";
    public static final String ACCOUNT_FOUND_MESSAGE="User successfully found";

    public static final String ACCOUNT_CREDIT_SUCCESS= "200";
    public static final String ACCOUNT_CREDIT_SUCCESS_MESSAGE="Amount credited to user's account successfully";

    public static final String ACCOUNT_DEBIT_SUCCESS_CODE= "200";
    public static final String ACCOUNT_DEBIT_SUCCESS_MESSAGE="Amount debited from user's account successfully";

    public static final String ACCOUNT_DEBIT_FAILED_CODE= "500";
    public static final String ACCOUNT_DEBIT_FAILED_MESSAGE="Amount failed to debit from user's account";

    /* always begin with current year and concatenate any random 6 digits with it*/

    public static String generateAccountNumber(){

        Year currentYear = Year.now();
        int min = 100000;
        int max =999999;

        //Generate a random number between min & max defined

        int randomNumber = (int) Math.floor(Math.random() *(max-min+1) + min);
        //convert the currentYear & randomNumber to strings and concatenate them

        String year = String.valueOf(currentYear);
        String randomNumberinString = String.valueOf(randomNumber);
        StringBuilder accountNumber = new StringBuilder();
        return accountNumber.append(year).append(randomNumber).toString();

    }
}

package com.springprojects.banking_application.utils;

import java.time.Year;

public class AccountUtils {

    public static final String ACCOUNT_CREATION_CODE = "200";
    public static final String ACCOUNT_CREATION_MESSAGE="New account has been created successfully!";

    public static final String ACCOUNT_EXISTS_CODE = "001";
    public static final String ACCOUNT_EXISTS_MESSAGE="User Already has an account!";

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

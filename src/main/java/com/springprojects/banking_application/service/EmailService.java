package com.springprojects.banking_application.service;

import com.springprojects.banking_application.dto.EmailDetailsDTO;

public interface EmailService {

    void sendEmailAlert(EmailDetailsDTO emailDetailsDTO);
}

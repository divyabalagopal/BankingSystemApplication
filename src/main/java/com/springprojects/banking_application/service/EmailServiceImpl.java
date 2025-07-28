package com.springprojects.banking_application.service;

import com.springprojects.banking_application.dto.EmailDetailsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService{

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String senderEmail;

    @Override
    public void sendEmailAlert(EmailDetailsDTO emailDetailsDTO) {
        try{
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(senderEmail);
            mailMessage.setTo(emailDetailsDTO.getRecipient());
            mailMessage.setText(emailDetailsDTO.getMessageBody());
            mailMessage.setSubject(emailDetailsDTO.getSubject());

            javaMailSender.send(mailMessage);
            System.out.println("Mail sent successfully!");
        }
        catch (MailException e)
        {
            throw new RuntimeException(e);
        }
    }
}

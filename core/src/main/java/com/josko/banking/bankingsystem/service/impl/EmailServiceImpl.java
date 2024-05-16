package com.josko.banking.bankingsystem.service.impl;

import com.josko.banking.bankingsystem.presentation.dto.TransactionDTO;
import com.josko.banking.bankingsystem.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
@Slf4j
public class EmailServiceImpl implements EmailService {

    private static final JavaMailSender SENDER;
    
    static {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmailbla.com");
        mailSender.setPort(587);

        mailSender.setUsername("my.gmail@gmail.com");
        mailSender.setPassword("password");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        SENDER = mailSender;
    }
    
    @Override
    public void sendTransactionConfirmationEmail(String recipientEmail, TransactionDTO transaction, boolean successful, 
                                                 Double oldBalance, Double newBalance) {
        var text = getText(transaction, successful, oldBalance, newBalance);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@josko.com");
        message.setTo(recipientEmail);
        message.setSubject("Transaction confirmation");
        message.setText(text);

        try {
            SENDER.send(message);
        } catch (MailException e) {
            log.error("Failed to send a confirmation email.", e);
        }
    }

    private static String getText(TransactionDTO transaction, boolean successful, Double oldBalance, Double newBalance) {
        String status = successful ? "successfully" : "unsuccessfully";
        String action = newBalance > oldBalance ? "added" : "taken";
       
        return String.format("""
                Hello!
                
                The transaction with ID: %d has been processed %s,
                and the balance: %f has been %s from your account.
                
                Old balance: %f
                New balance: %f
                
                Regards,
                Your XYZ bank
                """, transaction.transactionId(), status, transaction.amount(), action,
                oldBalance, newBalance);
    }
}

package com.josko.banking.bankingsystem.service;

import com.josko.banking.bankingsystem.presentation.dto.TransactionDTO;

public interface EmailService {

    void sendTransactionConfirmationEmail(String recipientEmail, TransactionDTO transaction, boolean successful,
                                          Double oldBalance, Double newBalance);
}

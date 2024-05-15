package com.josko.banking.bankingsystem.service.record;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Data
@AllArgsConstructor
public class TransactionRecord implements LoadingRecord {
    private Double amount;
    private String message;
    private Instant timestamp;
    private Long senderAccountId;
    private Long receiverAccountId;
}

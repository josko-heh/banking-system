package com.josko.banking.bankingsystem.service.record;

import com.josko.banking.bankingsystem.persistence.entity.Account;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AccountRecord implements LoadingRecord {
    private String iban;
    private Account.AccountType type;
    private Long customerId;
}

package com.josko.banking.bankingsystem.service.record;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomerRecord implements LoadingRecord {
    private String name;
    private String address;
    private String email;
    private String phoneNumber;
}

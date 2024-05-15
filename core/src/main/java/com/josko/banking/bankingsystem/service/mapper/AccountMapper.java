package com.josko.banking.bankingsystem.service.mapper;

import com.josko.banking.bankingsystem.persistence.entity.Account;
import com.josko.banking.bankingsystem.presentation.dto.BasicAccountData;
import com.josko.banking.bankingsystem.service.record.AccountRecord;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class AccountMapper {

	public abstract Account fromRecord(AccountRecord accountRecord);

	public abstract List<BasicAccountData> toBasicDataList(Collection<Account> accounts);
}

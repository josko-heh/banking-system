package com.josko.banking.bankingsystem.service.mapper;

import com.josko.banking.bankingsystem.persistence.entity.Transaction;
import com.josko.banking.bankingsystem.presentation.dto.TransactionDTO;
import com.josko.banking.bankingsystem.service.record.TransactionRecord;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class TransactionMapper {

	public abstract Transaction fromRecord(TransactionRecord transactionRecord);
	
	public abstract Transaction fromDTO(TransactionDTO dto);
}

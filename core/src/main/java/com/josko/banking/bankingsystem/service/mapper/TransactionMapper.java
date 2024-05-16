package com.josko.banking.bankingsystem.service.mapper;

import com.josko.banking.bankingsystem.persistence.entity.Transaction;
import com.josko.banking.bankingsystem.presentation.dto.TransactionDTO;
import com.josko.banking.bankingsystem.service.record.TransactionRecord;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class TransactionMapper {

	public abstract Transaction fromRecord(TransactionRecord transactionRecord);
	
	public abstract Transaction fromDTO(TransactionDTO dto);
	
	@Mapping(source = "dto.senderAccount.accountId", target = "senderAccountId")
	@Mapping(source = "dto.receiverAccount.accountId", target = "receiverAccountId")
	public abstract TransactionDTO toDTO(Transaction dto);

	public abstract List<TransactionDTO> toDTOs(Collection<Transaction> entities);
}

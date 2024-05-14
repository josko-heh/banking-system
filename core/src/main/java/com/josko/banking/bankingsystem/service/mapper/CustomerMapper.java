package com.josko.banking.bankingsystem.service.mapper;

import com.josko.banking.bankingsystem.persistence.entity.Customer;
import com.josko.banking.bankingsystem.service.record.CustomerRecord;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class CustomerMapper {

	public abstract Customer fromRecord(CustomerRecord customerRecord);
}

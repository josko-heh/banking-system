package com.josko.banking.bankingsystem.service;

import com.josko.banking.bankingsystem.service.record.LoadingRecord;

public interface RecordCreationService<T extends LoadingRecord> {

	Class<T> getType();
	
	void create(T recordData);
}

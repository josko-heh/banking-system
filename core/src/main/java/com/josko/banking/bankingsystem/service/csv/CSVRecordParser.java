package com.josko.banking.bankingsystem.service.csv;

import com.josko.banking.bankingsystem.service.record.LoadingRecord;
import org.apache.commons.csv.CSVRecord;

interface CSVRecordParser<T extends LoadingRecord> {

	Class<T> getType();
	String getPath();

	T parse(CSVRecord csvRecord);
}

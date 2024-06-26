package com.josko.banking.bankingsystem.service.csv;

import com.josko.banking.bankingsystem.service.record.TransactionRecord;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
class CSVTransactionParser implements CSVRecordParser<TransactionRecord> {

	@Value("${data.path.csv.transaction}")
	private String csvPath;


	@Override
	public Class<TransactionRecord> getType() {
		return TransactionRecord.class;
	}

	@Override
	public String getPath() {
		return csvPath;
	}

	@Override
	public TransactionRecord parse(CSVRecord csvRecord) {
		Double amount = Double.parseDouble(csvRecord.get("amount"));
		String message = csvRecord.get("message");
		Instant timestamp = Instant.parse(csvRecord.get("timestamp"));
		Long senderAccountId = Long.parseLong(csvRecord.get("senderAccountId"));
		Long receiverAccountId = Long.parseLong(csvRecord.get("receiverAccountId"));

		return new TransactionRecord(amount, message, timestamp, senderAccountId, receiverAccountId);
	}
}

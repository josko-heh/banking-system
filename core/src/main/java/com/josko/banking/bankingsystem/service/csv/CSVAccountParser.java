package com.josko.banking.bankingsystem.service.csv;

import com.josko.banking.bankingsystem.persistence.entity.Account;
import com.josko.banking.bankingsystem.service.record.AccountRecord;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
class CSVAccountParser implements CSVRecordParser<AccountRecord> {

	@Value("${data.path.csv.account}")
	private String csvPath;


	@Override
	public Class<AccountRecord> getType() {
		return AccountRecord.class;
	}

	@Override
	public String getPath() {
		return csvPath;
	}

	@Override
	public AccountRecord parse(CSVRecord csvRecord) {
		String iban = csvRecord.get("iban");
		Account.AccountType type = Account.AccountType.valueOf(csvRecord.get("type"));
		Long customerId = Long.parseLong(csvRecord.get("customerId"));

		return new AccountRecord(iban, type, customerId);
	}
}

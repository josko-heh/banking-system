package com.josko.banking.bankingsystem.service.csv;

import com.josko.banking.bankingsystem.service.record.CustomerRecord;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
class CSVCustomerParser implements CSVRecordParser<CustomerRecord> {

	@Value("${data.path.csv.customer}")
	private String csvPath;


	@Override
	public Class<CustomerRecord> getType() {
		return CustomerRecord.class;
	}

	@Override
	public String getPath() {
		return csvPath;
	}

	@Override
	public CustomerRecord parse(CSVRecord csvRecord) {
		String name = csvRecord.get("name");
		String address = csvRecord.get("address");
		String email = csvRecord.get("email");
		String phoneNumber = csvRecord.get("phoneNumber");

		return new CustomerRecord(name, address, email, phoneNumber);
	}
}

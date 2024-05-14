package com.josko.banking.bankingsystem.service.impl;

import com.josko.banking.bankingsystem.service.CustomerService;
import com.josko.banking.bankingsystem.service.DataLoader;
import com.josko.banking.bankingsystem.service.record.CustomerRecord;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

@Service
@Slf4j
@RequiredArgsConstructor
class CsvCustomerDataLoader implements DataLoader {

	private static final CSVFormat CSV_FORMAT = CSVFormat.DEFAULT.builder()
			.setHeader()
			.setSkipHeaderRecord(true)
			.build();
	
	@Value("${data.path.csv}")
	private String csvPath;

	private final CustomerService customerService;

	@Override
	public void load() {
		try (Reader reader = new FileReader(csvPath);
			 CSVParser csvParser = new CSVParser(reader, CSV_FORMAT)) {
			
			for (CSVRecord csvRecord : csvParser) {
				CustomerRecord customerData = parse(csvRecord);
				customerService.create(customerData);
			}
			
			log.debug("Loaded customer data from csv file.");
		} catch (IOException e) {
			log.error("Error loading customer data from csv file.", e);
		}
	}

	private CustomerRecord parse(CSVRecord csvRecord) {
		Long customerId = Long.parseLong(csvRecord.get(0));
		String name = csvRecord.get(1);
		String address = csvRecord.get(2);
		String email = csvRecord.get(3);
		String phoneNumber = csvRecord.get(4);

		return new CustomerRecord(customerId, name, address, email, phoneNumber);
	}
}

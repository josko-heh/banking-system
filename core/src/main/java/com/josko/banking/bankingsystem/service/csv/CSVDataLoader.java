package com.josko.banking.bankingsystem.service.csv;

import com.josko.banking.bankingsystem.service.DataLoader;
import com.josko.banking.bankingsystem.service.RecordCreationService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;

@Component
@Slf4j
public class CSVDataLoader implements DataLoader {

	private static final CSVFormat CSV_FORMAT = CSVFormat.DEFAULT.builder()
			.setHeader()
			.setSkipHeaderRecord(true)
			.build();

	private final List<RecordCreationService> recordCreationServices;
	private final Map<Class<?>, CSVRecordParser<?>> typeToParser;

	public CSVDataLoader(List<RecordCreationService> recordCreationServices, Set<CSVRecordParser<?>> parsers) {
		this.recordCreationServices = recordCreationServices;
		this.typeToParser = parsers.stream()
				.collect(toMap(CSVRecordParser::getType, Function.identity()));
	}

	@Override
	public void load() {
		recordCreationServices.forEach(creationService -> {
			Class type = creationService.getType();
			var parser = typeToParser.get(type);
			
			if (parser == null) {
				throw new IllegalStateException("No parser found for type " + type.getSimpleName());
			}
			
			try (Reader reader = new FileReader(parser.getPath());
				 CSVParser csvParser = new CSVParser(reader, CSV_FORMAT)) {
				
				for (CSVRecord csvRecord : csvParser) {
					var parsed = parser.parse(csvRecord);
					creationService.create(parsed);
				}
			} catch (IOException e) {
				log.error("Error loading {} data from csv file.", type.getSimpleName(), e);
			}
		});
	}
}

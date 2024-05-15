package com.josko.banking.bankingsystem.startup;

import com.josko.banking.bankingsystem.service.DataInitializer;
import com.josko.banking.bankingsystem.service.DataLoader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class Initializer {

	private final DataLoader dataLoader;
	private final DataInitializer dataInitializer;
	
	@EventListener
	public void onApplicationEvent(ContextRefreshedEvent event) {
		log.info("Loading data.");
		dataLoader.load();
		
		log.info("Initializing data.");
		dataInitializer.initialize();
	}
}

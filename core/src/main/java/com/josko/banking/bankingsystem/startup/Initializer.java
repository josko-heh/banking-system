package com.josko.banking.bankingsystem.startup;

import com.josko.banking.bankingsystem.service.DataLoader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@Slf4j
@RequiredArgsConstructor
public class Initializer {

	private final Set<DataLoader> dataLoaders;
	
	@EventListener
	public void onApplicationEvent(ContextRefreshedEvent event) {
		log.info("Application context refreshed. Loading data.");
		dataLoaders.forEach(DataLoader::load);
	}
}

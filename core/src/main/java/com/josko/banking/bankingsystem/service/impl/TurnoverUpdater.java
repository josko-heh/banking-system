package com.josko.banking.bankingsystem.service.impl;

import com.josko.banking.bankingsystem.persistence.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.temporal.TemporalAdjusters;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class TurnoverUpdater {

    private final AccountRepository accountRepository;

    @Scheduled(cron = "${turnover.cronExpression:0 0 0 1 */1 *}")
    public void updateTurnovers() {
        log.info("Updating turnovers.");

        Instant startOfMonth = Instant.now()
                .atZone(ZoneOffset.UTC)
                .with(TemporalAdjusters.firstDayOfMonth())
                .toLocalDate()
                .atStartOfDay(ZoneOffset.UTC)
                .toInstant();

        accountRepository.updatePastMonthTurnover(startOfMonth);
    }

}

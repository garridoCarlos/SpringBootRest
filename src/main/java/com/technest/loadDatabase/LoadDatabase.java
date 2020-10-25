package com.technest.loadDatabase;

import java.math.BigDecimal;
import java.util.Currency;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.technest.model.Account;
import com.technest.repository.AccountRepository;

@Configuration
class LoadDatabase {

  private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

  @Bean
  CommandLineRunner initDatabase(AccountRepository repository) {

    return args -> {
      log.info("Preloading " + repository.save(new Account("AccountPreLoaded1", Currency.getInstance("EUR"), new BigDecimal("20.00"), false)));
      log.info("Preloading " + repository.save(new Account("AccountPreLoaded2", Currency.getInstance("EUR"), new BigDecimal("20.00"), false)));
      log.info("Preloading " + repository.save(new Account("AccountPreLoaded3", Currency.getInstance("EUR"), new BigDecimal("20.00"), true)));
    };
  }
}

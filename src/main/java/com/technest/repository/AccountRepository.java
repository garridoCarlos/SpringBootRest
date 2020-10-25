package com.technest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.technest.model.Account;

public interface AccountRepository extends JpaRepository <Account, Long>{
	List<Account> findByAccountName(String name);
	
}

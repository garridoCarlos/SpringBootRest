package com.technest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.technest.model.Account;

public interface AccountRepository extends JpaRepository <Account, Long>{
	
}

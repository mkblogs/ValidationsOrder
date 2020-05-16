package com.tech.mkblogs.account.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.tech.mkblogs.account.model.Account;

@Repository
public interface AccountRepository 
			extends JpaRepository<Account, Integer>,JpaSpecificationExecutor<Account> {

	List<Account> findByAccountName(String accountName);
	List<Account> findByAmount(BigDecimal amount);
}

package com.tech.mkblogs.account.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.stream.StreamSupport;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.tech.mkblogs.account.filter.FilterDTO;
import com.tech.mkblogs.account.model.Account;

@SpringBootTest
@EnableJpaAuditing
public class RepositoryTestcase {

	@Autowired
	AccountRepository repository;
	
	@Test
	public void testSaveObject() throws Exception {
		Account account = new Account();
		account.setAccountName("testValidation");
		account.setAccountType("Savings");
		account.setAmount(new BigDecimal(100));
		repository.save(account);
	}
	
	@Test
	public void testSave_UpdateObject() throws Exception {
		Account account = new Account();
		account.setAccountName("testValidationNew");
		account.setAccountType("Savings");
		account.setAmount(new BigDecimal(100));
		repository.save(account);
		account.setAccountName("testUpdate");
		repository.save(account);
	}
	
	@Test
	public void testUpdateObject() throws Exception {
		Integer updateId = 2;
		Optional<Account> optionalAccount = repository.findById(updateId);
		if(optionalAccount.isPresent()) {
			Account account = optionalAccount.get();
			System.out.println(account);
			account.setAccountType("ABCupdate");
			account.setAmount(new BigDecimal(10000));
			System.out.println(account);
			repository.save(account);
		}else {
			System.out.println("Record Not Found");
		}
	}
	
	@Test
	public void testDeleteObject() throws Exception {
		Account account = new Account();
		account.setAccountName("testAudit");
		account.setAccountType("Savings");
		account.setAmount(new BigDecimal(100));
		repository.save(account);
		repository.delete(account);
	}
	
	@Test	
	public void testCount() throws Exception {
		Account account = new Account();
		account.setAccountName("testAudit");
		account.setAccountType("Savings");
		account.setAmount(new BigDecimal(100));
		repository.save(account);
		assertEquals(1, repository.count());
	}
	
	@Test
	public void fetchAllData() throws Exception {
		AccountSpecifications specifications = new AccountSpecifications();
		Iterable<Account> accountList = repository.findAll(specifications);
		long count = StreamSupport.stream(accountList.spliterator(), false).count();
		if(count > 0) {
			System.out.println("Size : "+accountList.spliterator().getExactSizeIfKnown());
			accountList.forEach(n -> System.out.println(n)); 
		}
	}
	
	@Test
	public void fetchData() throws Exception {
		String name = "test";
		String type = "test";
		BigDecimal amount = new BigDecimal("123");
		
		
		FilterDTO dto = new FilterDTO();
		dto.setAccountName(name);
		dto.setAccountType(type);
		dto.setAmount(amount.toPlainString());
		
		AccountSpecifications specifications = new AccountSpecifications(dto);
		
		Iterable<Account> accountList = repository.findAll(specifications);
		long count = StreamSupport.stream(accountList.spliterator(), false).count();
		if(count > 0) {
			System.out.println("Size : "+accountList.spliterator().getExactSizeIfKnown());
			accountList.forEach(n -> System.out.println(n)); 
		}
	}
}

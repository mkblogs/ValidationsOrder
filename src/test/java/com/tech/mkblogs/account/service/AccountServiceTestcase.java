package com.tech.mkblogs.account.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.StreamSupport;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.tech.mkblogs.account.dto.AccountDTO;
import com.tech.mkblogs.account.filter.FilterDTO;
import com.tech.mkblogs.response.ErrorObject;
import com.tech.mkblogs.response.ResponseDTO;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
@EnableJpaAuditing
public class AccountServiceTestcase {

	@Autowired
	private AccountService service;
	
	@Test
	public void testSaveObject() throws Exception {
		AccountDTO accountDTO = new AccountDTO();
		accountDTO.setName("Servicename");
		accountDTO.setType("Savings");
		accountDTO.setAmount(new BigDecimal("1234"));
		ResponseDTO<AccountDTO, ErrorObject> responseDTO = service.saveAccount(accountDTO);
		log.info("Is Error :: "+responseDTO.getIsError());
		if(!responseDTO.getIsError()) {
			log.info(responseDTO.getSuccessObject());
		}else {
			log.info(responseDTO.getErrorObject());
		}
	}
	
	
	@Test
	public void testSave_UpdateObject() throws Exception {
		AccountDTO accountDTO = new AccountDTO();
		accountDTO.setName("ServicenameUpdate");
		accountDTO.setType("Savings");
		accountDTO.setAmount(new BigDecimal("1234"));
		ResponseDTO<AccountDTO, ErrorObject> responseDTO = service.saveAccount(accountDTO);
		log.info("Is Error :: "+responseDTO.getIsError());
		if(responseDTO.getIsError()) {
			log.info(responseDTO.getErrorObject());
		}else {
			accountDTO = responseDTO.getSuccessObject();
		}
		accountDTO.setName("testAuditUpdate");
		responseDTO = service.updateAccount(accountDTO);
		log.info("Is Error :: "+responseDTO.getIsError());
		if(responseDTO.getIsError()) {
			log.info(responseDTO.getErrorObject());
		}else {
			accountDTO = responseDTO.getSuccessObject();
		}
		log.info(accountDTO);
	}
	
	@Test
	public void testUpdateObject() throws Exception {
		Integer updateId = 15;
		ResponseDTO<AccountDTO, ErrorObject> responseDTO =  service.getAccount(updateId);
		
		if(!responseDTO.getIsError()) {
			AccountDTO accountDTO = responseDTO.getSuccessObject();
			accountDTO.setType("ABCupdate");
			accountDTO.setAmount(new BigDecimal(10000));
			responseDTO = service.updateAccount(accountDTO);
			log.info("Is Error :: "+responseDTO.getIsError());
			if(responseDTO.getIsError()) {
				log.info(responseDTO.getErrorObject());
			}else {
				accountDTO = responseDTO.getSuccessObject();
			}
			log.info(accountDTO);
			
		}else {
			log.info(responseDTO.getErrorObject());
		}
	}
	
	
	@Test
	public void testDeleteObject() throws Exception {
		AccountDTO accountDTO = new AccountDTO();
		accountDTO.setName("Servicename");
		accountDTO.setType("Savings");
		accountDTO.setAmount(new BigDecimal("1234"));
		ResponseDTO<AccountDTO, ErrorObject> responseDTO = service.saveAccount(accountDTO);
		log.info("Is Error :: "+responseDTO.getIsError());
		if(!responseDTO.getIsError()) {
			accountDTO = responseDTO.getSuccessObject();
			ResponseDTO<String, ErrorObject> responseObject = service.deleteAccount(accountDTO.getAccountId());
			if(responseObject.getIsError()) {
				log.info(responseObject.getErrorObject());
			}else {
				log.info(responseObject.getSuccessObject());
			}
		}else {
			log.info(responseDTO.getErrorObject());
		}
	}
	
	
	
	@Test
	public void fetchAllData() throws Exception {
		ResponseDTO<List<AccountDTO>, ErrorObject> responseDTO = new ResponseDTO<List<AccountDTO>, ErrorObject>();
		responseDTO = service.getAllData();
		List<AccountDTO> accountList = responseDTO.getSuccessObject();
		long count = StreamSupport.stream(accountList.spliterator(), false).count();
		if(count > 0) {
			log.info("Size : "+accountList.spliterator().getExactSizeIfKnown());
			accountList.forEach(n -> log.info(n)); 
		}
	}
	
	@Test
	public void fetchData() throws Exception {
		String name = "Servicename";
		String type = "";
		BigDecimal amount = new BigDecimal("1234");
		
		FilterDTO dto = new FilterDTO();
		dto.setAccountName(name);
		dto.setAccountType(type);
		dto.setAmount(amount.toPlainString());
		
		ResponseDTO<List<AccountDTO>, ErrorObject> responseDTO = new ResponseDTO<List<AccountDTO>, ErrorObject>();
		responseDTO = service.search(dto);
		
		List<AccountDTO> accountList = responseDTO.getSuccessObject();
		long count = StreamSupport.stream(accountList.spliterator(), false).count();
		if(count > 0) {
			log.info("Size : "+accountList.spliterator().getExactSizeIfKnown());
			accountList.forEach(n -> log.info(n)); 
		}
	}
	
}

package com.tech.mkblogs.account.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tech.mkblogs.account.dto.AccountDTO;
import com.tech.mkblogs.account.filter.FilterDTO;
import com.tech.mkblogs.account.mapper.AccountMapper;
import com.tech.mkblogs.account.model.Account;
import com.tech.mkblogs.account.repository.AccountRepository;
import com.tech.mkblogs.account.repository.AccountSpecifications;
import com.tech.mkblogs.response.ErrorObject;
import com.tech.mkblogs.response.ResponseDTO;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@Transactional
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountRepository repository;
	
	
	@Override
	public ResponseDTO<AccountDTO,ErrorObject> saveAccount(AccountDTO accountDTO){
		log.info("Starting the saveAccount() method ");
		ResponseDTO<AccountDTO,ErrorObject> responseDTO = new ResponseDTO<AccountDTO,ErrorObject>();
		try {
			Account account = AccountMapper.INSTANCE.toAccount(accountDTO);
			account = repository.save(account);
			accountDTO = AccountMapper.INSTANCE.toAccountDTO(account);
			accountDTO.setAccountId(account.getId());
			log.info("Generated Primary Key : " + account.getId());
			responseDTO.setIsError(false);
			responseDTO.setSuccessObject(accountDTO);
		} catch (Exception e) {
			e.printStackTrace();
			responseDTO.setIsError(true);
			ErrorObject errorObject = constructResponseErrorObjct("SAVE_ERROR", e.getMessage());
			responseDTO.setSuccessObject(null);
			responseDTO.setErrorObject(errorObject);
		}
		log.info("Ended the saveAccount() method ");
		return responseDTO;
	}
	
	@Override
	public ResponseDTO<AccountDTO,ErrorObject> updateAccount(AccountDTO accountDTO) {
		log.info("Starting the updateAccount() method ");
		ResponseDTO<AccountDTO,ErrorObject> responseDTO = new ResponseDTO<AccountDTO,ErrorObject>();
		try {
			Optional<Account> dbAccountOptional = repository.findById(accountDTO.getAccountId());
			if(dbAccountOptional.isPresent()) {
				Account dbAccount = dbAccountOptional.get();
				dbAccount = AccountMapper.INSTANCE.toAccountForUpdate(accountDTO, dbAccount);
				dbAccount = repository.save(dbAccount);
				accountDTO = AccountMapper.INSTANCE.toAccountDTO(dbAccount);
				responseDTO.setIsError(false);
				responseDTO.setSuccessObject(accountDTO);
			}else {
				String errorMsg = ("Entity Not Found " + accountDTO.getAccountId());
				ErrorObject errorObject = constructResponseErrorObjct("UPDATE_ERROR", errorMsg);
				responseDTO.setIsError(true);
				responseDTO.setSuccessObject(null);
				responseDTO.setErrorObject(errorObject);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			ErrorObject errorObject = constructResponseErrorObjct("UPDATE_ERROR", e.getMessage());
			responseDTO.setIsError(true);
			responseDTO.setSuccessObject(null);
			responseDTO.setErrorObject(errorObject);
		}
		log.info("Ended the updateAccount() method ");
		return responseDTO;
	}
	
	@Override
	public ResponseDTO<AccountDTO,ErrorObject> getAccount(Integer id) {
		AccountDTO accountDTO = null;
		log.info("Starting the getAccount() method ");
		ResponseDTO<AccountDTO,ErrorObject> responseDTO = new ResponseDTO<AccountDTO,ErrorObject>();
		try {
			Optional<Account> dbObjectExists = repository.findById(id);
			if(dbObjectExists.isPresent()) {
				Account dbAccount = dbObjectExists.get();
				accountDTO = AccountMapper.INSTANCE.toAccountDTO(dbAccount);
				responseDTO.setIsError(false);
				responseDTO.setSuccessObject(accountDTO);
			}else {
				String errorMsg = ("Entity Not Found " + id);
				ErrorObject errorObject = constructResponseErrorObjct("GET_ERROR", errorMsg);
				responseDTO.setIsError(true);
				responseDTO.setSuccessObject(null);
				responseDTO.setErrorObject(errorObject);
			}
		} catch (Exception e) {			
			String errorMsg = ("getAccount() Exception, cause :: " + e.getMessage());
			ErrorObject errorObject = constructResponseErrorObjct("GET_ERROR", errorMsg);
			responseDTO.setIsError(true);
			responseDTO.setSuccessObject(null);
			responseDTO.setErrorObject(errorObject);
		}
		log.info("Ended the getAccount() method ");
		return responseDTO;
	}
	
	@Override
	public ResponseDTO<String,ErrorObject> deleteAccount(Integer accountId) {
		String result = "";
		ResponseDTO<String,ErrorObject> responseDTO = new ResponseDTO<String,ErrorObject>();
		try {
			log.info("Starting the deleteAccount() method ");
			Optional<Account> optionalAccount = repository.findById(accountId);
			if(optionalAccount.isPresent()) {
				Account account = optionalAccount.get();
				repository.delete(account);
				result = "Success";
				responseDTO.setIsError(false);
				responseDTO.setSuccessObject(result);
			}else {
				String errorMsg = ("Entity Not Found " + accountId);
				ErrorObject errorObject = constructResponseErrorObjct("DELETE_ERROR", errorMsg);
				responseDTO.setIsError(true);
				responseDTO.setSuccessObject(null);
				responseDTO.setErrorObject(errorObject);
			}
		}catch(Exception e) {			
			String errorMsg = ("deleteAccount() Exception, cause :: " + e.getMessage());
			ErrorObject errorObject = constructResponseErrorObjct("DELETE_ERROR", errorMsg);
			responseDTO.setIsError(true);
			responseDTO.setSuccessObject(null);
			responseDTO.setErrorObject(errorObject);
		}
		log.info("Ended the deleteAccount() method ");
		return responseDTO;
	}
	
	@Override
	public ResponseDTO<List<AccountDTO>,ErrorObject>  findByAccountName(String accountName) {
		ResponseDTO<List<AccountDTO>,ErrorObject> responseDTO = new ResponseDTO<List<AccountDTO>, ErrorObject>();
		List<AccountDTO> list = new ArrayList<AccountDTO>();
		log.info("Starting the findByAccountName() method ");
		try {
			List<Account> dbList =  repository.findByAccountName(accountName);
			for(Account dbAccount:dbList) {
				AccountDTO accountDTO = AccountMapper.INSTANCE.toAccountDTO(dbAccount);
				list.add(accountDTO);
			}
			responseDTO.setIsError(false);
			responseDTO.setSuccessObject(list);
			responseDTO.setErrorObject(null);
		} catch (Exception e) {
			String errorMsg = ("findByAccountName() Exception, cause :: " + e.getMessage());
			ErrorObject errorObject = constructResponseErrorObjct("FIND_BY_ACCOUNT_NAME_ERROR", errorMsg);
			responseDTO.setIsError(true);
			responseDTO.setSuccessObject(null);
			responseDTO.setErrorObject(errorObject);
		}
		log.info("Ended the findByAccountName() method ");
		return responseDTO;
	}
	
	
	@Override
	public ResponseDTO<List<AccountDTO>,ErrorObject> getAllData() {
		ResponseDTO<List<AccountDTO>,ErrorObject> responseDTO = new ResponseDTO<List<AccountDTO>, ErrorObject>();
		List<AccountDTO> list = new ArrayList<AccountDTO>();
		log.info("Starting the getAllData() method ");
		try {
			List<Account> dbList = repository.findAll();
			for(Account dbAccount:dbList) {
				AccountDTO accountDTO = AccountMapper.INSTANCE.toAccountDTO(dbAccount);
				list.add(accountDTO);
			}
			responseDTO.setIsError(false);
			responseDTO.setSuccessObject(list);
			responseDTO.setErrorObject(null);
		} catch (Exception e) {
			String errorMsg = ("getAllData() Exception, cause :: " + e.getMessage());
			ErrorObject errorObject = constructResponseErrorObjct("GET_ALL_DATA", errorMsg);
			responseDTO.setIsError(true);
			responseDTO.setSuccessObject(null);
			responseDTO.setErrorObject(errorObject);
		}
		log.info("Ended the getAllData() method ");
		return responseDTO;
	}
	
	
	
	
	@Override
	public ResponseDTO<List<AccountDTO>,ErrorObject> search(FilterDTO dto) {
		ResponseDTO<List<AccountDTO>,ErrorObject> responseDTO = new ResponseDTO<List<AccountDTO>, ErrorObject>();
		List<AccountDTO> list = new ArrayList<AccountDTO>();
		log.info("Starting the search() method ");
		try {
			List<Account> dbList = repository.findAll(new AccountSpecifications(dto));
			for(Account dbAccount:dbList) {
				AccountDTO accountDTO = AccountMapper.INSTANCE.toAccountDTO(dbAccount);
				list.add(accountDTO);
			}
			responseDTO.setIsError(false);
			responseDTO.setSuccessObject(list);
			responseDTO.setErrorObject(null);
		} catch (Exception e) {
			String errorMsg = ("search() Exception, cause :: " + e.getMessage());
			ErrorObject errorObject = constructResponseErrorObjct("SEARCH", errorMsg);
			responseDTO.setIsError(true);
			responseDTO.setSuccessObject(null);
			responseDTO.setErrorObject(errorObject);
		}
		log.info("Ended the search() method ");
		return responseDTO;
	}
}

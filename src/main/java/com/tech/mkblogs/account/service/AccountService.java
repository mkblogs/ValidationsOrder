package com.tech.mkblogs.account.service;

import java.util.List;

import com.tech.mkblogs.account.dto.AccountDTO;
import com.tech.mkblogs.account.filter.FilterDTO;
import com.tech.mkblogs.response.ErrorObject;
import com.tech.mkblogs.response.ResponseDTO;

public interface AccountService {

	public ResponseDTO<AccountDTO,ErrorObject> saveAccount(AccountDTO accountDTO);
	public ResponseDTO<AccountDTO,ErrorObject> updateAccount(AccountDTO accountDTO);
	public ResponseDTO<AccountDTO,ErrorObject> getAccount(Integer id);
	public ResponseDTO<String,ErrorObject> deleteAccount(Integer accountId);
	
	public ResponseDTO<List<AccountDTO>,ErrorObject> findByAccountName(String accountName);
	public ResponseDTO<List<AccountDTO>,ErrorObject> getAllData();
	public ResponseDTO<List<AccountDTO>,ErrorObject> search(FilterDTO dto);
	
	
	public default ErrorObject constructResponseErrorObjct(String errorCode,String errorMessage) {
		ErrorObject errorObject = new ErrorObject();
		errorObject.setErrorCode(errorCode);
		errorObject.setErrorField(errorCode);
		errorObject.setErrorMsg(errorMessage);
		return errorObject;
	}
}

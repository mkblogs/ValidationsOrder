package com.tech.mkblogs.user.service;

import java.util.List;
import java.util.stream.StreamSupport;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.tech.mkblogs.response.ErrorObject;
import com.tech.mkblogs.response.ResponseDTO;
import com.tech.mkblogs.user.dto.UserDTO;
import com.tech.mkblogs.user.filter.UserFilterDTO;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
@EnableJpaAuditing
public class ServiceTestcase {

	@Autowired
	private UserService service;
	
	@Test
	public void testSaveObject() throws Exception {
		UserDTO userDTO = new UserDTO();
		userDTO.setUsername("Test");
		userDTO.setPassword("password");
		userDTO.setFirstName("Firstname");
		userDTO.setLastName("lastname");
		ResponseDTO<UserDTO, ErrorObject> responseDTO = service.saveUser(userDTO);
		log.info("Is Error :: "+responseDTO.getIsError());
		if(!responseDTO.getIsError()) {
			log.info(responseDTO.getSuccessObject());
		}else {
			log.info(responseDTO.getErrorObject());
		}
	}
	
	
	@Test
	public void testSave_UpdateObject() throws Exception {
		UserDTO userDTO = new UserDTO();
		userDTO.setUsername("Test");
		userDTO.setPassword("password");
		userDTO.setFirstName("Firstname");
		userDTO.setLastName("lastname");
		ResponseDTO<UserDTO, ErrorObject> responseDTO = service.saveUser(userDTO);
		log.info("Is Error :: "+responseDTO.getIsError());
		if(responseDTO.getIsError()) {
			log.info(responseDTO.getErrorObject());
		}else {
			userDTO = responseDTO.getSuccessObject();
		}
		userDTO.setFirstName("testAuditUpdate");
		responseDTO = service.updateUser(userDTO);
		log.info("Is Error :: "+responseDTO.getIsError());
		if(responseDTO.getIsError()) {
			log.info(responseDTO.getErrorObject());
		}else {
			userDTO = responseDTO.getSuccessObject();
		}
		log.info(userDTO);
	}
	
	
	@Test
	public void testUpdateObject() throws Exception {
		Integer updateId = 5;
		ResponseDTO<UserDTO, ErrorObject> responseDTO =  service.getUser(updateId);
		
		if(!responseDTO.getIsError()) {
			UserDTO dto = responseDTO.getSuccessObject();
			dto.setFirstName("update");
			dto.setLastName("update");
			responseDTO = service.updateUser(dto);
			log.info("Is Error :: "+responseDTO.getIsError());
			if(responseDTO.getIsError()) {
				log.info(responseDTO.getErrorObject());
			}else {
				dto = responseDTO.getSuccessObject();
			}
			log.info(dto);
		}else {
			log.info(responseDTO.getErrorObject());
		}
	}
	
	
	@Test
	public void testDeleteObject() throws Exception {
		UserDTO userDTO = new UserDTO();
		userDTO.setUsername("Test");
		userDTO.setPassword("password");
		userDTO.setFirstName("Firstname");
		userDTO.setLastName("lastname");
		ResponseDTO<UserDTO, ErrorObject> responseDTO = service.saveUser(userDTO);
		log.info("Is Error :: "+responseDTO.getIsError());
		if(!responseDTO.getIsError()) {
			userDTO = responseDTO.getSuccessObject();
			ResponseDTO<String, ErrorObject> responseObject = service.deleteUser(userDTO.getId());
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
		ResponseDTO<List<UserDTO>, ErrorObject> responseDTO = new ResponseDTO<List<UserDTO>, ErrorObject>();
		responseDTO = service.getAllData();
		List<UserDTO> accountList = responseDTO.getSuccessObject();
		long count = StreamSupport.stream(accountList.spliterator(), false).count();
		if(count > 0) {
			log.info("Size : "+accountList.spliterator().getExactSizeIfKnown());
			accountList.forEach(n -> log.info(n)); 
		}
	}
	
	
	@Test
	public void fetchData() throws Exception {
		String username = "test";
		String firstName = "";
		String lastName = "";
		
		UserFilterDTO dto = new UserFilterDTO();
		dto.setUsername(username);
		dto.setFirstName(firstName);
		dto.setLastName(lastName);
		
		ResponseDTO<List<UserDTO>, ErrorObject> responseDTO = new ResponseDTO<List<UserDTO>, ErrorObject>();
		responseDTO = service.search(dto);
		
		List<UserDTO> accountList = responseDTO.getSuccessObject();
		long count = StreamSupport.stream(accountList.spliterator(), false).count();
		if(count > 0) {
			log.info("Size : "+accountList.spliterator().getExactSizeIfKnown());
			accountList.forEach(n -> log.info(n)); 
		}
	}
	
}

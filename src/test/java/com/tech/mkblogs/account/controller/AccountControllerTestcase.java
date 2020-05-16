package com.tech.mkblogs.account.controller;

import java.util.List;
import java.util.stream.StreamSupport;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.tech.mkblogs.account.dto.AccountDTO;
import com.tech.mkblogs.response.ErrorObject;
import com.tech.mkblogs.response.ResponseDTO;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@AutoConfigureMockMvc
@SuppressWarnings("unchecked")
@Log4j2
@EnableJpaAuditing
public class AccountControllerTestcase {

	@Autowired
	private MockMvc mvc;
	
	@Autowired
	ObjectMapper objectMapper;
	 
	@Test
	public void testSaveObject() throws Exception {
		
		String url = "/api/account";
		String jsonString = "{" + " \"name\":\"Tes12\"," 
								+ " \"type\": \"Savings\"," 
								+ " \"amount\": 1000"
								+ " } ";
		log.info(jsonString);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(url).accept(MediaType.APPLICATION_JSON)
				.content(jsonString).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mvc.perform(requestBuilder).andReturn();
		objectMapper.registerModule(new JavaTimeModule());
		String responeString = result.getResponse().getContentAsString();
		log.info(responeString);
		if(!StringUtils.isEmpty(responeString)) {
			ResponseDTO<AccountDTO, ErrorObject> responseDTO = objectMapper.readValue(responeString, ResponseDTO.class);
			if(!responseDTO.getIsError()) {
				log.info(responseDTO.getSuccessObject());
			}else {
				log.info(responseDTO.getErrorObject());
			}
		}else {
			System.out.println("Empty Response String");
		}
	}
	
	
	@Test
	public void testUpdateObject() throws Exception {
		String url = "/api/account";
		String jsonString = "{" + " \"accountId\": 19,"
								+ " \"name\":\"TestControllerUpdate\"," 
								+ " \"type\": \"Savings\"," 
								+ " \"amount\": 120"
								+ " } ";
		System.out.println(jsonString);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.put(url).accept(MediaType.APPLICATION_JSON)
				.content(jsonString).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mvc.perform(requestBuilder).andReturn();
		String responeString = result.getResponse().getContentAsString();
		log.info(responeString);
		if(!StringUtils.isEmpty(responeString)) {
			ResponseDTO<AccountDTO, ErrorObject> responseDTO = objectMapper.readValue(responeString, ResponseDTO.class);
			if(!responseDTO.getIsError()) {
				log.info(responseDTO.getSuccessObject());
			}else {
				log.info(responseDTO.getErrorObject());
			}
		}else {
			System.out.println("Empty Response String");
		}
	}
	
	@Test
	public void testDeleteObject() throws Exception {
		String url = "/api/account/20";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete(url).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mvc.perform(requestBuilder).andReturn();
		String responeString = result.getResponse().getContentAsString();
		log.info(responeString);
		objectMapper.registerModule(new JavaTimeModule());
		if(!StringUtils.isEmpty(responeString)) {
			ResponseDTO<String, ErrorObject> responseDTO = objectMapper.readValue(responeString, ResponseDTO.class);
			if(!responseDTO.getIsError()) {
				log.info(responseDTO.getSuccessObject());
			}else {
				log.info(responseDTO.getErrorObject());
			}
		}else {
			System.out.println("Empty Response String");
		}
	}
	
	@Test
	public void testGetObject() throws Exception {
		String url = "/api/account/20";

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(url).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mvc.perform(requestBuilder).andReturn();
		String responeString = result.getResponse().getContentAsString();
		log.info(responeString);
		objectMapper.registerModule(new JavaTimeModule());
		if(!StringUtils.isEmpty(responeString)) {
			ResponseDTO<AccountDTO, ErrorObject> responseDTO = objectMapper.readValue(responeString, ResponseDTO.class);
			if(!responseDTO.getIsError()) {
				log.info(responseDTO.getSuccessObject());
			}else {
				log.info(responseDTO.getErrorObject());
			}
		}else {
			System.out.println("Empty Response String");
		}
	}
	
	
	@Test
	public void testGetAllObject() throws Exception {
		String url = "/api/account";

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(url).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mvc.perform(requestBuilder).andReturn();
		String responeString = result.getResponse().getContentAsString();
		log.info(responeString);
		objectMapper.registerModule(new JavaTimeModule());
		if(!StringUtils.isEmpty(responeString)) {
			ResponseDTO<List<AccountDTO>, ErrorObject> responseDTO 
			  = objectMapper.readValue(responeString, new TypeReference<ResponseDTO<List<AccountDTO>,ErrorObject>>(){ });
			
			if(!responseDTO.getIsError()) {
				List<AccountDTO> accountList = responseDTO.getSuccessObject();
				long count = StreamSupport.stream(accountList.spliterator(), false).count();
				if(count > 0) {
					log.info("Size : "+accountList.spliterator().getExactSizeIfKnown());
					accountList.forEach(n -> System.out.println(n)); 
				}
			}else {
				log.info(responseDTO.getErrorObject());
			}
		}else {
			System.out.println("Empty Response String");
		}
	}
	
	@Test
	public void testFilterObject() throws Exception {
		String url = "/api/filterData?accountName=test&accountType=&amount=";

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(url).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mvc.perform(requestBuilder).andReturn();
		String responeString = result.getResponse().getContentAsString();
		log.info(responeString);
		objectMapper.registerModule(new JavaTimeModule());
		if(!StringUtils.isEmpty(responeString)) {
			ResponseDTO<List<AccountDTO>, ErrorObject> responseDTO 
			  = objectMapper.readValue(responeString, new TypeReference<ResponseDTO<List<AccountDTO>,ErrorObject>>(){ });
			
			if(!responseDTO.getIsError()) {
				List<AccountDTO> accountList = responseDTO.getSuccessObject();
				long count = StreamSupport.stream(accountList.spliterator(), false).count();
				if(count > 0) {
					log.info("Size : "+accountList.spliterator().getExactSizeIfKnown());
					accountList.forEach(n -> System.out.println(n)); 
				}
			}else {
				log.info(responseDTO.getErrorObject());
			}
		}else {
			System.out.println("Empty Response String");
		}
	}
	
}

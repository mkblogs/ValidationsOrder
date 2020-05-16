package com.tech.mkblogs.user.controller;

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
import com.tech.mkblogs.response.ErrorObject;
import com.tech.mkblogs.response.ResponseDTO;
import com.tech.mkblogs.user.dto.UserDTO;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@AutoConfigureMockMvc
@SuppressWarnings("unchecked")
@Log4j2
@EnableJpaAuditing
public class ControllerTestcase {

	@Autowired
	private MockMvc mvc;
	
	@Autowired
	ObjectMapper objectMapper;
	 
	@Test
	public void testSaveObject() throws Exception {
		
		String url = "/api/user";
		String jsonString = "{" + " \"username\":\"Testtwo\"," 
								+ " \"password\": \"password\"," 
								+ " \"repeatPassword\": \"password\"," 
								+ " \"firstName\": \"Testfirstname\"," 
								+ " \"lastName\": \"lastname\"" 
								+ " } ";
		log.info(jsonString);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(url).accept(MediaType.APPLICATION_JSON)
				.content(jsonString).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mvc.perform(requestBuilder).andReturn();
		objectMapper.registerModule(new JavaTimeModule());
		String responeString = result.getResponse().getContentAsString();
		log.info(responeString);
		if(!StringUtils.isEmpty(responeString)) {
			ResponseDTO<UserDTO, ErrorObject> responseDTO = objectMapper.readValue(responeString, ResponseDTO.class);
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
		String url = "/api/user";
		String jsonString = "{" 
								+ " \"id\": 5,"
								+ " \"username\":\"TestOne\"," 
								+ " \"password\": \"passwordnew\"," 
								+ " \"repeatPassword\": \"passwordnew\"," 
								+ " \"firstName\": \"updateone\"," 
								+ " \"lastName\": \"updateone\"" 
								+ " } ";
		System.out.println(jsonString);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.put(url).accept(MediaType.APPLICATION_JSON)
				.content(jsonString).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mvc.perform(requestBuilder).andReturn();
		String responeString = result.getResponse().getContentAsString();
		log.info(responeString);
		if(!StringUtils.isEmpty(responeString)) {
			ResponseDTO<UserDTO, ErrorObject> responseDTO = objectMapper.readValue(responeString, ResponseDTO.class);
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
		String url = "/api/user/10";
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
		String url = "/api/user/5";

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(url).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mvc.perform(requestBuilder).andReturn();
		String responeString = result.getResponse().getContentAsString();
		log.info(responeString);
		objectMapper.registerModule(new JavaTimeModule());
		if(!StringUtils.isEmpty(responeString)) {
			ResponseDTO<UserDTO, ErrorObject> responseDTO = objectMapper.readValue(responeString, ResponseDTO.class);
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
		String url = "/api/user";

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(url).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mvc.perform(requestBuilder).andReturn();
		String responeString = result.getResponse().getContentAsString();
		log.info(responeString);
		objectMapper.registerModule(new JavaTimeModule());
		if(!StringUtils.isEmpty(responeString)) {
			ResponseDTO<List<UserDTO>, ErrorObject> responseDTO 
			  = objectMapper.readValue(responeString, new TypeReference<ResponseDTO<List<UserDTO>,ErrorObject>>(){ });
			
			if(!responseDTO.getIsError()) {
				List<UserDTO> accountList = responseDTO.getSuccessObject();
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
		String url = "/api/user/filterData?username=test&firstname=&lastname=";

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(url).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mvc.perform(requestBuilder).andReturn();
		String responeString = result.getResponse().getContentAsString();
		log.info(responeString);
		objectMapper.registerModule(new JavaTimeModule());
		if(!StringUtils.isEmpty(responeString)) {
			ResponseDTO<List<UserDTO>, ErrorObject> responseDTO 
			  = objectMapper.readValue(responeString, new TypeReference<ResponseDTO<List<UserDTO>,ErrorObject>>(){ });
			
			if(!responseDTO.getIsError()) {
				List<UserDTO> accountList = responseDTO.getSuccessObject();
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

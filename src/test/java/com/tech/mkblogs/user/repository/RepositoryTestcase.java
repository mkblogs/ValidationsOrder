package com.tech.mkblogs.user.repository;

import static org.junit.Assert.assertEquals;

import java.util.Optional;
import java.util.stream.StreamSupport;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.tech.mkblogs.user.filter.UserFilterDTO;
import com.tech.mkblogs.user.model.User;

@SpringBootTest
@EnableJpaAuditing
public class RepositoryTestcase {

	@Autowired
	UserRepository repository;
	
	@Test
	public void testSaveObject() throws Exception {
		User user = new User();
		user.setUsername("Test");
		user.setPassword("Test@123");
		user.setFirstName("FirstName");
		user.setLastName("Lastname");
		repository.save(user);
	}
	
	@Test
	public void testSave_UpdateObject() throws Exception {
		User user = new User();
		user.setUsername("Test");
		user.setPassword("Test@123");
		user.setFirstName("FirstName");
		user.setLastName("Lastname");
		repository.save(user);
		user.setFirstName("FirstName_Updated");
		repository.save(user);
	}
	
	
	@Test
	public void testUpdateObject() throws Exception {
		Integer updateId = 2;
		Optional<User> optionalObject = repository.findById(updateId);
		if(optionalObject.isPresent()) {
			User user = optionalObject.get();
			System.out.println(user);
			user.setFirstName("ABCUpdate");
			user.setLastName("ABCUpdate");
			repository.save(user);
		}else {
			System.out.println("Record Not Found");
		}
	}
	
	
	@Test
	public void testDeleteObject() throws Exception {
		User user = new User();
		user.setUsername("Test");
		user.setPassword("Test@123");
		user.setFirstName("FirstName");
		user.setLastName("Lastname");
		repository.save(user);
		repository.delete(user);
	}
	@Test
	public void testDeleteById() throws Exception {
		User user = new User();
		user.setUsername("Test");
		user.setPassword("Test@123");
		user.setFirstName("FirstName");
		user.setLastName("Lastname");
		repository.save(user);
		repository.deleteById(user.getId());
	}
	
	@Test	
	public void testCount() throws Exception {
		User user = new User();
		user.setUsername("Test");
		user.setPassword("Test@123");
		user.setFirstName("FirstName");
		user.setLastName("Lastname");
		repository.save(user);
		assertEquals(3, repository.count());
	}
	
	@Test
	public void fetchAllData() throws Exception {
		UserSpecifications specifications = new UserSpecifications();
		Iterable<User> accountList = repository.findAll(specifications);
		long count = StreamSupport.stream(accountList.spliterator(), false).count();
		if(count > 0) {
			System.out.println("Size : "+accountList.spliterator().getExactSizeIfKnown());
			accountList.forEach(n -> System.out.println(n)); 
		}
	}
	
	
	@Test
	public void fetchData() throws Exception {
		String username = "test";
		String firstName = "first";
		String lastName = "last";
		
		UserFilterDTO dto = new UserFilterDTO();
		dto.setUsername(username);
		dto.setFirstName(firstName);
		dto.setLastName(lastName);
		
		UserSpecifications specifications = new UserSpecifications(dto);
		
		Iterable<User> accountList = repository.findAll(specifications);
		long count = StreamSupport.stream(accountList.spliterator(), false).count();
		if(count > 0) {
			System.out.println("Size : "+accountList.spliterator().getExactSizeIfKnown());
			accountList.forEach(n -> System.out.println(n)); 
		}
	}
	
}

package com.tech.mkblogs.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.tech.mkblogs.user.model.User;

@Repository
public interface UserRepository 
			extends JpaRepository<User, Integer>,JpaSpecificationExecutor<User> {

	List<User> findByUsername(String username);
	
}

package com.tech.mkblogs.user.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.tech.mkblogs.user.filter.UserFilterDTO;
import com.tech.mkblogs.user.model.User;



public class UserSpecifications implements Specification<User>{

	private static final long serialVersionUID = 2065807339220549456L;
	
	private UserFilterDTO filterDTO;
	
	public UserSpecifications() {
		this.filterDTO = new UserFilterDTO();
	}
	
	public UserSpecifications(UserFilterDTO filterDTO) {
		this.filterDTO = filterDTO;
	}
	
	@Override
	public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
		List<Predicate> predicates = new ArrayList<Predicate>();
		
		String username  = filterDTO.getUsername();
		String firstName = filterDTO.getFirstName();
		String lastName  = filterDTO.getLastName();
		
		if(!StringUtils.isEmpty(username)) {
			predicates.add(criteriaBuilder.equal(root.get("username"),  username));
		}
		
		if(!StringUtils.isEmpty(firstName)) {
			predicates.add(criteriaBuilder.like(root.get("firstName"),  "%"+ firstName + "%"));
		}
		
		if(!StringUtils.isEmpty(lastName)) {
			predicates.add(criteriaBuilder.like(root.get("lastName"),  "%"+ lastName + "%"));
		}
		return query.where(criteriaBuilder.and(predicates.toArray(new Predicate[0]))).getRestriction();
	}
}

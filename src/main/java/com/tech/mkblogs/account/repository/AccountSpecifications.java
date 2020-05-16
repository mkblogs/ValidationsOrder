package com.tech.mkblogs.account.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.tech.mkblogs.account.filter.FilterDTO;
import com.tech.mkblogs.account.model.Account;



public class AccountSpecifications implements Specification<Account>{

	private static final long serialVersionUID = 2065807339220549456L;
	
	private FilterDTO filterDTO;
	
	public AccountSpecifications() {
		this.filterDTO = new FilterDTO();
	}
	
	public AccountSpecifications(FilterDTO filterDTO) {
		this.filterDTO = filterDTO;
	}
	
	@Override
	public Predicate toPredicate(Root<Account> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
		List<Predicate> predicates = new ArrayList<Predicate>();
		String accountName = filterDTO.getAccountName();
		String accountType = filterDTO.getAccountType();
		String amount = filterDTO.getAmount();
		
		if(!StringUtils.isEmpty(accountName)) {
			predicates.add(criteriaBuilder.like(root.get("accountName"), "%" + accountName + "%"));
		}
		
		if(!StringUtils.isEmpty(accountType)) {
			predicates.add(criteriaBuilder.equal(root.get("accountType"),  accountType));
		}
		
		if(!StringUtils.isEmpty(amount)) {
			predicates.add(criteriaBuilder.equal(root.get("amount"),  amount));
		}
		
		return query.where(criteriaBuilder.and(predicates.toArray(new Predicate[0]))).getRestriction();
	}
	
}

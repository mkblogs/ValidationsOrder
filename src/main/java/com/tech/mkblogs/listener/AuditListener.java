package com.tech.mkblogs.listener;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.springframework.context.annotation.Configuration;

import com.tech.mkblogs.model.BaseModel;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Configuration
public class AuditListener {

	 @PrePersist
	 private void beforeSave(BaseModel model) {
		 log.info("Starting the beforeSave() method ");
	     model.setCreatedBy("1");
	     model.setCreatedName("Test");
	 }
	 
	 @PreUpdate
	 private void beforeUpdate(BaseModel model) {
		 log.info("Starting the beforeUpdate() method ");
	     model.setLastModifiedName("Test");
	     model.setLastModifiedBy("1");
	 }
}

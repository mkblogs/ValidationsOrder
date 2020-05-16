package com.tech.mkblogs.model;

import java.time.LocalDateTime;

import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import com.tech.mkblogs.listener.AuditListener;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@MappedSuperclass
@EntityListeners(AuditListener.class)
public class BaseModel {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@CreationTimestamp
	protected LocalDateTime createdTs;
	@CreatedBy
	protected String createdBy;
	@UpdateTimestamp
	protected LocalDateTime lastModifiedTs;
	@LastModifiedBy
	protected String lastModifiedBy;
	@Version
	protected Integer version;
	
	private String createdName;	
	private String lastModifiedName;
}

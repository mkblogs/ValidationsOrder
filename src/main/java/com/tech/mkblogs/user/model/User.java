package com.tech.mkblogs.user.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.tech.mkblogs.model.BaseModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class User extends BaseModel{
	
	private String username;	
	private String password;
	private String firstName;
	private String lastName;
	
}

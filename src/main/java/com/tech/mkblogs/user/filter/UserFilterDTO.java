package com.tech.mkblogs.user.filter;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserFilterDTO {
	
	private String username;	
	private String firstName;
	private String lastName;
}

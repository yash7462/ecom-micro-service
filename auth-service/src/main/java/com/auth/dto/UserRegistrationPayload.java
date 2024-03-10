package com.auth.dto;

import lombok.Data;

@Data
public class UserRegistrationPayload {
	//private long userId;
	private String userName;
	private String mobile;
	private String email;
	private String password;
	
	
}

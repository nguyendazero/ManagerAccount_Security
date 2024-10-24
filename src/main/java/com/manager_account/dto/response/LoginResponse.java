package com.manager_account.dto.response;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {
	
	private String username;
	
    private Set<String> roles;
    
    private String jwtToken;

    private String refreshToken;
}

package com.manager_account.dto.response;



import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterResponse {
	private String username;
    private String fullName;
    private boolean enabled;
    private String message;
}

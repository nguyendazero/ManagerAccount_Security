package com.manager_account.services;

import com.manager_account.dto.request.LoginRequest;
import com.manager_account.dto.request.RegisterRequest;
import com.manager_account.dto.response.APICustomize;
import com.manager_account.dto.response.LoginResponse;
import com.manager_account.dto.response.RegisterResponse;

public interface UserService {

	public APICustomize<LoginResponse> authenticateUser(LoginRequest loginRequest);
	
	public APICustomize<RegisterResponse> register(RegisterRequest registerRequest);
}

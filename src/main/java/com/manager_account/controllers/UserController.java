package com.manager_account.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.manager_account.services.UserService;
import com.manager_account.dto.request.LoginRequest;
import com.manager_account.dto.request.RegisterRequest;
import com.manager_account.dto.response.APICustomize;
import com.manager_account.dto.response.LoginResponse;
import com.manager_account.dto.response.RegisterResponse;

@RestController
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @GetMapping("/admin/hello-admin")
    public ResponseEntity<?> xinChaoAdmin(){
    	String response = "Xin chào Admin";
    	return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    
    @GetMapping("/user/hello-user")
    public ResponseEntity<?> xinChaoUser(){
    	String response = "Xin chào User";
    	return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest registerRequest) {
        APICustomize<RegisterResponse> response = userService.register(registerRequest);
        return ResponseEntity.status(Integer.parseInt(response.getStatusCode())).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        APICustomize<LoginResponse> response = userService.authenticateUser(loginRequest);
        return ResponseEntity.status(Integer.parseInt(response.getStatusCode())).body(response);
    }
    
    @GetMapping("/profile")
    public ResponseEntity<?> getUserProfile(){
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    	
    	Map<String, Object> profile = new HashMap<>();
    	profile.put("username", userDetails.getUsername());
    	profile.put("roles", userDetails.getAuthorities().stream()
    			.map(item -> item.getAuthority())
    			.collect(Collectors.toList())
    	);
    	profile.put("message", "This is user-specific content form backend");
    	return ResponseEntity.ok(profile);
    }
}

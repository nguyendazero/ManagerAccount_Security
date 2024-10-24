package com.manager_account.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterRequest {
    

    private String username;

    private String password;

    private String fullName;
    
}

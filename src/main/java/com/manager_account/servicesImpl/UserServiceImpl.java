package com.manager_account.servicesImpl;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.GrantedAuthority;

import com.manager_account.dto.request.LoginRequest;
import com.manager_account.dto.request.RegisterRequest;
import com.manager_account.dto.response.APICustomize;
import com.manager_account.dto.response.LoginResponse;
import com.manager_account.dto.response.RegisterResponse;
import com.manager_account.entities.Role;
import com.manager_account.entities.User;
import com.manager_account.entities.Users_Roles;
import com.manager_account.enums.ApiError;
import com.manager_account.exceptions.ErrorLoginException;
import com.manager_account.exceptions.UserExistException;
import com.manager_account.repositories.RoleRepository;
import com.manager_account.repositories.UserRepository;
import com.manager_account.repositories.UsersRolesRepository;
import com.manager_account.security.jwt.JwtUtils;
import com.manager_account.services.UserService;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private UsersRolesRepository usersRolesRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public APICustomize<LoginResponse> authenticateUser(LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            
            String jwtToken = jwtUtils.generateTokenFromUsername(userDetails);
            
            String refreshToken = jwtUtils.generateRefreshTokenFromUsername(userDetails);

            Set<String> roles = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toSet());
            
            LoginResponse response = new LoginResponse(userDetails.getUsername(), roles, jwtToken, refreshToken);

            return new APICustomize<>(ApiError.OK.getCode(), ApiError.OK.getMessage(), response);
        } catch (BadCredentialsException e) {
            throw new ErrorLoginException("Sai tên đăng nhập hoặc mật khẩu!");
        }
    }


	@Override
	public APICustomize<RegisterResponse> register(RegisterRequest registerRequest) {

	    if (userRepository.existsByUsername(registerRequest.getUsername())) {
	       throw new UserExistException("Tên đăng nhập đã tồn tại!"); 
	    }

	    User newUser = new User();
	    newUser.setUsername(registerRequest.getUsername());
	    newUser.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
	    newUser.setFullName(registerRequest.getFullName());
	    newUser.setEnabled(true);

	    userRepository.save(newUser);

	    Role role = roleRepository.findByName("ROLE_USER");
	    Users_Roles ur = new Users_Roles();
	    ur.setRole(role);
	    ur.setUser(newUser);
	    
	    usersRolesRepository.save(ur);

	    RegisterResponse response = new RegisterResponse(
	            newUser.getUsername(),
	            newUser.getFullName(),
	            newUser.isEnabled(),
	            "Đăng ký thành công"
	    );

	    return new APICustomize<>(ApiError.CREATED.getCode(), ApiError.CREATED.getMessage(), response);
	}

}

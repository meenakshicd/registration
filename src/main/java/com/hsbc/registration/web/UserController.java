package com.hsbc.registration.web;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.hsbc.registration.domain.Role;
import com.hsbc.registration.domain.User;
import com.hsbc.registration.exception.RegistrationException;
import com.hsbc.registration.model.UserDetail;
import com.hsbc.registration.service.TokenService;
import com.hsbc.registration.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	Logger logger = LoggerFactory.getLogger(getClass());
	private  UserService userService;

    @Autowired
    public void setUserService (UserService userService) {
        this.userService = userService;
    }
    
    private  TokenService tokenService;

    @Autowired
    public void setTokenService (TokenService tokenService) {
        this.tokenService = tokenService;
    }

	@PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
	public User createUser(@RequestBody UserDetail userDetail) {
			try {
				logger.debug("createUser");
				return userService.createUser(userDetail);
			} catch (RegistrationException e) {
				
				logger.error(e.getMessage(),e);
				throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, e.getMessage());
			}
	}
	@DeleteMapping("/{userName}")
	public void deleteUser(@PathVariable String userName) {
		try {
			logger.debug("deleteUser");
			userService.deleteUser(userName);
		} catch (RegistrationException e) {
			
			logger.error(e.getMessage(),e);
			throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, e.getMessage());
		}
	}
	@PatchMapping("/{userName}/role/{roleName}")
	public User addRole(@PathVariable String userName,@PathVariable String roleName) {
		logger.debug("addRole");
		return userService.addUserRole(userName, roleName);
	}
	
	@GetMapping("/roles")
	public Set<Role> getUserRoles(@RequestParam String authToken) {
		logger.debug("getUserRoles");
		return userService.findUserRoles(tokenService.getUser(authToken));
	}
	
	@GetMapping("/")
	public Set<User> getAllUser() {
		logger.debug("getAllUser");
		return userService.findAll();
	}
	

}

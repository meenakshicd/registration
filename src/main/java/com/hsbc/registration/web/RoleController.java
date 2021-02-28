package com.hsbc.registration.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.hsbc.registration.domain.Role;
import com.hsbc.registration.exception.RegistrationException;
import com.hsbc.registration.service.RoleService;
import com.hsbc.registration.service.TokenService;
@RestController
@RequestMapping("/role")
public class RoleController {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	private  RoleService roleService;

    @Autowired
    public void setRoleService (RoleService roleService) {
        this.roleService = roleService;
    }
    private  TokenService tokenService;

    @Autowired
    public void setTokenService (TokenService tokenService) {
        this.tokenService = tokenService;
    }
    @GetMapping("/{roleName}")
	public boolean findRole(@PathVariable  String roleName,@RequestHeader String authToken) {
    	logger.debug("findRole");
    	if(!tokenService.validateToken(authToken).equals("ValidToken")) {
    		logger.error("Auth Token invalid");
    		throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Auth Token invalid");
    	}
		try {
			return roleService.findRole(roleName).isPresent();
		} catch (RegistrationException e) {
			
			logger.error("Auth Token invalid",e);
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e.getCause());
		}
	}
	@PostMapping("/{roleName}")
	public Role createRole(@PathVariable  String roleName) {
		try {
			logger.debug("createRole");
			return roleService.createRole(roleName);
		} catch (RegistrationException e) {
			
			logger.error(e.getMessage(),e);
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e.getCause());
		}
	}
	@DeleteMapping("/{roleName}")
	public void deleteRole(@PathVariable String roleName) {
		try {
			logger.debug("deleteRole");
			roleService.deleteRole(roleName);
		} catch (RegistrationException e) {
			
			logger.error(e.getMessage(),e);
			throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, e.getMessage());
		}
	}
}

package com.hsbc.registration.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.hsbc.registration.domain.Token;
import com.hsbc.registration.model.UserDetail;
import com.hsbc.registration.service.TokenService;

@RestController
@RequestMapping("/authenticate")
public class AuthenticationController {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Value("${allow.anonymousUser}")
	private boolean allowAnonymous;

	private final TokenService tokenService;

    @Autowired
    AuthenticationController (TokenService tokenService) {
        this.tokenService = tokenService;
    }
    @PostMapping(value="/user", consumes = {MediaType.APPLICATION_JSON_VALUE})
	public String authenticateUser(@RequestBody UserDetail userDetail) {
    	logger.debug("authenticateUser");
		Token token = tokenService.authenticateUser(userDetail);
		if(token==null) {
			logger.error("User Credentials Invalid");
			throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,"User Credentials Invalid");
		}
		return token.getToken();
	}
	
	@PostMapping(value="/anonymous")
	public String anonymousUser() {
		logger.debug("anonymousUser");
		if (allowAnonymous) {
			Token token = tokenService.authenticateAsAnonymous();
			return token.getToken();
		} else {
			logger.error("Anonymous Users not allowed");

			throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Anonymous Users not allowed");
		}
	}
	
	@PostMapping(value ="/validate", consumes = {MediaType.TEXT_PLAIN_VALUE})
	public String validateToken(@RequestBody String token) {
		logger.debug("validateToken");
		return tokenService.validateToken(token);
	}
	@PostMapping(value ="/invalidate", consumes = {MediaType.TEXT_PLAIN_VALUE})
	public void invalidateToken(@RequestBody String token) {
		logger.debug("invalidateToken");
		tokenService.deleteToken(token);
	}
	
	
	
}

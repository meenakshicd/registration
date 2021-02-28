package com.hsbc.registration.service;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hsbc.registration.domain.Token;
import com.hsbc.registration.domain.User;
import com.hsbc.registration.model.UserDetail;

@Component("tokenService")
public final class TokenService {

	private Set<Token> tokens = new HashSet<>();

    private final UserService userService;

    @Autowired
    TokenService (UserService userService) {
        this.userService = userService;
    }
    
    
    public static final String TOKEN_INVALID = "InvalidToken";
    public static final String TOKEN_EXPIRED = "ExpiredToken";
    public static final String TOKEN_VALID = "ValidToken";
    
    public Token authenticateUser(UserDetail userDetail) {
    	User user = userService.validateUser(userDetail);
    	if(user!=null) {
    		return generateToken(user);
    	}
    	return null;
    }
    
    public Token authenticateAsAnonymous() {
    		return generateToken(null);
    }
    
   
	protected Token generateToken(User user) {
    	Token token = new Token(user);
    	tokens.add(token);
		return token;
	}
    
    public String validateToken(String token) {
    	Optional<Token> validToken = tokens.stream().filter(tk->tk.getToken().equals(token)).findFirst();
    	if(validToken.isEmpty())
    		return TOKEN_INVALID;
    	final Calendar cal = Calendar.getInstance();
        if ((validToken.get().getExpiryDate().getTime() <= cal.getTime().getTime())) {
        	deleteToken(token);
            return TOKEN_EXPIRED;
        } else {
        	return TOKEN_VALID;
        }
    	
    }
    public boolean isTokenValid(String token) {
    	return TOKEN_VALID.equals(validateToken(token));
    	
    }
    
    public User getUser(String tokenString) {
    	Optional<Token> validToken = tokens.stream().filter(tk->tk.getToken().equals(tokenString)).findFirst();
    	if(validToken.isPresent()) {
    		return validToken.get().getUser();
    	}
    	return null;
    	
    }
    public boolean deleteToken(String token) {
    	return tokens.removeIf(tk->token.equals(tk.getToken()));
    	
    }
    
	
}

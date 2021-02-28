package com.hsbc.registration.domain;

import java.util.Date;
import java.util.UUID;

import com.hsbc.registration.service.UserUtil;


public class Token {

    private String token;
    
    private User user;

    private Date expiryDate;
    
    public Token(User user) {
    	this.user = user;
        this.token = UUID.randomUUID().toString();;
        this.expiryDate = UserUtil.calculateExpiryDate(60*2);
    }

	public String getToken() {
		return token;
	}

	public User getUser() {
		return user;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}
	
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Token :").append(token).append("\n").append("Expires : ").append(expiryDate).append("\n");
        return builder.toString();
    }

}

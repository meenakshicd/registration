package com.hsbc.registration.domain;

import java.beans.Transient;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class User implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public User(String userName, byte[] secret, String encodedPassword) {
		this.secret = secret;
		this.userName = userName;
		this.encodedPassword = encodedPassword;
	}
	
	private String userName;
	
	private String encodedPassword;
	private byte[] secret;
	@Transient
	public byte[] getSecret() {
		return secret;
	}

	private Set<Role> roles = new HashSet<Role>();

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	@Transient
	public String getEncodedPassword() {
		return encodedPassword;
	}

	public void setEncodedPassword(String encodedPassword) {
		this.encodedPassword = encodedPassword;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Role role) {
		if(this.roles==null) {
			this.roles = new HashSet<Role>();
		}
		this.roles.add(role);
	}
	@Override
    public boolean equals(Object user) {
		return this.userName.equals(((User) user).getUserName());
	}
	
}

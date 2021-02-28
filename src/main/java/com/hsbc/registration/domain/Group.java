package com.hsbc.registration.domain;

import java.util.HashSet;
import java.util.Set;

public class Group {

	private String groupName;
	private Set<User> users = new HashSet<User>();
	private Set<Role> roles = new HashSet<Role>();
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public Set<User> getUsers() {
		return users;
	}
	public void setUsers(Set<User> users) {
		this.users = users;
	}
	public Set<Role> getRoles() {
		return roles;
	}
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
}

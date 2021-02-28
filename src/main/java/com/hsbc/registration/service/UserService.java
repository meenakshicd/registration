package com.hsbc.registration.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import com.hsbc.registration.domain.Role;
import com.hsbc.registration.domain.User;
import com.hsbc.registration.exception.RegistrationException;
import com.hsbc.registration.model.UserDetail;


@Component("userService")
public class UserService {
		private RoleService roleService;
	
	    @Autowired
	    public void setRoleService (RoleService roleService) {
	        this.roleService = roleService;
	    }
	 	private Set<User> users = new HashSet<>();
	    
	    @Bean
		public PasswordEncoder passwordEncoder() {
			return new BCryptPasswordEncoder();
		}
	    
	    public Set<User> findAll() {
	        return this.users;
	    }
	    
	    public Optional<User> findUser(String userName) {
	    	return users.stream().filter(user->userName.equals(user.getUserName())).findFirst();
	    }

	    
	    public User validateUser(UserDetail userDetail) {
	    	Optional<User> validUser = users.stream().filter(user->userDetail.getUserName().equals(user.getUserName())).findFirst();
	    	if(validUser.isEmpty()) {
	    		throw new RegistrationException("INVALID_USER");
	    	}
	    	User user = validUser.get();
	    	if(passwordEncoder().matches(user.getSecret()+userDetail.getPassword(), user.getEncodedPassword())) {
	    		return user;
	    	}
	    	throw new RegistrationException("INVALID_CREDENTIALS");
	    }
	    public User createUser(UserDetail userDetail) {
	    	if(users.stream().anyMatch(user->userDetail.getUserName().equals(user.getUserName()))){
	    		throw new RegistrationException("User already Exists");
	    	} else {
	    		byte[] secret = UserUtil.getSalt();
	    		User newUser=new User(userDetail.getUserName(),secret, passwordEncoder().encode(secret+userDetail.getPassword()));
	    		users.add(newUser);
	    		return newUser;
	    	}
	    }
	    public boolean deleteUser(String userName){
	    	
	    	if(users.removeIf(user->userName.equals(user.getUserName()))){
	    		return true;
	    	} else {
	    		throw new RegistrationException("User not found");
	    	}
	    }
	   public User addUserRole(String userName,String roleName) {
	    	Optional<User> user = findUser(userName);
	    	
	    	if(user.isEmpty()) {
	    		throw new RegistrationException("User does not Exists");
	    	}
	    	User availUser = user.get();
	    	Optional<Role> role = roleService.findRole(roleName);
	    	if(role.isPresent()) {
	    		availUser.setRoles(role.get());
	    		
	    	}else {
	    		availUser.setRoles(roleService.createRole(roleName));
	    	}
			return availUser;
	    }
	   public void removeUserRole(Role role) {
		   users.forEach((user)->{
			   Set<Role> roles = user.getRoles();
			   roles.remove(role);
		   });
		  
	    }
	   
	   public Set<Role> findUserRoles(User user) {
		   Set<Role> userRoles = new HashSet<>();
	   
		   if(user!=null) 
			   userRoles.addAll(user.getRoles());
		   else
			   throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Invalid Token");
		   return userRoles;
		   
	    }
	   
	    
}

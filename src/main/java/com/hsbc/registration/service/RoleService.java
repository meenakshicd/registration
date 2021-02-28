package com.hsbc.registration.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hsbc.registration.domain.Role;
import com.hsbc.registration.exception.RegistrationException;

@Component("roleService")
public class RoleService {
	private Set<Role> roles = new HashSet<>();
	private  UserService userService;

    @Autowired
    public void setUserService (UserService userService) {
        this.userService = userService;
    }
    
    
    
    public Role createRole(String roleName){
    	if(roles.stream().anyMatch(role->roleName.equals(role.getRoleName()))){
    		throw new RegistrationException("Role already Exists");
    	} else {
    		Role newRole = new Role(roleName);
    		roles.add(newRole);
    		return newRole;
    	}
    }
    public Optional<Role> findRole(String roleName) {
    	return roles.stream().filter(r->roleName.equals(r.getRoleName())).findFirst();
    	
    }
    public boolean deleteRole(String roleName){
    	Optional<Role> role = roles.stream().filter(r->roleName.equals(r.getRoleName())).findFirst();
    	if(role.isPresent()) {
    		Role r = role.get();
    		userService.removeUserRole(r);
    	}
    			
    	if(roles.removeIf(r1->roleName.equals(r1.getRoleName()))){
    		return true;
    	} else {
    		throw new RegistrationException("Role not found");
    	}
    }
}

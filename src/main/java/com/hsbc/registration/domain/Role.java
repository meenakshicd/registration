package com.hsbc.registration.domain;

public class Role {
	private String roleName;
	
	public Role(String roleName){
		this.roleName = roleName;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}@Override
    public int hashCode() {
        final int prime = 37;
        int result = 1;
        result = prime * result + ((getRoleName() == null) ? 0 : getRoleName().hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Role other = (Role) obj;
        if (getRoleName() == null) {
            if (other.getRoleName() != null) {
                return false;
            }
        } else if (!getRoleName().equals(other.getRoleName())) {
            return false;
        }
        return true;
    }
    @Override
    public String toString() {
        return roleName;
    }
	
}

package com.sv.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "roles_mapping")
public class RolesMappingEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)

	@Column(name = "id")
	private long id;

	@Column(name = "role")
	private String role;
	
	@Column(name = "is_user")
	private int isUser;

	@Column(name = "roles_allowed_apps")
	private String allowedApps;

	@Column(name = "superiors")
	private String superiors;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getAllowedApps() {
		return allowedApps;
	}

	public void setAllowedApps(String allowedApps) {
		this.allowedApps = allowedApps;
	}

	public String getSuperiors() {
		return superiors;
	}

	public void setSuperiors(String superiors) {
		this.superiors = superiors;
	}

	public int getIsUser() {
		return isUser;
	}

	public void setIsUser(int isUser) {
		this.isUser = isUser;
	}
	
}
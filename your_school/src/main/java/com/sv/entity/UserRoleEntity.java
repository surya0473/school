package com.sv.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_roles")
public class UserRoleEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)

	@Column(name = "id")
	private long id;

	@Column(name = "user_id")
	private String userId;

	@Column(name = "role")
	private String role;

	@Column(name = "status")
	private String status;

	@Column(name = "act_date")
	private String activatedDate;

	@Column(name = "de_act_date")
	private String deActivatedDate;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getActivatedDate() {
		return activatedDate;
	}

	public void setActivatedDate(String activatedDate) {
		this.activatedDate = activatedDate;
	}

	public String getDeActivatedDate() {
		return deActivatedDate;
	}

	public void setDeActivatedDate(String deActivatedDate) {
		this.deActivatedDate = deActivatedDate;
	}

	
}

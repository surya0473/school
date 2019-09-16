package com.sv.resp.bean;

import java.io.Serializable;

@SuppressWarnings("serial")
public class UserRoleUpdateReq implements Serializable {

	private String companyId;
	private String userId;
	private String userRoles;

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(String userRoles) {
		this.userRoles = userRoles;
	}

	@Override
	public String toString() {
		return "UserRoleUpdateReq [companyId=" + companyId + ", userId=" + userId + ", userRoles=" + userRoles
				+ "]";
	}
}

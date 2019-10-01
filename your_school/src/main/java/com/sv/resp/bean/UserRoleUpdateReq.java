package com.sv.resp.bean;

import java.io.Serializable;

@SuppressWarnings("serial")
public class UserRoleUpdateReq implements Serializable {

	private String schoolId;
	private String userId;
	private String userRoles;

	public String getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(String companyId) {
		this.schoolId = companyId;
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
		return "UserRoleUpdateReq [SchoolId=" + schoolId + ", userId=" + userId + ", userRoles=" + userRoles + "]";
	}
}

package com.sv.resp.bean;

import java.io.Serializable;

@SuppressWarnings("serial")
public class UserUpdateReqBean implements Serializable {

	public UserUpdateReqBean() {
		// TODO Auto-generated constructor stub
	}

	private String userId;
	private String schoolId;
	private String status;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(String companyId) {
		this.schoolId = companyId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "UserUpdateReqBean [userId=" + userId + ", SchoolId=" + schoolId + ", status=" + status + "]";
	}

}

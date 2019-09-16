package com.sv.resp.bean;

import java.io.Serializable;

@SuppressWarnings("serial")
public class UserUpdateReqBean implements Serializable {

	public UserUpdateReqBean() {
		// TODO Auto-generated constructor stub
	}

	private String userId;
	private String companyId;
	private String status;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "UserUpdateReqBean [userId=" + userId + ", companyId=" + companyId + ", status=" + status + "]";
	}

}

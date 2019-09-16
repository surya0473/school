package com.sv.resp.bean;

import java.io.Serializable;

@SuppressWarnings("serial")
public class CreateUserReq implements Serializable {

	public CreateUserReq() {
		// TODO Auto-generated constructor stub
	}

	private String companyId;
	private String mobileNo;
	private String email;
	private String userRoles;
	
	public String getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(String userRoles) {
		this.userRoles = userRoles;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "CreateUserReq [companyId=" + companyId + ", mobileNo=" + mobileNo + ", email=" + email + "]";
	}

}

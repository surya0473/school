package com.sv.resp.bean;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ActivateCompanyReq implements Serializable {

	private long id;
	private String schoolId;
	private String adminFirstName;
	private String adminLastName;
	private String adminMobile;
	private String adminEmail;
	private String adminPassword;//adminPassword

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}






	public String getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(String companyId) {
		this.schoolId = companyId;
	}

	public String getAdminFirstName() {
		return adminFirstName;
	}

	public void setAdminFirstName(String adminFirstName) {
		this.adminFirstName = adminFirstName;
	}

	public String getAdminLastName() {
		return adminLastName;
	}

	public void setAdminLastName(String adminLastName) {
		this.adminLastName = adminLastName;
	}

	public String getAdminMobile() {
		return adminMobile;
	}

	public void setAdminMobile(String adminMobile) {
		this.adminMobile = adminMobile;
	}

	public String getAdminEmail() {
		return adminEmail;
	}

	public void setAdminEmail(String adminEmail) {
		this.adminEmail = adminEmail;
	}

	public String getAdminPassword() {
		return adminPassword;
	}

	public void setAdminPassword(String adminPassword) {
		this.adminPassword = adminPassword;
	}

}

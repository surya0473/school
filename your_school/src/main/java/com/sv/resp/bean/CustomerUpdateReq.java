package com.sv.resp.bean;

import java.io.Serializable;

@SuppressWarnings("serial")
public class CustomerUpdateReq implements Serializable {

	private String schoolId;
	private String mobileNo;
	private String newMobileNo;
	private String status;

	public String getSchoolId() {
		return schoolId;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public String getNewMobileNo() {
		return newMobileNo;
	}

	public String getStatus() {
		return status;
	}

	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public void setNewMobileNo(String newMobileNo) {
		this.newMobileNo = newMobileNo;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}

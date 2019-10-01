package com.sv.resp.bean;

import java.io.Serializable;

@SuppressWarnings("serial")
public class RegComClientResp implements Serializable {

	private long id;
	private String schoolId;
	private String status;

	@Override
	public String toString() {
		return "RegComClientResp [id=" + id + ", SchoolId=" + schoolId + ", status=" + status + "]";
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}

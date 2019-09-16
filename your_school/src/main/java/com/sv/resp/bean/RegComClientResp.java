package com.sv.resp.bean;

import java.io.Serializable;

@SuppressWarnings("serial")
public class RegComClientResp implements Serializable {

	private long id;
	private String companyId;
	private String status;

	@Override
	public String toString() {
		return "RegComClientResp [id=" + id + ", companyId=" + companyId + ", status=" + status + "]";
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

}

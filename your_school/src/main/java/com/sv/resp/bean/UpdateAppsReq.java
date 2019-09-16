package com.sv.resp.bean;

public class UpdateAppsReq {

	private String companyId;
	private String apps;

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getApps() {
		return apps;
	}

	public void setApps(String apps) {
		this.apps = apps;
	}

	@Override
	public String toString() {
		return "UpdateAppsReq [companyId=" + companyId + ", apps=" + apps + "]";
	}
}

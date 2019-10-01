package com.sv.resp.bean;

public class UpdateAppsReq {

	private String schoolId;
	private String apps;

	public String getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(String companyId) {
		this.schoolId = companyId;
	}

	public String getApps() {
		return apps;
	}

	public void setApps(String apps) {
		this.apps = apps;
	}

	@Override
	public String toString() {
		return "UpdateAppsReq [SchoolId=" + schoolId + ", apps=" + apps + "]";
	}
}

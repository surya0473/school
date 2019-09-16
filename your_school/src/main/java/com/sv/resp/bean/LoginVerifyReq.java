package com.sv.resp.bean;

import java.io.Serializable;

@SuppressWarnings("serial")
public class LoginVerifyReq implements Serializable {

	String userId;
	String appName;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}
}

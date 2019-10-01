package com.sv.resp.bean;

import java.util.ArrayList;
import java.util.List;

public class UpdateFeatureMapReqBean {

	private String schoolId;
	private String appName;
	private List<FeatureReqBean> features = new ArrayList<>();

	public String getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(String companyId) {
		this.schoolId = companyId;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public List<FeatureReqBean> getFeatures() {
		return features;
	}

	public void setFeatures(List<FeatureReqBean> features) {
		this.features = features;
	}

	@Override
	public String toString() {
		return "UpdateFeatureMapReqBean [schoolId=" + schoolId + ", appName=" + appName + ", features=" + features
				+ "]";
	}
}

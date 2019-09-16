package com.sv.resp.bean;

import java.util.ArrayList;
import java.util.List;

public class UpdateFeatureMapReqBean {

	private String companyId;
	private String appName;
	private List<FeatureReqBean> features = new ArrayList<>();

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
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
		return "UpdateFeatureMapReqBean [companyId=" + companyId + ", appName=" + appName + ", features=" + features
				+ "]";
	}
}

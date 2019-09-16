package com.sv.resp.bean;

import java.io.Serializable;

@SuppressWarnings("serial")
public class VerifyUserResp implements Serializable {

	public VerifyUserResp() {
		// TODO Auto-generated constructor stub
	}

	private String userId;
	private String companyId;
	private String name;
	private String email;
	private String contactNumber;
	private String website;
	private String country;
	private String routingUrl;
	private String logoUrl;
	private String mappedApps;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getRoutingUrl() {
		return routingUrl;
	}

	public void setRoutingUrl(String routingUrl) {
		this.routingUrl = routingUrl;
	}

	public String getLogoUrl() {
		return logoUrl;
	}

	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}

	public String getMappedApps() {
		return mappedApps;
	}

	public void setMappedApps(String mappedApps) {
		this.mappedApps = mappedApps;
	}

}

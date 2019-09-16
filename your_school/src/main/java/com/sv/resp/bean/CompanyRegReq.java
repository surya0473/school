package com.sv.resp.bean;

import java.io.Serializable;

@SuppressWarnings("serial")
public class CompanyRegReq implements Serializable {

	private String name;
	private String emailId;
	private String contactNo;
	private String website;
	private String address;
	private String country;
	private String languages;
	private String regionName;
	private String routingUrl;
	private String logoUrl;

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public String getRoutingUrl() {
		return routingUrl;
	}

	public void setRoutingUrl(String routingUrl) {
		this.routingUrl = routingUrl;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	public String getWebsite() {
		return website.toLowerCase();
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getLanguages() {
		return languages;
	}

	public void setLanguages(String languages) {
		this.languages = languages;
	}

	public String getLogoUrl() {
		return logoUrl;
	}

	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}

	@Override
	public String toString() {
		return "CompanyRegReq [name=" + name + ", emailId=" + emailId + ", contactNo=" + contactNo + ", website="
				+ website + ", address=" + address + ", country=" + country + ", languages=" + languages
				+ ", regionName=" + regionName + ", routingUrl=" + routingUrl + ", logoUrl=" + logoUrl + "]";
	}
}

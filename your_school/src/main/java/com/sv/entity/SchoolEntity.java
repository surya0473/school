package com.sv.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/*
 * @author surya v
 * @date 23/08/2019
 * 
 */

@Entity
@Table(name = "schools")
public class SchoolEntity {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "school_id")
	private String schoolId;

	@Column(name = "name")
	private String name;

	@Column(name = "email")
	private String emailId;

	@Column(name = "contact_no")
	private String contactNumber;

	@Column(name = "website")
	private String website;

	@Column(name = "address")
	private String address;

	@Column(name = "country")
	private String country;

	@Column(name = "status")
	private String status;

	@Column(name = "code")
	private int code;

	@Column(name = "time_zone")
	private String timeZone;

	@Column(name = "time_interval")
	private String timeInterval;

	@Column(name = "currency")
	private String currency;

	@Column(name = "currency_code")
	private String currencyCode;

	@Column(name = "currency_symbol")
	private String currencySymbol;

	@Column(name = "created_date")
	private String createdDate;

	@Column(name = "activated_date")
	private String activatedDate;

	@Column(name = "languages")
	private String languages;

	@Column(name = "routing_url")
	private String routingUrl;

	@Column(name = "logo_url")
	private String logoUrl;

	@Column(name = "mapped_apps")
	private String mappedApps;

	@Column(name = "region")
	private String region;

	@javax.persistence.Transient
	private int userCount;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(String companyId) {
		this.schoolId = companyId;
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

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}

	public String getTimeInterval() {
		return timeInterval;
	}

	public void setTimeInterval(String timeInterval) {
		this.timeInterval = timeInterval;
	}

	public String getLanguages() {
		return languages;
	}

	public void setLanguages(String languages) {
		this.languages = languages;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getCurrencySymbol() {
		return currencySymbol;
	}

	public void setCurrencySymbol(String currencySymbol) {
		this.currencySymbol = currencySymbol;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getActivatedDate() {
		return activatedDate;
	}

	public void setActivatedDate(String activatedDate) {
		this.activatedDate = activatedDate;
	}

	public String getMappedApps() {
		return mappedApps;
	}

	public void setMappedApps(String mappedApps) {
		this.mappedApps = mappedApps;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public int getUserCount() {
		return userCount;
	}

	public void setUserCount(int userCount) {
		this.userCount = userCount;
	}

	@Override
	public String toString() {
		return "CompanyEntity [id=" + id + ", schoolId=" + schoolId + ", name=" + name + ", emailId=" + emailId
				+ ", contactNumber=" + contactNumber + ", website=" + website + ", address=" + address + ", country="
				+ country + ", status=" + status + ", code=" + code + ", timeZone=" + timeZone + ", timeInterval="
				+ timeInterval + ", currency=" + currency + ", currencyCode=" + currencyCode + ", currencySymbol="
				+ currencySymbol + ", createdDate=" + createdDate + ", activatedDate=" + activatedDate + ", languages="
				+ languages + ", routingUrl=" + routingUrl + ", logoUrl=" + logoUrl + "]";
	}

}

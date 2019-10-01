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
@Table(name = "customers")
public class CustomerEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private long id;

	@Column(name = "school_id")
	private String schoolId;

	@Column(name = "mobile_no")
	private String mobileNo;

	@Column(name = "status")
	private String status;

	@Column(name = "created_date")
	private String createdDateTime;

	public long getId() {
		return id;
	}

	public String getSchoolId() {
		return schoolId;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public String getStatus() {
		return status;
	}

	public String getCreatedDateTime() {
		return createdDateTime;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setSchoolId(String companyId) {
		this.schoolId = companyId;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setCreatedDateTime(String createdDateTime) {
		this.createdDateTime = createdDateTime;
	}
}

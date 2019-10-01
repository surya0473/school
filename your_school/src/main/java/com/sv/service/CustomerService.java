package com.sv.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sv.constants.AppConstants;
import com.sv.constants.StatusConstants;
import com.sv.dao.impl.SchoolDao;
import com.sv.dao.impl.CustomerDao;
import com.sv.entity.SchoolEntity;
import com.sv.entity.CustomerEntity;
import com.sv.resp.bean.CreateUserReq;
import com.sv.resp.bean.CustomerUpdateReq;
import com.sv.util.DateUtil;

@Service
public class CustomerService {
	Logger logger = LoggerFactory.getLogger(CustomerService.class);
	@Autowired
	CustomerDao dao;
	@Autowired
	SchoolDao cDao;

	public CustomerEntity create(CreateUserReq req) throws Exception {
		logger.info("inside create()...checking Customer.....");
		SchoolEntity company = cDao.getSchoolDetails(req.getSchoolId());
		if (company == null) {
			logger.info("Company details does not exist");
			throw new Exception("Company details doesn't exist.");
		}
		if (!AppConstants.STATUS_ACTIVE.equalsIgnoreCase(company.getStatus())) {
			logger.info("Company details does not exist");
			throw new Exception("Company is not active.");
		}
		CustomerEntity cust = dao.getCustomer(req.getMobileNo());
		if (cust == null) {
			logger.info("Customer doesn't exist with given mobile,Creating new ....");
			cust = new CustomerEntity();
			cust.setSchoolId(req.getSchoolId());
			cust.setCreatedDateTime(DateUtil.getCurrentDateTime(company.getTimeZone()));
			cust.setMobileNo(req.getMobileNo());
			cust.setStatus(AppConstants.STATUS_ACTIVE);
			logger.info("Saving user....");
			cust = dao.save(cust);
			if (cust != null && cust.getId() > 0) {
				logger.info("Customer created successfully.");
				return cust;
			} else {
				logger.info("Unable create user,Please try again later.");
				throw new Exception("Unable create customer,Please try again later.");
			}
		} else {
			logger.info("Customer already exists with given Mobile");
			if (cust.getSchoolId().equalsIgnoreCase(req.getSchoolId())) {
				logger.info("User already exists with given Mobile .");
				return cust;
			} else {
				logger.info("Unable create Customer,Please try again later.");
				throw new Exception(StatusConstants.CUST_ALREADY_EXISTS_WITH_MOBILE);
			}
		}
	}

	public CustomerEntity update(CustomerUpdateReq req) throws Exception {
		logger.info("inside update()......");
		if (req.getStatus() != null && !req.getStatus().isEmpty() && !"null".equalsIgnoreCase(req.getStatus())) {
			logger.info("inside update()...");
			SchoolEntity company = cDao.getSchoolDetails(req.getSchoolId());
			if (company == null) {
				logger.info("Company details does not exist");
				throw new Exception("Company details doesn't exist.");
			}
			if (!AppConstants.STATUS_ACTIVE.equalsIgnoreCase(company.getStatus())) {
				logger.info("Company is not active");
				throw new Exception("Company is not active.");
			}
			CustomerEntity cust = dao.getCustomer(req.getMobileNo());
			if (cust == null) {
				logger.info("Customer details does not exist");
				throw new Exception("Customer details doesn't exist.");
			}
			if (!req.getSchoolId().equalsIgnoreCase(cust.getSchoolId())) {
				logger.info("Company details does not exist");
				throw new Exception("Company details are not matching.");
			}
			if (!req.getStatus().equalsIgnoreCase(cust.getStatus())) {
				logger.info("status has been changed, updating status");
				cust.setStatus(req.getStatus());
				dao.update(cust);
				logger.info("status updated successfully.");
			}
			return cust;
		} else {
			logger.info("Invalid status");
			throw new Exception("Invalid request.");
		}
	}

	public CustomerEntity updateMobile(CustomerUpdateReq req) throws Exception {
		logger.info("inside updateMobile()......");
		if (req.getNewMobileNo() != null && !req.getNewMobileNo().isEmpty()
				&& !"null".equalsIgnoreCase(req.getNewMobileNo())) {
			logger.info("inside update()...");
			SchoolEntity company = cDao.getSchoolDetails(req.getSchoolId());
			if (company == null) {
				logger.info("Company details does not exist");
				throw new Exception("Company details doesn't exist.");
			}
			if (!AppConstants.STATUS_ACTIVE.equalsIgnoreCase(company.getStatus())) {
				logger.info("Company is not active");
				throw new Exception("Company is not active.");
			}
			logger.info("fetching customer with new no");
			CustomerEntity newCust = dao.getCustomer(req.getNewMobileNo());
			if (newCust == null) {
				CustomerEntity oldCust = dao.getCustomer(req.getMobileNo());
				if (oldCust == null) {
					logger.info("Customer details does not exist");
					throw new Exception("Customer details doesn't exist.");
				}
				if (!req.getSchoolId().equalsIgnoreCase(oldCust.getSchoolId())) {
					logger.info("Company details does not exist");
					throw new Exception("Company details are not matching.");
				}
				if (!req.getNewMobileNo().equalsIgnoreCase(oldCust.getMobileNo())) {
					logger.info("Mobile no has been changed, updating..");
					oldCust.setMobileNo(req.getNewMobileNo());
					dao.update(oldCust);
					logger.info("Mobile number updated successfully.");
				}
				return oldCust;
			} else {
				logger.info("Customer already exists with given Mobile");
				if (newCust.getSchoolId().equalsIgnoreCase(req.getSchoolId())) {
					logger.info("User already exists with given Mobile .");
					return newCust;
				} else {
					logger.info("Unable create Customer,Please try again later.");
					throw new Exception(StatusConstants.CUST_ALREADY_EXISTS_WITH_MOBILE);
				}
			}
		} else {
			logger.info("Invalid status");
			throw new Exception("Invalid request.");
		}
	}

}

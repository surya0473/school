package com.sv.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sv.constants.AppConstants;
import com.sv.dao.impl.SchoolDao;
import com.sv.dao.impl.CountryDao;
import com.sv.dao.impl.UserDao;
import com.sv.entity.SchoolEntity;
import com.sv.entity.CountryEntity;
import com.sv.entity.UserEntity;
import com.sv.resp.bean.ActivateCompanyReq;
import com.sv.resp.bean.CompanyRegReq;
import com.sv.resp.bean.SchoolUpdateReq;
import com.sv.resp.bean.RegComClientResp;
import com.sv.resp.bean.RegCompanyClientReq;
import com.sv.util.ClientRestUtil;
import com.sv.util.DateUtil;

@Service
public class SchoolService {
	Logger logger = LoggerFactory.getLogger(SchoolService.class);
	@Autowired
	SchoolDao dao;
	@Autowired
	CountryDao countryDao;
	@Autowired
	UserDao userDao;
	@Autowired
	ClientRestUtil clientUtil;

	public List<SchoolEntity> getAll(Integer status) throws Exception {
		logger.info("inside service getCompanies method");
		List<SchoolEntity> companyEntyLst = dao.getAll(status);
		return companyEntyLst;
	}

	public SchoolEntity getSchoolDetails(String companyId) throws Exception {
		logger.info("inside service getCompanyDetails():" + companyId);
		return dao.getSchoolDetails(companyId);
	}

	public SchoolEntity verifySchool(String domain) throws Exception {
		logger.info("inside service verifyCompany():" + domain);
		SchoolEntity company = dao.verify(domain);
		if (company != null) {
			logger.info("Company already exists, verifying super user..");
			List<UserEntity> users = userDao.getUsers(company.getSchoolId());
			if (users != null) {
				logger.info("Users exists, count:" + users.size());
				company.setUserCount(users.size());
			}
		}
		return company;
	}

	public SchoolEntity register(CompanyRegReq req) throws Exception {
		logger.info("inside  create(): verifying company...");
		SchoolEntity company = dao.verify(req.getWebsite());
		if (company != null) {
			logger.info("Company exists with website:" + req.getWebsite());
			throw new Exception("Company already exists with " + req.getWebsite());
		}
		logger.info("does not exists with website:" + req.getWebsite() + ", creasting new...");
		if (req.getRegionName() == null || req.getRegionName().isEmpty()) {
			logger.info("No routing URL.");
			throw new Exception("Please select region.");
		}
		CountryEntity country = countryDao.getCountry(req.getCountry());
		if (country == null) {
			logger.info("Country details doesn't exist.");
			throw new Exception("Country details doesn't exist.");
		}
		company = new SchoolEntity();
		String companyId = req.getWebsite().replace(".", "_").replace("-", "");
		company.setSchoolId(companyId);
		logger.info("Website:" + req.getWebsite() + ", Company Id:" + company.getSchoolId());
		company.setName(req.getName());
		company.setAddress(req.getAddress());
		company.setContactNumber(req.getContactNo());
		company.setCountry(req.getCountry());
		company.setEmailId(req.getEmailId());
		company.setLanguages(req.getLanguages());
		company.setLogoUrl(req.getLogoUrl());
		company.setWebsite(req.getWebsite());
		company.setRegion(req.getRegionName());
		company.setRoutingUrl(req.getRoutingUrl());
		company.setStatus(AppConstants.STATUS_NEW);
		company.setTimeInterval(country.getTimeInterval());
		company.setTimeZone(country.getTimezone());
		company.setCreatedDate(DateUtil.getCurrentDateTime(country.getTimezone()));
		company.setCurrency(country.getCurrency());
		company.setCurrencyCode(country.getCurrencyCode());
		company.setCurrencySymbol(country.getSymbol());
		company.setCode(country.getCode());
		company.setMappedApps(AppConstants.MIS);
		return dao.save(company);
	}

	public SchoolEntity activate(ActivateCompanyReq req) throws Exception {
		logger.info("inside  update(): getting company details...");
		SchoolEntity company = dao.getSchool(req.getId());
		if (company == null) {
			logger.info("Company doesn't exists");
			throw new Exception("Company doesn't exist.");
		}
		if (!AppConstants.STATUS_NEW.equalsIgnoreCase(company.getStatus())) {
			if (AppConstants.STATUS_ACTIVE.equalsIgnoreCase(company.getStatus())) {
				logger.info("Company already activated.");
				throw new Exception("Company already activated.");
			} else {
				logger.info("Company is not active.");
				throw new Exception("Company is not active.");
			}
		}
		UserEntity user = userDao.checkUser(req.getAdminMobile(), req.getAdminEmail());
		if (user != null) {
			logger.info("User already exists with given mobile/email.");
			throw new Exception("Mobile no/Email already registered with us. Please try with different.");
		}
		logger.info("Company exists , updating..");
		company.setStatus(AppConstants.STATUS_ACTIVE);
		company.setActivatedDate(DateUtil.getCurrentDateTime(company.getTimeZone()));
		logger.info("Creating client req bean..");
		RegCompanyClientReq clientReq = new RegCompanyClientReq();
		clientReq.setActivatedDate(company.getActivatedDate());
		clientReq.setAddress(company.getAddress());
		clientReq.setAdminEmail(req.getAdminEmail());
		clientReq.setAdminFirstName(req.getAdminFirstName());
		clientReq.setAdminLastName(req.getAdminLastName());
		clientReq.setAdminMobile(req.getAdminMobile());
		clientReq.setAdminPassword(req.getAdminPassword());
		clientReq.setCode(company.getCode());
		clientReq.setSchoolId(company.getSchoolId());
		clientReq.setContactNumber(company.getContactNumber());
		clientReq.setCountry(company.getCountry());
		clientReq.setCreatedDate(company.getCreatedDate());
		clientReq.setCurrency(company.getCurrency());
		clientReq.setCurrencyCode(company.getCurrencyCode());
		clientReq.setCurrencySymbol(company.getCurrencySymbol());
		clientReq.setEmailId(company.getEmailId());
		clientReq.setLanguages(company.getLanguages());
		clientReq.setLogoUrl(company.getLogoUrl());
		clientReq.setName(company.getName());
		clientReq.setRoutingUrl(company.getRoutingUrl());
		clientReq.setStatus(company.getStatus());
		clientReq.setTimeInterval(company.getTimeInterval());
		clientReq.setTimeZone(company.getTimeZone());
		clientReq.setWebsite(company.getWebsite());
		logger.info("Calling routing url for creating company....");
		RegComClientResp clientResp = clientUtil.regWithClient(company.getRoutingUrl(), clientReq);
		logger.info("respFromClient:" + clientResp.toString());
		if (clientResp.getSchoolId() != null
				&& AppConstants.STATUS_ACTIVE.equalsIgnoreCase(clientResp.getStatus())) {
			logger.info("Company activated successfully..updating company...");
			boolean isUpdated = dao.update(company);
			return isUpdated == true ? company : null;
		}
		return null;
	}

	public boolean update(SchoolUpdateReq req) throws Exception {
		logger.info("inside  update(): getting company details...");
		SchoolEntity company = dao.getSchoolDetails(req.getSchoolId());
		if (company == null) {
			logger.info("Company doesn't exists");
			throw new Exception("Company doesn't exist.");
		}
		logger.info("Company exists , updating..");
		if (AppConstants.STATUS_NEW.equalsIgnoreCase(company.getStatus())) {
			throw new Exception("Company not activated yet, Please contact support team.");
		}
		if (req.getStatus() != null && !req.getStatus().isEmpty()
				&& !company.getStatus().equalsIgnoreCase(req.getStatus())) {
			company.setStatus(req.getStatus());
		}
		if (req.getName() != null && !req.getName().isEmpty()) {
			company.setName(req.getName());
		}
		if (req.getAddress() != null && !req.getAddress().isEmpty()) {
			company.setAddress(req.getAddress());
		}
		if (req.getContactNo() != null && !req.getContactNo().isEmpty()) {
			company.setContactNumber(req.getContactNo());
		}
		if (req.getCountry() != null && !req.getCountry().isEmpty() && !company.getCountry().equals(req.getCountry())) {
			CountryEntity country = countryDao.getCountry(req.getCountry());
			if (country == null) {
				logger.info("Country details doesn't exist.");
				throw new Exception("Country details doesn't exist.");
			}
			company.setTimeInterval(country.getTimeInterval());
			company.setTimeZone(country.getTimezone());
			company.setCurrency(country.getCurrency());
			company.setCurrencyCode(country.getCurrencyCode());
			company.setCurrencySymbol(country.getSymbol());
			company.setCode(country.getCode());
			company.setCountry(req.getCountry());
		}
		if (req.getEmailId() != null && !req.getEmailId().isEmpty()) {
			company.setEmailId(req.getEmailId());
		}
		if (req.getLogoUrl() != null && !req.getLogoUrl().isEmpty()) {
			company.setLogoUrl(req.getLogoUrl());
		}
		if (req.getLanguages() != null && !req.getLanguages().isEmpty()
				&& !company.getLanguages().equals(req.getLanguages())) {
			company.setLanguages(req.getLanguages());
		}
		if (req.getRoutingUrl() != null && !req.getRoutingUrl().isEmpty()) {
			company.setRoutingUrl(req.getRoutingUrl());
		}
		logger.info("Updating client company details....");
		RegCompanyClientReq clientReq = new RegCompanyClientReq();
		clientReq.setAddress(company.getAddress());
		clientReq.setCode(company.getCode());
		clientReq.setSchoolId(company.getSchoolId());
		clientReq.setContactNumber(company.getContactNumber());
		clientReq.setCountry(company.getCountry());
		clientReq.setCurrency(company.getCurrency());
		clientReq.setCurrencyCode(company.getCurrencyCode());
		clientReq.setCurrencySymbol(company.getCurrencySymbol());
		clientReq.setEmailId(company.getEmailId());
		clientReq.setLanguages(company.getLanguages());
		clientReq.setLogoUrl(company.getLogoUrl());
		clientReq.setName(company.getName());
		clientReq.setRoutingUrl(company.getRoutingUrl());
		clientReq.setStatus(company.getStatus());
		clientReq.setTimeInterval(company.getTimeInterval());
		clientReq.setTimeZone(company.getTimeZone());
		logger.info("Calling routing url for updating company....");
		RegComClientResp clientResp = clientUtil.updateWithClient(company.getRoutingUrl(), clientReq);
		if (clientResp != null) {
			logger.info("Company updated successfully..");
			return dao.update(company);
		}
		return false;
	}

	public boolean updateApps(SchoolUpdateReq reqBean) throws Exception {
		logger.info("inside  updateApps(): getting company details...");
		SchoolEntity company = dao.getSchool(reqBean.getId());
		if (company == null) {
			logger.info("Company doesn't exists");
			throw new Exception("Company doesn't exist.");
		} else {
			logger.info("Company exists , updating..");
			if (!AppConstants.STATUS_ACTIVE.equalsIgnoreCase(company.getStatus())) {
				throw new Exception("Company not active, Please contact support team.");
			}
			if (reqBean.getMappedApps() != null && !reqBean.getMappedApps().isEmpty()
					&& !"null".equalsIgnoreCase(reqBean.getMappedApps())) {
				company.setMappedApps(reqBean.getMappedApps());
			}
			logger.info("Company updated successfully..");
			return dao.update(company);
		}
	}
}

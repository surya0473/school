package com.sv.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sv.constants.AppConstants;
import com.sv.constants.StatusConstants;
import com.sv.dao.impl.SchoolDao;
import com.sv.dao.impl.CustomerDao;
import com.sv.dao.impl.GlobalConfigDao;
import com.sv.dao.impl.UserDao;
import com.sv.entity.SchoolEntity;
import com.sv.entity.CustomerEntity;
import com.sv.entity.RolesEntity;
import com.sv.entity.UserEntity;
import com.sv.entity.UserRoleEntity;
import com.sv.resp.bean.CreateUserReq;
import com.sv.resp.bean.LoginVerifyReq;
import com.sv.resp.bean.UserRoleUpdateReq;
import com.sv.resp.bean.UserUpdateReqBean;
import com.sv.resp.bean.VerifyUserResp;
import com.sv.util.DateUtil;
import com.sv.util.GenerateUserIdUtil;

@Service
public class UserService {
	Logger logger = LoggerFactory.getLogger(UserService.class);

	@Autowired
	UserDao userDao;

	@Autowired
	CustomerDao customerDao;

	@Autowired
	SchoolDao companyDao;

	@Autowired
	GlobalConfigDao globalDao;

	@Autowired
	GenerateUserIdUtil userIdUtil;

	public UserEntity create(CreateUserReq reqBean) throws Exception {
		logger.info("inside create()...checking user.....");
		SchoolEntity company = companyDao.getCompanyDetails(reqBean.getCompanyId());
		if (company == null) {
			logger.info("Company details does not exist");
			throw new Exception("Company details doesn't exist.");
		}
		if (AppConstants.STATUS_IN_ACTIVE.equalsIgnoreCase(company.getStatus())) {
			logger.info("Company details does not exist");
			throw new Exception("Company is not active.");
		} else if (AppConstants.STATUS_NEW.equalsIgnoreCase(company.getStatus())) {
			if (!AppConstants.SUPERUSER.equalsIgnoreCase(reqBean.getUserRoles())) {
				throw new Exception("Company is not activated.");
			}
		}
		UserEntity user = userDao.checkUser(reqBean.getMobileNo(), reqBean.getEmail());
		if (user == null) {
			logger.info("User doesn't exist with given mobile and email,Creating new User....");
			user = new UserEntity();
			user.setCompanyId(reqBean.getCompanyId());
			user.setCreatedDateTime(DateUtil.getCurrentDateTime(company.getTimeZone()));
			user.setEmailId(reqBean.getEmail());
			user.setMobileNumber(reqBean.getMobileNo());
			user.setStatus(AppConstants.STATUS_ACTIVE);
			user.setUserId(userIdUtil.generateUniqueId());
			if (user.getUserId() != null) {
				logger.info("Saving user....");
				user = userDao.save(user);
				if (user != null) {
					logger.info("User created successfully...verifying roles..");
					try {
						if (reqBean.getUserRoles() != null && !reqBean.getUserRoles().isEmpty()) {
							logger.info("user roles exists:" + reqBean.getUserRoles());
							String[] roles = reqBean.getUserRoles().split(",");
							if (roles != null && roles.length > 0) {
								List<UserRoleEntity> list = new ArrayList<>();
								for (String role : roles) {
									logger.info("Role:" + role);
									UserRoleEntity entity = new UserRoleEntity();
									entity.setRole(role);
									entity.setUserId(user.getUserId());
									entity.setStatus(AppConstants.STATUS_ACTIVE);
									entity.setActivatedDate(DateUtil.getCurrentDate(company.getTimeZone()));
									list.add(entity);
								}
								userDao.saveUserRoles(list);
							}
						}
					} catch (Exception e) {
						logger.error("error while updating user roles", e);
					}
					return user;
				} else {
					logger.info("Unable create user,Please try again later.");
					throw new Exception("Unable create user,Please try again later.");
				}
			} else {
				logger.info("Unable create user,Please try again later.");
				throw new Exception("Unable create user,Please try again later.");
			}
		} else {
			logger.info("User already exists with given Mobile/Email");
			if (user.getCompanyId().equalsIgnoreCase(reqBean.getCompanyId())) {
				logger.info("User already exists with given Mobile/Email .");
				return user;
			} else {
				logger.info("Unable create user,Please try again later.");
				throw new Exception(StatusConstants.USER_ALREADY_EXISTS_WITH_MOBILE_EMAIL);
			}
		}
	}

	public UserEntity update(UserUpdateReqBean reqBean) throws Exception {
		logger.info("inside update()......");
		if (reqBean.getStatus() != null
				&& (AppConstants.STATUS_ACTIVE.equalsIgnoreCase(reqBean.getStatus())
						|| AppConstants.STATUS_IN_ACTIVE.equalsIgnoreCase(reqBean.getStatus()))
				|| AppConstants.STATUS_CLOSED.equalsIgnoreCase(reqBean.getStatus())) {
			logger.info("inside update()...getting user.....");
			UserEntity user = userDao.getUserInfo(reqBean.getUserId());
			if (user == null) {
				logger.info("Company details does not exist");
				throw new Exception("User details doesn't exist.");
			}
			if (!reqBean.getCompanyId().equalsIgnoreCase(user.getCompanyId())) {
				logger.info("Company details does not exist");
				throw new Exception("Company details are not matching.");
			}
			SchoolEntity company = companyDao.getCompanyDetails(user.getCompanyId());
			if (company == null) {
				logger.info("Company details does not exist");
				throw new Exception("Company details doesn't exist.");
			}
			if (!AppConstants.STATUS_ACTIVE.equalsIgnoreCase(company.getStatus())) {
				logger.info("Company is not active");
				throw new Exception("Company is not active.");
			}
			logger.info("User exists");
			user.setStatus(reqBean.getStatus());
			user = userDao.update(user);
			if (user != null) {
				logger.info("User updated successfully...");
				if (AppConstants.STATUS_CLOSED.equalsIgnoreCase(reqBean.getStatus())) {
					logger.info("closing user roles...");
					userDao.deactiveUserRoles(reqBean.getUserId(), AppConstants.STATUS_IN_ACTIVE,
							DateUtil.getCurrentDate(company.getTimeZone()));
				}
				return user;
			} else {
				logger.info("Unable update user,Please try again later.");
				throw new Exception("Unable update user,Please try again later.");
			}
		} else {
			logger.info("Invalid status");
			throw new Exception("Invalid status");
		}
	}

	public UserEntity updateUserRoles(UserRoleUpdateReq reqBean) throws Exception {
		logger.info("inside updateUserRoles()......getting user.....");
		UserEntity user = userDao.getUserInfo(reqBean.getUserId());
		if (user == null) {
			logger.info("Company details does not exist");
			throw new Exception("User details doesn't exist.");
		}
		if (!reqBean.getCompanyId().equalsIgnoreCase(user.getCompanyId())) {
			logger.info("Company details does not exist");
			throw new Exception("Company details are not matching.");
		}
		SchoolEntity company = companyDao.getCompanyDetails(user.getCompanyId());
		if (company == null) {
			logger.info("Company details does not exist");
			throw new Exception("Company details doesn't exist.");
		}
		if (!AppConstants.STATUS_ACTIVE.equalsIgnoreCase(company.getStatus())) {
			logger.info("Company is not active");
			throw new Exception("Company is not active.");
		}
		logger.info("User exists,fetching existing all roles..new roles:" + reqBean.getUserRoles());
		List<UserRoleEntity> existingRoles = userDao.getUserRoles(reqBean.getUserId());
		if (reqBean.getUserRoles() != null && !reqBean.getUserRoles().isEmpty()) {
			logger.info("new user roles exists:" + reqBean.getUserRoles());
			String[] newRoles = reqBean.getUserRoles().split(",");
			if (newRoles != null && newRoles.length > 0) {
				List<UserRoleEntity> list = new ArrayList<>();
				for (String newRole : newRoles) {
					logger.info("Role:" + newRole);
					UserRoleEntity entity = null;
					if (existingRoles != null && existingRoles.size() > 0) {
						for (int j = 0; j < existingRoles.size(); j++) {
							UserRoleEntity dbEntity = existingRoles.get(j);
							if (dbEntity.getRole().equalsIgnoreCase(newRole)) {
								entity = dbEntity;
								if (!AppConstants.STATUS_ACTIVE.equalsIgnoreCase(entity.getStatus())) {
									entity.setStatus(AppConstants.STATUS_ACTIVE);
									entity.setActivatedDate(DateUtil.getCurrentDate(company.getTimeZone()));
									entity.setDeActivatedDate(null);
								}
								existingRoles.remove(j);
								break;
							}
						}
					}
					if (entity == null) {
						entity = new UserRoleEntity();
						entity.setRole(newRole);
						entity.setUserId(user.getUserId());
						entity.setStatus(AppConstants.STATUS_ACTIVE);
						entity.setActivatedDate(DateUtil.getCurrentDate(company.getTimeZone()));
						entity.setDeActivatedDate(null);
					}
					list.add(entity);
				}
				userDao.saveOrUpdateUserRoles(list);
			}
		}
		if (existingRoles != null && existingRoles.size() > 0) {
			List<UserRoleEntity> list = new ArrayList<>();
			for (int j = 0; j < existingRoles.size(); j++) {
				UserRoleEntity entity = existingRoles.get(j);
				entity.setStatus(AppConstants.STATUS_IN_ACTIVE);
				entity.setDeActivatedDate(DateUtil.getCurrentDate(company.getTimeZone()));
				list.add(entity);
			}
			userDao.saveOrUpdateUserRoles(list);
		}
		return user;
	}

	public VerifyUserResp verifyUser(LoginVerifyReq req) throws Exception {
		logger.info("inside service verifyUser() method");
		VerifyUserResp resp = null;
		String companyId = null;
		CustomerEntity customer = null;
		UserEntity user = null;
		if (AppConstants.PARENT_APP.equalsIgnoreCase(req.getAppName())
				|| AppConstants.PARENT_APP.equalsIgnoreCase(req.getAppName())) {
			logger.info("Customer login,");
			customer = customerDao.getCustomer(req.getUserId());
			if (customer == null) {
				logger.info("Customer details does not exist");
				throw new Exception("Customer doesn't exist.");
			}
			if (!AppConstants.STATUS_ACTIVE.equalsIgnoreCase(customer.getStatus())) {
				logger.info("Customer is not active.");
				throw new Exception("Customer is not active.");
			}
			companyId = customer.getCompanyId();
		} else {
			logger.info("User login..");
			user = userDao.getUserInfo(req.getUserId());
			if (user == null) {
				logger.info("User details does not exist");
				throw new Exception("User doesn't exist.");
			}
			if (!AppConstants.STATUS_ACTIVE.equalsIgnoreCase(user.getStatus())) {
				logger.info("User is not active.");
				throw new Exception("User is not active.");
			}
			companyId = user.getCompanyId();
		}
		logger.info("Fetching  company details.....");
		if (companyId == null || companyId.isEmpty() || "null".equalsIgnoreCase(companyId)) {
			logger.info("Company details does not exist");
			throw new Exception("Credentials not recognised.");
		}
		SchoolEntity company = companyDao.getCompanyDetails(companyId);
		if (company == null) {
			logger.info("Company details does not exist");
			throw new Exception("Company doesn't exist.");
		}
		if (!AppConstants.STATUS_ACTIVE.equalsIgnoreCase(company.getStatus())) {
			logger.info("User is not active.");
			throw new Exception("Company is not active.");
		}
		logger.info("user found, company active, preparing resp bean...");
		if (company.getMappedApps() == null || company.getMappedApps().isEmpty()
				|| "null".equalsIgnoreCase(company.getMappedApps())) {
			logger.info("No applications enabled with your company.");
			throw new Exception("No applications enabled.");
		}
		String[] apps = company.getMappedApps().split(",");
		boolean isAppMapped = false;
		for (String app : apps) {
			if (app.equalsIgnoreCase(req.getAppName())) {
				isAppMapped = true;
				break;
			}
		}
		if (!isAppMapped) {
			logger.info(" application not yet enabled");
			throw new Exception("This application not yet enabled.");
		}
		if (customer == null) {
			logger.info("User login, Checking user vs app roles..");
			List<UserRoleEntity> existingRoles = userDao.getUserActiveRoles(user.getUserId());
			logger.info("existing roles:" + existingRoles.size());
			if (existingRoles == null || existingRoles.size() < 1) {
				logger.info("You are not authorized to login into any application.");
				throw new Exception("You are not allowed to login with any application.");
			}
			List<RolesEntity> allowedRoles = globalDao.getRoles(new String[] { req.getAppName() });
			logger.info("allowed roles:" + allowedRoles.size());
			if (allowedRoles == null || allowedRoles.size() < 1) {
				logger.info("Unable to authorize login into this application.");
				throw new Exception("Login not allowed with this application.");
			}
			boolean isRoleMapped = false;
			for (UserRoleEntity userRole : existingRoles) {
				for (RolesEntity roles : allowedRoles) {
					if (userRole.getRole().equalsIgnoreCase(roles.getRole())) {
						isRoleMapped = true;
						break;
					}
				}
				if (isRoleMapped) {
					logger.info("role mappped");
					break;
				}
			}
			if (!isRoleMapped) {
				logger.info("You are not allowed to login with this application");
				throw new Exception("You are not allowed to login with this application.");
			}
		}
		resp = new VerifyUserResp();
		resp.setCompanyId(company.getCompanyId());
		resp.setContactNumber(company.getContactNumber());
		resp.setCountry(company.getCountry());
		resp.setEmail(company.getEmailId());
		resp.setLogoUrl(company.getLogoUrl());
		resp.setName(company.getName());
		resp.setRoutingUrl(company.getRoutingUrl());
		resp.setWebsite(company.getWebsite());
		resp.setMappedApps(company.getMappedApps());
		resp.setUserId(user != null ? user.getUserId() : null);
		logger.info("Returing resp Bean..");
		return resp;
	}
}

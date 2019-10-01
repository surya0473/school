package com.sv.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sv.constants.AppConstants;
import com.sv.dao.impl.SchoolDao;
import com.sv.dao.impl.GlobalConfigDao;
import com.sv.entity.AppFeaturesEntity;
import com.sv.entity.AppsEntity;
import com.sv.entity.SchoolEntity;
import com.sv.entity.ConfigurationsEntity;
import com.sv.entity.RolesEntity;
import com.sv.resp.bean.FeaturesMapRespBean;
import com.sv.resp.bean.UpdateAppsReq;
import com.sv.resp.bean.UpdateFeatureMapReqBean;

@Service
public class GlobalConfigService {
	Logger logger = LoggerFactory.getLogger(GlobalConfigService.class);
	@Autowired
	GlobalConfigDao dao;
	@Autowired
	SchoolDao companyDao;

	public List<AppFeaturesEntity> getFeatures() throws Exception {
		logger.info("inside service getFeatures()");
		return dao.getFeatures();
	}

	public List<FeaturesMapRespBean> getAppFeatures(String companyId, String appName) throws Exception {
		logger.info("inside service getAppFeatures()");
		return dao.getAppFeatures(companyId, appName);
	}

	public boolean updateFeatures(UpdateFeatureMapReqBean reqBean) throws Exception {
		logger.info("inside service updateFeatures()");
		return dao.updateFeatures(reqBean);
	}

	public List<ConfigurationsEntity> getConfigurations() throws Exception {
		logger.info("inside service getConfigs()");
		return dao.getConfigurations();
	}

	public List<RolesEntity> getRoles(String appName) {
		logger.info("inside getRoles()");
		return dao.getRoles(new String[] { appName });
	}

	public List<AppsEntity> getApps(String companyId) throws Exception {
		logger.info("inside service getApps():" + companyId);
		List<AppsEntity> apps = dao.getApps();
		if (apps != null && apps.size() > 0) {
			SchoolEntity company = companyDao.getSchoolDetails(companyId);
			if (company == null) {
				apps = null;
			}
			String[] mappedApps = company.getMappedApps() != null ? company.getMappedApps().split(",") : null;
			if (mappedApps != null && mappedApps.length > 0) {
				for (String appName : mappedApps) {
					for (AppsEntity entity : apps) {
						if (appName.equalsIgnoreCase(entity.getAppName())) {
							entity.setIsEnabled("yes");
							break;
						}
					}
				}
			}
		}
		return apps;
	}

	public String updateApps(UpdateAppsReq reqBean) throws Exception {
		logger.info("inside service updateApps()");
		SchoolEntity company = companyDao.getSchoolDetails(reqBean.getSchoolId());
		if (company == null) {
			logger.info("Company details does not exist");
			throw new Exception("Company details doesn't exist.");
		}
		if (!AppConstants.STATUS_ACTIVE.equalsIgnoreCase(company.getStatus())) {
			logger.info("Company details does not exist");
			throw new Exception("Company is not active.");
		}
		String apps = reqBean.getApps() == null || reqBean.getApps().isEmpty()
				|| "null".equalsIgnoreCase(reqBean.getApps()) ? "" : "," + reqBean.getApps();
		company.setMappedApps("MIS" + apps);
		boolean isUpdated = companyDao.update(company);
		if (isUpdated) {
			StringBuffer excludingRoles = new StringBuffer("SUPERUSER");
			for (String appName : company.getMappedApps().split(",")) {
				List<RolesEntity> roles = dao.getRoles(new String[] { appName });
				if (roles != null && roles.size() > 0) {
					for (RolesEntity role : roles) {
						if (!"SUPERUSER".equalsIgnoreCase(role.getRole())) {
							excludingRoles.append("," + role.getRole());
						}
					}
				}
			}
			return excludingRoles.toString();
		}
		return null;
	}

	public List<RolesEntity> userRolesBySuperior(String superiorRoles) {
		logger.info("inside userRolesBySuperior()");
		List<RolesEntity> roles = null;
		String allowedApps = dao.getAppsBySuperiorRoles(superiorRoles);
		if (allowedApps != null && !allowedApps.isEmpty()) {
			if (AppConstants.ALL.equalsIgnoreCase(allowedApps)) {
				roles = dao.getRoles(null);
			} else {
				roles = dao.getRoles(allowedApps.split(","));
			}
		}
		if (roles != null && roles.size() > 0) {
			roles.removeIf(role -> role.getRole().equalsIgnoreCase(AppConstants.SUPERUSER));
		}
		return roles;
	}

	public String superiorRolesByUser(String userRole) {
		logger.info("inside superiorRolesByUser()");
		return dao.superiorRolesByUser(userRole);
	}

}

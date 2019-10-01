package com.sv.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sv.constants.AppConstants;
import com.sv.constants.StatusConstants;
import com.sv.entity.AppFeaturesEntity;
import com.sv.entity.AppsEntity;
import com.sv.entity.ConfigurationsEntity;
import com.sv.entity.RolesEntity;
import com.sv.resp.bean.ClientRespBean;
import com.sv.resp.bean.FeaturesMapRespBean;
import com.sv.resp.bean.UpdateAppsReq;
import com.sv.resp.bean.UpdateFeatureMapReqBean;
import com.sv.service.GlobalConfigService;
import com.sv.util.ClientRespUtil;

@RestController
@RequestMapping("/globalConfigs")
public class GlobalConfigController {
	Logger logger = LoggerFactory.getLogger(GlobalConfigController.class);
	@Autowired
	private GlobalConfigService service;

	@GetMapping(value = "/features", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ClientRespBean> getFeatures() {
		logger.info("inside controller getFeatures()..");
		ClientRespBean respBean = null;
		try {
			List<AppFeaturesEntity> features = service.getFeatures();
			if (features == null || features.size() <= 0) {
				respBean = ClientRespUtil.getInstance().buildResp(null, AppConstants.STATUS_ERROR,
						"FEATURES_NOT_FOUND", "Features not found");
			} else {
				respBean = ClientRespUtil.getInstance().buildResp(features, AppConstants.STATUS_SUCCESS,
						"FEATURES_FOUND", "Features found:" + features.size());
			}
		} catch (Exception e) {
			respBean = ClientRespUtil.getInstance().buildResp(null, AppConstants.STATUS_ERROR,
					StatusConstants.SYSTEM_ERROR, "System error,Please try again later.");
		}
		return ClientRespUtil.getInstance().buildRetunResp(respBean);
	}

	@GetMapping(value = "/features/{companyId}/{appName}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ClientRespBean> getAppFeatures(@PathVariable("companyId") String companyId,
			@PathVariable("appName") String appName) {
		logger.info("inside controller getAppFeatures()");
		ClientRespBean respBean = null;
		try {
			if (appName == null || appName.isEmpty() || "null".equalsIgnoreCase(appName)) {
				respBean = ClientRespUtil.getInstance().buildResp(null, AppConstants.STATUS_ERROR,
						"FEATURES_NOT_UPDATED", "App name is missing.");
			} else if (companyId == null || companyId.isEmpty() || "null".equalsIgnoreCase(companyId)) {
				respBean = ClientRespUtil.getInstance().buildResp(null, AppConstants.STATUS_ERROR,
						"FEATURES_NOT_UPDATED", "Company id is missing.");
			} else {
				List<FeaturesMapRespBean> features = service.getAppFeatures(companyId, appName);
				if (features == null || features.size() <= 0) {
					respBean = ClientRespUtil.getInstance().buildResp(null, AppConstants.STATUS_ERROR,
							"FEATURES_NOT_FOUND", "Features not found");
				} else {
					respBean = ClientRespUtil.getInstance().buildResp(features, AppConstants.STATUS_SUCCESS,
							"FEATURES_FOUND", "Features found:" + features.size());
				}
			}
		} catch (Exception e) {
			respBean = ClientRespUtil.getInstance().buildResp(null, AppConstants.STATUS_ERROR,
					StatusConstants.SYSTEM_ERROR, "System error,Please try again later.");
		}
		return ClientRespUtil.getInstance().buildRetunResp(respBean);
	}

	@PostMapping(value = "/features/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ClientRespBean> updateFeatures(@RequestBody UpdateFeatureMapReqBean reqBean) {
		logger.info("inside controller updateFeatures():" + reqBean.toString());
		ClientRespBean respBean = null;
		try {
			if (reqBean.getAppName() == null || reqBean.getAppName().isEmpty()
					|| "null".equalsIgnoreCase(reqBean.getAppName())) {
				respBean = ClientRespUtil.getInstance().buildResp(null, AppConstants.STATUS_ERROR,
						"FEATURES_NOT_UPDATED", "App name is missing.");
			} else if (reqBean.getSchoolId() == null || reqBean.getSchoolId().isEmpty()
					|| "null".equalsIgnoreCase(reqBean.getSchoolId())) {
				respBean = ClientRespUtil.getInstance().buildResp(null, AppConstants.STATUS_ERROR,
						"FEATURES_NOT_UPDATED", "Company id is missing.");
			} else if (reqBean.getFeatures() == null || reqBean.getFeatures().size() < 1) {
				respBean = ClientRespUtil.getInstance().buildResp(null, AppConstants.STATUS_ERROR,
						"FEATURES_NOT_UPDATED", "Features are missing.");
			} else {
				logger.info("calling updateFeatures....");
				boolean isUpdated = service.updateFeatures(reqBean);
				if (!isUpdated) {
					respBean = ClientRespUtil.getInstance().buildResp(null, AppConstants.STATUS_ERROR,
							"FEATURES_NOT_UPDATED", "Features not updated.");
				} else {
					respBean = ClientRespUtil.getInstance().buildResp(null, AppConstants.STATUS_SUCCESS,
							"FEATURES_UPDATED", "Features updated successfully");
				}
			}
		} catch (Exception e) {
			respBean = ClientRespUtil.getInstance().buildResp(null, AppConstants.STATUS_ERROR,
					StatusConstants.SYSTEM_ERROR, "System error,Please try again later.");
		}
		return ClientRespUtil.getInstance().buildRetunResp(respBean);
	}

	@GetMapping(value = "/configurations", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ClientRespBean> getConfigurations() {
		logger.info("inside controller getConfigurations()");
		ClientRespBean respBean = null;
		try {
			List<ConfigurationsEntity> configs = service.getConfigurations();
			if (configs == null || configs.size() <= 0) {
				respBean = ClientRespUtil.getInstance().buildResp(null, AppConstants.STATUS_ERROR,
						"CONFIGS_NOT_FOUND", "Configs not found");
			} else {
				respBean = ClientRespUtil.getInstance().buildResp(configs, AppConstants.STATUS_SUCCESS,
						"CONFIGS_FOUND", "Configs found:" + configs.size());
			}
		} catch (Exception e) {
			respBean = ClientRespUtil.getInstance().buildResp(null, AppConstants.STATUS_ERROR,
					StatusConstants.SYSTEM_ERROR, "System error,Please try again later.");
		}
		return ClientRespUtil.getInstance().buildRetunResp(respBean);
	}

	@GetMapping("/roles/{appName}")
	public ResponseEntity<ClientRespBean> getRoles(@PathVariable("appName") String appName) {
		logger.info("inside controller getRoles() method" + appName);
		ClientRespBean respBean = null;
		try {
			List<RolesEntity> roles = service.getRoles(appName);
			if (roles != null && roles.size() > 0) {
				respBean = ClientRespUtil.getInstance().buildResp(roles, AppConstants.STATUS_SUCCESS,
						StatusConstants.ROLES_FOUND, "Roles found.");
			} else {
				respBean = ClientRespUtil.getInstance().buildResp(null, AppConstants.STATUS_ERROR,
						StatusConstants.NO_ROLES_FOUND, "No roles found.");
			}
		} catch (Exception e) {
			logger.info("error while getRoles ..", e);
			respBean = ClientRespUtil.getInstance().buildResp(null, AppConstants.STATUS_ERROR,
					StatusConstants.NO_ROLES_FOUND, e.getMessage());
		}
		return ClientRespUtil.getInstance().buildRetunResp(respBean);
	}

	@GetMapping(value = "/apps/{companyId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ClientRespBean> getApps(@PathVariable("companyId") String companyId) {
		logger.info("inside controller getApps():" + companyId);
		ClientRespBean respBean = null;
		try {
			List<AppsEntity> apps = service.getApps(companyId);
			if (apps == null || apps.size() <= 0) {
				respBean = ClientRespUtil.getInstance().buildResp(null, AppConstants.STATUS_ERROR, "APPS_NOT_FOUND",
						"Apps not found");
			} else {
				respBean = ClientRespUtil.getInstance().buildResp(apps, AppConstants.STATUS_SUCCESS, "APPS_FOUND",
						"Apps found:" + apps.size());
			}
		} catch (Exception e) {
			respBean = ClientRespUtil.getInstance().buildResp(null, AppConstants.STATUS_ERROR,
					StatusConstants.SYSTEM_ERROR, "System error,Please try again later.");
		}
		return ClientRespUtil.getInstance().buildRetunResp(respBean);
	}

	@PostMapping(value = "/apps/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ClientRespBean> updateApps(@RequestBody UpdateAppsReq reqBean) {
		logger.info("inside controller updateApps():" + reqBean.toString());
		ClientRespBean respBean = null;
		try {
			if (reqBean.getSchoolId() == null || reqBean.getSchoolId().isEmpty()
					|| "null".equalsIgnoreCase(reqBean.getSchoolId())) {
				respBean = ClientRespUtil.getInstance().buildResp(null, AppConstants.STATUS_ERROR, "APPS_NOT_MAPPED",
						"Company id is missing.");
			} else {
				logger.info("calling updateApps....");
				String excludingRoles = service.updateApps(reqBean);
				if (excludingRoles == null) {
					respBean = ClientRespUtil.getInstance().buildResp(null, AppConstants.STATUS_ERROR,
							"APPS_NOT_MAPPED", "Apps not updated.");
				} else {
					respBean = ClientRespUtil.getInstance().buildResp(excludingRoles, AppConstants.STATUS_SUCCESS,
							"APPS_MAPPED", "Apps updated successfully");
				}
			}
		} catch (Exception e) {
			respBean = ClientRespUtil.getInstance().buildResp(null, AppConstants.STATUS_ERROR,
					StatusConstants.SYSTEM_ERROR, "System error,Please try again later.");
		}
		return ClientRespUtil.getInstance().buildRetunResp(respBean);
	}

	@GetMapping("/user/roles/by/superior")
	public ResponseEntity<ClientRespBean> userRolesBySuperior(@RequestParam("superiorRoles") String superiorRoles) {
		logger.info("inside controller userRolesBySuperior() method" + superiorRoles);
		ClientRespBean respBean = null;
		try {
			List<RolesEntity> roles = service.userRolesBySuperior(superiorRoles);
			if (roles != null && roles.size() > 0) {
				respBean = ClientRespUtil.getInstance().buildResp(roles, AppConstants.STATUS_SUCCESS,
						StatusConstants.ROLES_FOUND, "Roles found.");
			} else {
				respBean = ClientRespUtil.getInstance().buildResp(null, AppConstants.STATUS_ERROR,
						StatusConstants.NO_ROLES_FOUND, "No roles found.");
			}
		} catch (Exception e) {
			logger.info("error while getRoles ..", e);
			respBean = ClientRespUtil.getInstance().buildResp(null, AppConstants.STATUS_ERROR,
					StatusConstants.NO_ROLES_FOUND, e.getMessage());
		}
		return ClientRespUtil.getInstance().buildRetunResp(respBean);
	}

	@GetMapping("/superior/roles/by/user")
	public ResponseEntity<ClientRespBean> superiorRolesByUser(@RequestParam("userRole") String userRole) {
		logger.info("inside controller superiorRolesByUser() method" + userRole);
		ClientRespBean respBean = null;
		try {
			String roles = service.superiorRolesByUser(userRole);
			if (roles != null) {
				respBean = ClientRespUtil.getInstance().buildResp(roles, AppConstants.STATUS_SUCCESS,
						StatusConstants.ROLES_FOUND, "Roles found.");
			} else {
				respBean = ClientRespUtil.getInstance().buildResp(null, AppConstants.STATUS_ERROR,
						StatusConstants.NO_ROLES_FOUND, "No roles found.");
			}
		} catch (Exception e) {
			logger.info("error while getRoles ..", e);
			respBean = ClientRespUtil.getInstance().buildResp(null, AppConstants.STATUS_ERROR,
					StatusConstants.NO_ROLES_FOUND, e.getMessage());
		}
		return ClientRespUtil.getInstance().buildRetunResp(respBean);
	}

}

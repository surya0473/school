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
import com.sv.entity.SchoolEntity;
import com.sv.resp.bean.ActivateCompanyReq;
import com.sv.resp.bean.ClientRespBean;
import com.sv.resp.bean.CompanyRegReq;
import com.sv.resp.bean.CompanyUpdateReq;
import com.sv.service.SchoolService;
import com.sv.util.ClientRespUtil;

@RestController
@RequestMapping("/schoolServices")
public class SchoolController {
	Logger logger = LoggerFactory.getLogger(SchoolController.class);

	@Autowired
	SchoolService service;

	@Autowired
	ClientRespUtil respUtil;

	@GetMapping(value = "/getAll/{status}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ClientRespBean> getAll(@PathVariable(value = "status") Integer status) {
		logger.info("inside controller getAll method");
		ClientRespBean respBean = null;
		try {
			List<SchoolEntity> companiesLst = service.getAll(status);
			if (companiesLst == null || companiesLst.size() <= 0) {
				respBean = respUtil.buildResp(null, AppConstants.STATUS_ERROR, StatusConstants.SCHOOLS_NOT_FOUND,
						"No companies found");
			} else {
				respBean = ClientRespUtil.getInstance().buildResp(companiesLst, AppConstants.STATUS_SUCCESS,
						StatusConstants.SCHOOLS_FOUND, "companies found.");
			}
		} catch (Exception ex) {
			logger.info("ex");
			logger.info("error while getting companies:" + ex.getMessage());
			respBean = respUtil.buildResp(null, AppConstants.STATUS_ERROR, StatusConstants.SYSTEM_ERROR,
					"System error,please try again later.");
		}
		return respUtil.buildRetunResp(respBean);
	}

	@GetMapping(value = "/getSchool/{schoolId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ClientRespBean> getSchoolDetails(@PathVariable(value = "schoolId") String schoolId) {
		logger.info("inside controller getSchoolDetails():" + schoolId);
		ClientRespBean respBean = null;
		try {
			SchoolEntity School = service.getCompanyDetails(schoolId);
			if (School == null) {
				respBean = respUtil.buildResp(null, AppConstants.STATUS_ERROR, StatusConstants.SCHOOLS_NOT_FOUND,
						"No companies found");
			} else {
				respBean = ClientRespUtil.getInstance().buildResp(School, AppConstants.STATUS_SUCCESS,
						StatusConstants.SCHOOLS_FOUND, "School details found.");
			}
		} catch (Exception ex) {
			logger.info("error while getting companies:" + ex.getMessage());
			respBean = respUtil.buildResp(null, AppConstants.STATUS_ERROR, StatusConstants.SYSTEM_ERROR,
					"System error,please try again later.");
		}
		return respUtil.buildRetunResp(respBean);
	}

	@GetMapping(value = "/verify", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ClientRespBean> getVerifySchool(@RequestParam(value = "domain") String domain) {
		logger.info("inside controller getVerifySchool():" + domain);
		ClientRespBean respBean = null;
		try {
			SchoolEntity School = service.verifyCompany(domain);
			if (School == null) {
				respBean = respUtil.buildResp(null, AppConstants.STATUS_ERROR, StatusConstants.SCHOOLS_NOT_FOUND,
						"No companies found");
			} else {
				respBean = ClientRespUtil.getInstance().buildResp(School, AppConstants.STATUS_SUCCESS,
						StatusConstants.SCHOOLS_FOUND, "School details found.");
			}
		} catch (Exception ex) {
			logger.info("error while getting companies:" + ex.getMessage());
			respBean = respUtil.buildResp(null, AppConstants.STATUS_ERROR, StatusConstants.SYSTEM_ERROR,
					"System error,please try again later.");
		}
		return respUtil.buildRetunResp(respBean);
	}

	@PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ClientRespBean> register(@RequestBody CompanyRegReq req) {
		logger.info("inside register() :" + req);
		ClientRespBean resp = null;
		try {
			SchoolEntity School = service.register(req);
			if (School == null) {
				resp = respUtil.buildResp(null, AppConstants.STATUS_ERROR, StatusConstants.COMPANY_REG_FAILED,
						"Registration failed,Please try again later.");
			} else {
				resp = respUtil.buildResp(School, AppConstants.STATUS_SUCCESS, StatusConstants.COMPANY_REG_SUCCESS,
						"Registration done successfully.");
			}
		} catch (Exception ex) {
			logger.info("error:" + ex.getMessage());
			resp = respUtil.buildResp(null, AppConstants.STATUS_ERROR, StatusConstants.COMPANY_REG_FAILED,
					ex.getMessage());
		}
		return respUtil.buildRetunResp(resp);
	}

	@PostMapping(value = "/activate", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ClientRespBean> activate(@RequestBody ActivateCompanyReq reqBean) {
		logger.info("inside activate() :" + reqBean.toString());
		ClientRespBean respBean = null;
		try {
			SchoolEntity School = service.activate(reqBean);
			if (School == null) {
				respBean = respUtil.buildResp(null, AppConstants.STATUS_ERROR, StatusConstants.COMPANY_ACT_FAILED,
						"Activation failed,Please try again later.");
			} else {
				respBean = respUtil.buildResp(School, AppConstants.STATUS_SUCCESS, StatusConstants.COMPANY_ACT_SUCCESS,
						"Activated successfully.");
			}
		} catch (Exception ex) {
			logger.info("error:" + ex.getMessage());
			respBean = respUtil.buildResp(null, AppConstants.STATUS_ERROR, StatusConstants.COMPANY_ACT_FAILED,
					ex.getMessage());
		}
		return respUtil.buildRetunResp(respBean);
	}

	@PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ClientRespBean> update(@RequestBody CompanyUpdateReq req) {
		logger.info("inside update() :" + req.toString());
		ClientRespBean resp = null;
		try {
			boolean isUpdated = service.update(req);
			if (!isUpdated) {
				resp = respUtil.buildResp(null, AppConstants.STATUS_ERROR, StatusConstants.COMPANY_UPDATE_FAILED,
						"Update failed,Please try again later.");
			} else {
				resp = respUtil.buildResp(null, AppConstants.STATUS_SUCCESS, StatusConstants.COMPANY_UPDATE_SUCCESS,
						"Updated successfully.");
			}
		} catch (Exception ex) {
			logger.info("error:" + ex.getMessage());
			resp = respUtil.buildResp(null, AppConstants.STATUS_ERROR, StatusConstants.COMPANY_UPDATE_FAILED,
					ex.getMessage());
		}
		return respUtil.buildRetunResp(resp);
	}

	@PostMapping(value = "/apps/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ClientRespBean> updateApps(@RequestBody CompanyUpdateReq reqBean) {
		logger.info("inside updateApps() :" + reqBean.toString());
		ClientRespBean respBean = null;
		try {
			boolean isUpdated = service.updateApps(reqBean);
			if (!isUpdated) {
				respBean = respUtil.buildResp(null, AppConstants.STATUS_ERROR, StatusConstants.COMPANY_UPDATE_FAILED,
						"Update failed,Please try again later.");
			} else {
				respBean = respUtil.buildResp(null, AppConstants.STATUS_SUCCESS, StatusConstants.COMPANY_UPDATE_SUCCESS,
						"Updated successfully.");
			}
		} catch (Exception ex) {
			logger.info("error:" + ex.getMessage());
			respBean = respUtil.buildResp(null, AppConstants.STATUS_ERROR, StatusConstants.COMPANY_UPDATE_FAILED,
					ex.getMessage());
		}
		return respUtil.buildRetunResp(respBean);
	}

}

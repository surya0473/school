package com.sv.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sv.constants.AppConstants;
import com.sv.constants.StatusConstants;
import com.sv.entity.CustomerEntity;
import com.sv.resp.bean.ClientRespBean;
import com.sv.resp.bean.CreateUserReq;
import com.sv.resp.bean.CustomerUpdateReq;
import com.sv.service.CustomerService;
import com.sv.util.ClientRespUtil;

@RestController
@RequestMapping("/customerServices")
public class CustomerController {
	@Autowired
	CustomerService service;
	@Autowired
	ClientRespUtil respUtil;
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ClientRespBean> create(@RequestBody CreateUserReq req) {
		logger.info("inside user create method :" + req.toString());
		ClientRespBean resp = null;
		try {
			CustomerEntity customer = service.create(req);
			if (customer == null) {
				resp = respUtil.buildResp(null, AppConstants.STATUS_ERROR, StatusConstants.CREATION_FAILED,
						"Customer creation failed,Please try again later.");
			} else {
				resp = respUtil.buildResp(customer, AppConstants.STATUS_SUCCESS, StatusConstants.CREATION_SUCCESS,
						"Customer created successfully.");
			}
		} catch (Exception ex) {
			logger.info("error:" + ex.getMessage());
			resp = respUtil.buildResp(null, AppConstants.STATUS_ERROR, StatusConstants.CREATION_FAILED,
					ex.getMessage());
		}
		return respUtil.buildRetunResp(resp);
	}

	@PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody CustomerUpdateReq req) {
		logger.info("inside update():" + req.toString());
		ClientRespBean resp = null;
		try {
			CustomerEntity cust = service.update(req);
			if (cust == null) {
				resp = respUtil.buildResp(null, AppConstants.STATUS_ERROR, StatusConstants.UPDATE_FAILED,
						"Unable to update customer,Please try again later.");
			} else {
				resp = respUtil.buildResp(cust, AppConstants.STATUS_SUCCESS, StatusConstants.UPDATE_SUCCESS,
						"Customer updated successfully.");
			}
		} catch (Exception ex) {
			logger.info("error:" + ex.getMessage());
			resp = respUtil.buildResp(null, AppConstants.STATUS_ERROR, StatusConstants.UPDATE_FAILED,
					ex.getMessage());
		}
		return respUtil.buildRetunResp(resp);
	}

	@PostMapping(value = "/update/mobile", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateMobile(@RequestBody CustomerUpdateReq req) {
		logger.info("inside updateMobile():" + req.toString());
		ClientRespBean resp = null;
		try {
			CustomerEntity cust = service.updateMobile(req);
			if (cust == null) {
				resp = respUtil.buildResp(null, AppConstants.STATUS_ERROR, StatusConstants.UPDATE_FAILED,
						"Unable to update customer,Please try again later.");
			} else {
				resp = respUtil.buildResp(cust, AppConstants.STATUS_SUCCESS, StatusConstants.UPDATE_SUCCESS,
						"Customer updated successfully.");
			}
		} catch (Exception ex) {
			logger.info("error:" + ex.getMessage());
			resp = respUtil.buildResp(null, AppConstants.STATUS_ERROR, StatusConstants.UPDATE_FAILED,
					ex.getMessage());
		}
		return respUtil.buildRetunResp(resp);
	}
}

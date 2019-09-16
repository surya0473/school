
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
import com.sv.entity.UserEntity;
import com.sv.resp.bean.ClientRespBean;
import com.sv.resp.bean.CreateUserReq;
import com.sv.resp.bean.LoginVerifyReq;
import com.sv.resp.bean.UserRoleUpdateReq;
import com.sv.resp.bean.UserUpdateReqBean;
import com.sv.resp.bean.VerifyUserResp;
import com.sv.service.UserService;
import com.sv.util.ClientRespUtil;

@RestController
@RequestMapping("/userServices")
public class UserController {

	public UserController() {
		// TODO Auto-generated constructor stub
	}

	@Autowired
	UserService service;

	@Autowired
	ClientRespUtil respUtil;

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ClientRespBean> create(@RequestBody CreateUserReq reqBean) {
		logger.info("inside user create method :" + reqBean.toString());
		ClientRespBean respBean = null;
		try {
			UserEntity userEntity = service.create(reqBean);
			if (userEntity == null) {
				respBean = respUtil.buildResp(null, AppConstants.STATUS_ERROR, StatusConstants.CREATION_FAILED,
						"User creation failed,Please try again later.");
			} else {
				respBean = respUtil.buildResp(userEntity, AppConstants.STATUS_SUCCESS,
						StatusConstants.CREATION_SUCCESS, "User created successfully.");
			}
		} catch (Exception ex) {
			logger.info("error:" + ex.getMessage());
			respBean = respUtil.buildResp(null, AppConstants.STATUS_ERROR, StatusConstants.CREATION_FAILED,
					ex.getMessage());
		}
		return respUtil.buildRetunResp(respBean);
	}

	@PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody UserUpdateReqBean reqBean) {
		logger.info("inside update():" + reqBean.toString());
		ClientRespBean respBean = null;
		try {
			UserEntity user = service.update(reqBean);
			if (user == null) {
				respBean = respUtil.buildResp(null, AppConstants.STATUS_ERROR, StatusConstants.UPDATE_FAILED,
						"Unable to update user,Please try again later.");
			} else {
				respBean = respUtil.buildResp(user, AppConstants.STATUS_SUCCESS, StatusConstants.UPDATE_SUCCESS,
						"User updated successfully.");
			}
		} catch (Exception ex) {
			logger.info("error:" + ex.getMessage());
			respBean = respUtil.buildResp(null, AppConstants.STATUS_ERROR, StatusConstants.UPDATE_FAILED,
					ex.getMessage());
		}
		return respUtil.buildRetunResp(respBean);
	}

	@PostMapping(value = "/user/roles/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateUserRoles(@RequestBody UserRoleUpdateReq reqBean) {
		logger.info("inside updateUserRoles():" + reqBean.toString());
		ClientRespBean respBean = null;
		try {
			UserEntity user = service.updateUserRoles(reqBean);
			respBean = respUtil.buildResp(user, AppConstants.STATUS_SUCCESS, StatusConstants.UPDATE_SUCCESS,
					"User Roles updated successfully.");
		} catch (Exception ex) {
			logger.info("error:" + ex.getMessage());
			respBean = respUtil.buildResp(null, AppConstants.STATUS_ERROR, StatusConstants.UPDATE_FAILED,
					ex.getMessage());
		}
		return respUtil.buildRetunResp(respBean);
	}

	@PostMapping(value = "/verifyUser", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ClientRespBean> verifyUser(@RequestBody LoginVerifyReq req) {
		logger.info("inside controller verifyUser() method");
		ClientRespBean resp = null;
		try {
			if (req.getUserId() == null || req.getUserId().isEmpty() || "null".equalsIgnoreCase(req.getUserId())
					|| req.getAppName() == null || req.getAppName().isEmpty()
					|| "null".equalsIgnoreCase(req.getAppName())) {
				resp = ClientRespUtil.getInstance().buildResp(null, AppConstants.STATUS_ERROR,
						StatusConstants.VERIFY_FAILED, "User id/mobile/ email is missing,Please try again.");
				return ClientRespUtil.getInstance().buildRetunResp(resp);
			}
			if (req.getAppName() == null || req.getAppName().isEmpty() || "null".equalsIgnoreCase(req.getAppName())) {
				resp = ClientRespUtil.getInstance().buildResp(null, AppConstants.STATUS_ERROR,
						StatusConstants.VERIFY_FAILED, "Login app name is missing,Please try again.");
				return ClientRespUtil.getInstance().buildRetunResp(resp);
			}
			VerifyUserResp verify = service.verifyUser(req);
			if (verify != null) {
				resp = ClientRespUtil.getInstance().buildResp(verify, AppConstants.STATUS_SUCCESS,
						StatusConstants.VERIFY_SUCCESS, "User verified successfully.");
			} else {
				resp = ClientRespUtil.getInstance().buildResp(null, AppConstants.STATUS_ERROR,
						StatusConstants.VERIFY_FAILED, "Unable to verify user,Please try again.");
			}
		} catch (Exception e) {
			logger.info("error while verifying user..", e);
			resp = ClientRespUtil.getInstance().buildResp(null, AppConstants.STATUS_ERROR,
					StatusConstants.VERIFY_FAILED, e.getMessage());
		}
		return ClientRespUtil.getInstance().buildRetunResp(resp);
	}
}
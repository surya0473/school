package com.sv.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sv.constants.AppConstants;
import com.sv.constants.StatusConstants;
import com.sv.resp.bean.ClientRespBean;
import com.sv.service.ConstantService;
import com.sv.util.ClientRespUtil;

@RestController
@RequestMapping("/constants")
public class ConstantController {
	Logger logger = LoggerFactory.getLogger(LanguageController.class);
	@Autowired
	private ConstantService service;

	@GetMapping(value = "/get", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ClientRespBean> get(@RequestParam(name = "country") String country,
			@RequestParam(name = "language") String language, @RequestParam(name = "appName") String appName,
			@RequestParam(name = "type") String type) {
		logger.info("inside controller get()");
		ClientRespBean respBean = null;
		try {
			Map<String,Object> map = service.get(country, language, appName ,type);
			if (map == null || map.size() <= 0) {
				respBean = ClientRespUtil.getInstance().buildResp(null, AppConstants.STATUS_ERROR,
						StatusConstants.CONSTANTS_NOT_FOUND, "No Constants found");
			} else {
				respBean = ClientRespUtil.getInstance().buildResp(map, AppConstants.STATUS_SUCCESS,
						StatusConstants.CONSTANTS_FOUND, "Constants found");
			}
		} catch (Exception e) {
			respBean = ClientRespUtil.getInstance().buildResp(null, AppConstants.STATUS_ERROR,
					StatusConstants.SYSTEM_ERROR, "System error,Please try again later.");
		}
		return ClientRespUtil.getInstance().buildRetunResp(respBean);
	}
}

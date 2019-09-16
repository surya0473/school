package com.sv.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sv.constants.AppConstants;
import com.sv.constants.StatusConstants;
import com.sv.entity.LanguageEntity;
import com.sv.resp.bean.ClientRespBean;
import com.sv.service.LanguageService;
import com.sv.util.ClientRespUtil;

@RestController
@RequestMapping("/languages")
public class LanguageController {

	Logger logger = LoggerFactory.getLogger(LanguageController.class);
	@Autowired
	private LanguageService service;

	@GetMapping(value = "/getAll", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ClientRespBean> getLanguages() {
		logger.info("inside controller getAll method");
		ClientRespBean respBean = null;
		try {
			List<LanguageEntity> list = service.getLanguages();
			if (list == null || list.size() <= 0) {
				respBean = ClientRespUtil.getInstance().buildResp(null, AppConstants.STATUS_ERROR,
						StatusConstants.LANGUAGES_NOT_FOUND, "No languages found");
			} else {
				respBean = ClientRespUtil.getInstance().buildResp(list, AppConstants.STATUS_SUCCESS,
						StatusConstants.LANGUAGES_FOUND, "Languages found:" + list.size());
			}
		} catch (Exception e) {
			respBean = ClientRespUtil.getInstance().buildResp(null, AppConstants.STATUS_ERROR,
					StatusConstants.SYSTEM_ERROR, "System error,Please try again later.");
		}
		return ClientRespUtil.getInstance().buildRetunResp(respBean);
	}
}

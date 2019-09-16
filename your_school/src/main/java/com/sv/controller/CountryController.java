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
import com.sv.entity.CountryEntity;
import com.sv.resp.bean.ClientRespBean;
import com.sv.service.CountryService;
import com.sv.util.ClientRespUtil;

@RestController
@RequestMapping("/countries")
public class CountryController {

	Logger logger = LoggerFactory.getLogger(CountryController.class);
	@Autowired
	private CountryService countryService;

	@GetMapping(value = "/getAll", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ClientRespBean> getCountries() {
		logger.info("inside controller getCountries method");
		ClientRespBean respBean = null;
		try {
			List<CountryEntity> countriesLst = countryService.getCountries();
			if (countriesLst == null || countriesLst.size() <= 0) {
				respBean = ClientRespUtil.getInstance().buildResp(null, AppConstants.STATUS_ERROR,
						StatusConstants.COUNTRIES_NOT_FOUND, "countries not found");
			} else {
				respBean = ClientRespUtil.getInstance().buildResp(countriesLst, AppConstants.STATUS_SUCCESS,
						StatusConstants.COUNTRIES_FOUND, "Countries found:" + countriesLst.size());
			}
		} catch (Exception e) {
			respBean = ClientRespUtil.getInstance().buildResp(null, AppConstants.STATUS_ERROR,
					StatusConstants.SYSTEM_ERROR, "System error,Please try again later.");
		}
		return ClientRespUtil.getInstance().buildRetunResp(respBean);
	}
}

package com.sv.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sv.constants.AppConstants;
import com.sv.resp.bean.ClientRespBean;
import com.sv.resp.bean.RegComClientResp;
import com.sv.resp.bean.RegCompanyClientReq;

@Service
public class ClientRestUtil {

	@Autowired
	RestTemplate restTemplate;

	final String CREATE_COMPANY = "/school/companyServices/create";
	final String UPDATE_COMPANY = "/school/companyServices/update";

	private static final Logger logger = LoggerFactory.getLogger(ClientRestUtil.class);

	public RegComClientResp regWithClient(String routingUrl, RegCompanyClientReq reqBean) throws Exception {
		logger.info("Calling for register company with client..");
		ResponseEntity<ClientRespBean> response = restTemplate.postForEntity(routingUrl + CREATE_COMPANY, reqBean,
				ClientRespBean.class);
		ClientRespBean resp = response.getBody();
		logger.info("Response from client is:" + response);
		if (resp != null) {
			if (AppConstants.STATUS_SUCCESS.equalsIgnoreCase(resp.getStatus())) {
				logger.info("Company register success at client,,,");
				ObjectMapper mapper = new ObjectMapper();
				RegComClientResp bean = mapper.convertValue(resp.getDetails(), RegComClientResp.class);
				return bean;
			} else {
				logger.info("Error from client:" + resp.getStatusDesc());
				throw new Exception(resp.getStatusDesc());
			}
		} else {
			logger.info("No resp from client");
			throw new Exception("System error, Please try again later.");
		}
	}

	public RegComClientResp updateWithClient(String routingUrl, RegCompanyClientReq reqBean) throws Exception {
		logger.info("Calling for updateWithClient");
		ResponseEntity<ClientRespBean> response = restTemplate.postForEntity(routingUrl + UPDATE_COMPANY, reqBean,
				ClientRespBean.class);
		ClientRespBean resp = response.getBody();
		logger.info("Response from client is:" + response);
		if (resp != null) {
			if (AppConstants.STATUS_SUCCESS.equalsIgnoreCase(resp.getStatus())) {
				logger.info("Company update success at client,,,");
				ObjectMapper mapper = new ObjectMapper();
				RegComClientResp bean = mapper.convertValue(resp.getDetails(), RegComClientResp.class);
				return bean;
			} else {
				logger.info("Error from client:" + resp.getStatusDesc());
				throw new Exception(resp.getStatusDesc());
			}
		} else {
			logger.info("No resp from client");
			throw new Exception("System error, Please try again later.");
		}
	}

}
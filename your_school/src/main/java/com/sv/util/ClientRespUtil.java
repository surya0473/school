package com.sv.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sv.resp.bean.ClientRespBean;

@Component
public class ClientRespUtil {

	private static final Logger logger = LoggerFactory.getLogger(ClientRespUtil.class);
	private static ClientRespUtil util = new ClientRespUtil();
	ObjectMapper mapper = null;

	private ClientRespUtil() {

	}

	public static ClientRespUtil getInstance() {
		if (util == null) {
			synchronized (ClientRespUtil.class) {
				if (util == null) {
					util = new ClientRespUtil();
				}
			}
		}
		return util;
	}

	public <T> ClientRespBean buildResp(T t, String status, String statusCode, String statusDesc) {
		ClientRespBean respBean = new ClientRespBean();
		respBean.setStatus(status);
		respBean.setStatusCode(statusCode);
		respBean.setStatusDesc(statusDesc);
		respBean.setDetails(t);
		return respBean;
	}

	public ResponseEntity<ClientRespBean> buildRetunResp(ClientRespBean resp) {
		try {
			if (mapper == null) {
				mapper = new ObjectMapper();
			}
			logger.info("Resp after String:" + mapper.writeValueAsString(resp));
		} catch (Exception e1) {
		}
		ResponseEntity<ClientRespBean> respEntity = new ResponseEntity<ClientRespBean>(resp, HttpStatus.OK);
		return respEntity;
	}

}

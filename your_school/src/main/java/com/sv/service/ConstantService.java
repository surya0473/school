package com.sv.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sv.dao.impl.LableDao;

@Service
public class ConstantService {
	Logger logger = LoggerFactory.getLogger(ConstantService.class);
	@Autowired
	LableDao dao;

	public Map<String, Object> get(String country, String language,String appName,String type) throws Exception {
		logger.info("inside service get() method");
		return dao.get(country, language,appName,type);
	}
}

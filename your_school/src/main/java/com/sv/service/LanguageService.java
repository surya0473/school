package com.sv.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sv.dao.impl.LanguageDao;
import com.sv.entity.LanguageEntity;

@Service
public class LanguageService {
	Logger logger = LoggerFactory.getLogger(LanguageService.class);
	@Autowired
	LanguageDao dao;

	public List<LanguageEntity> getLanguages() throws Exception {
		logger.info("inside service getLanguages() method");
		return dao.getLanguages();
	}
}

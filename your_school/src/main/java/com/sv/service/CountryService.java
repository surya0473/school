package com.sv.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sv.dao.impl.CountryDao;
import com.sv.entity.CountryEntity;


@Service
public class CountryService {
	Logger logger=LoggerFactory.getLogger(CountryService.class);

	@Autowired
	CountryDao countryDao;
	
	public List<CountryEntity> getCountries() throws Exception{
		logger.info("inside service getCountries method");
		return countryDao.getCountries();
	}
	
}

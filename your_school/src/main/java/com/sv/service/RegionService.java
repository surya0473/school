package com.sv.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sv.dao.impl.RegionDao;
import com.sv.entity.RegionsEntity;

@Service
public class RegionService {
	Logger logger=LoggerFactory.getLogger(RegionService.class);
	@Autowired
	RegionDao regionDao;
	public List<RegionsEntity> getRegions() throws Exception{
		logger.info("inside service getRegions()");
		return regionDao.getRegions();
	}
}

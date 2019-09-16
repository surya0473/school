package com.sv.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sv.SessionFactoryUtilz;
import com.sv.constants.AppConstants;
import com.sv.entity.RegionsEntity;


/*
 * @author surya v
 * @date 23/08/2019
 * 
 */

@Repository
public class RegionDao {
	Logger logger = LoggerFactory.getLogger(RegionDao.class);

	@Autowired
	SessionFactoryUtilz sfUtil;

	@SuppressWarnings("unchecked")
	public List<RegionsEntity> getRegions() {
		logger.info("inside dao getRegions()");
		Session session = null;
		List<RegionsEntity> regions = null;
		try {
			session = sfUtil.getSession(AppConstants.DEFAULT_SCHEMA);
			Query query = session.createQuery("from RegionsEntity c");
			regions = (List<RegionsEntity>) query.list();
		} catch (Exception ex) {
			logger.error("error while fetching getRegions:" + ex);
		} finally {
			sfUtil.closeSession(session);
		}
		return regions;
	}

}

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
import com.sv.entity.LanguageEntity;


/*
 * @author surya v
 * @date 23/08/2019
 * 
 */

@Repository
public class LanguageDao {

	Logger logger = LoggerFactory.getLogger(LanguageDao.class);

	@Autowired
	SessionFactoryUtilz sfUtil;

	@SuppressWarnings("unchecked")
	public List<LanguageEntity> getLanguages() {
		logger.info("inside dao getLanguages()");
		Session session = null;
		List<LanguageEntity> list = null;
		try {
			session = sfUtil.getSession(AppConstants.DEFAULT_SCHEMA);
			Query query = session.createQuery("from LanguageEntity c");
			list = (List<LanguageEntity>) query.list();
		} catch (Exception ex) {
			logger.error("error while fetching languages:", ex);
		} finally {
			sfUtil.closeSession(session);
		}
		return list;
	}

}

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
import com.sv.entity.CountryEntity;


/*
 * @author surya v
 * @date 23/08/2019
 * 
 */

@Repository
public class CountryDao {
	Logger logger = LoggerFactory.getLogger(CountryDao.class);

	@Autowired
	SessionFactoryUtilz sfUtil;

	@SuppressWarnings("unchecked")
	public List<CountryEntity> getCountries() {
		logger.info("inside dao getCountries method");
		Session session = null;
		List<CountryEntity> countryList = null;
		try {
			session = sfUtil.getSession(AppConstants.DEFAULT_SCHEMA);
			Query query = session.createQuery("from CountryEntity c");
			countryList = (List<CountryEntity>) query.list();

		} catch (Exception ex) {
			logger.error("error while fetching countries:" + ex);
		} finally {
			sfUtil.closeSession(session);
		}
		return countryList;
	}

	@SuppressWarnings("unchecked")
	public CountryEntity getCountry(String name) {
		logger.info("inside getCountry()");
		Session session = null;
		try {
			session = sfUtil.getSession(AppConstants.DEFAULT_SCHEMA);
			Query query = session.createQuery("from CountryEntity c where c.name=:name");
			query.setString("name", name);
			List<CountryEntity> countryList = (List<CountryEntity>) query.list();
			if (countryList != null && countryList.size() > 0) {
				return countryList.get(0);
			}
		} catch (Exception ex) {
			logger.error("error while fetching countries:" + ex);
		} finally {
			sfUtil.closeSession(session);
		}
		return null;
	}
}

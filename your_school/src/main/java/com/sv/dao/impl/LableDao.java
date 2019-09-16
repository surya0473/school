package com.sv.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sv.SessionFactoryUtilz;
import com.sv.constants.AppConstants;
import com.sv.entity.ConstantEntity;


/*
 * @author surya v
 * @date 23/08/2019
 * 
 */

@Repository
public class LableDao {
	Logger logger = LoggerFactory.getLogger(LanguageDao.class);
	@Autowired
	SessionFactoryUtilz sfUtil;

	@SuppressWarnings({ "unchecked", "deprecation" })
	public Map<String, Object> get(String country, String language, String appName, String type) {
		logger.info("inside dao getLables()");
		Session session = null;
		List<ConstantEntity> list = null;
		try {
			session = sfUtil.getSession(AppConstants.DEFAULT_SCHEMA);
			Query query = session.createQuery(" from ConstantEntity c where c.language= :language "
					+ " and ( c.country= :country or c.country= :all )  and ( c.appName= :appName or c.appName= :all )");
			query.setString("language", language);
			query.setString("country", country);
			query.setString("appName", appName);
			query.setString("all", AppConstants.ALL);
			list = (List<ConstantEntity>) query.list();
			if (list != null && list.size() > 0) {
				Map<String, Object> map = new HashMap<>();
				if (AppConstants.TYPE_VALIDATION.equalsIgnoreCase(type)) {
					list.forEach(entity -> {
						entity.setAppName(null);
						entity.setCountry(null);
						entity.setLanguage(null);
						map.put(entity.getCode(), entity);
					});
				} else {
					list.forEach(entity -> {
						map.put(entity.getCode(), entity.getValue());
					});
				}
				return map;
			}
		} catch (Exception ex) {
			logger.error("error while fetching constants:", ex);
		} finally {
			sfUtil.closeSession(session);
		}
		return null;
	}

}
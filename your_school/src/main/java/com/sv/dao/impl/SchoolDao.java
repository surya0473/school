package com.sv.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sv.SessionFactoryUtilz;
import com.sv.constants.AppConstants;
import com.sv.entity.SchoolEntity;

/*
 * @author surya v
 * @date 22/08/2019
 * 
 */

@Repository
public class SchoolDao {
	Logger logger = LoggerFactory.getLogger(SchoolDao.class);

	@Autowired
	SessionFactoryUtilz sfUtil;

	@SuppressWarnings({ "unchecked", "rawtypes", "deprecation" })
	public List<SchoolEntity> getAll(Integer status) {
		logger.info("inside dao getAll method");
		Session session = null;
		List<SchoolEntity> schoolList = null;
		try {
			status = status > 0 ? status : 0;
			session = sfUtil.getSession(AppConstants.DEFAULT_SCHEMA);
			Query query = session.createQuery("from SchoolEntity c where c.status in (:status)");
			query.setParameter("status", status);
			schoolList = (List<SchoolEntity>) query.list();
		} catch (Exception ex) {
			logger.error("error while fetching School:" + ex);
		} finally {
			sfUtil.closeSession(session);
		}
		return schoolList;
	}

	@SuppressWarnings("unchecked")
	public SchoolEntity getSchoolDetails(String schoolId) {
		logger.info("inside dao getSchoolDetails():" + schoolId);
		Session session = null;
		SchoolEntity school = null;
		try {
			session = sfUtil.getSession(AppConstants.DEFAULT_SCHEMA);
			Query query = session.createQuery("from SchoolEntity c where c.schoolId = :schoolId");
			query.setParameter("schoolId", schoolId);
			query.setReadOnly(true);
			List<SchoolEntity> schoolList = (List<SchoolEntity>) query.list();
			if (schoolList != null && schoolList.size() > 0) {
				return schoolList.get(0);
			}
		} catch (Exception ex) {
			logger.error("error while fetching School:", ex);
		} finally {
			sfUtil.closeSession(session);
		}
		return school;
	}

	@SuppressWarnings("unchecked")
	public SchoolEntity verify(String website) {
		logger.info("inside dao verify():" + website);
		Session session = null;
		SchoolEntity school = null;
		try {
			session = sfUtil.getSession(AppConstants.DEFAULT_SCHEMA);
			Query query = session.createQuery("from SchoolEntity c where c.website = :website");
			query.setParameter("website", website);
			List<SchoolEntity> schoolList = (List<SchoolEntity>) query.list();
			if (schoolList != null && schoolList.size() > 0) {
				return schoolList.get(0);
			}
		} catch (Exception ex) {
			logger.error("error while fetching School:", ex);
		} finally {
			sfUtil.closeSession(session);
		}
		return school;
	}

	public SchoolEntity getSchool(long id) {
		logger.info("inside dao getSchool():" + id);
		Session session = null;
		try {
			session = sfUtil.getSession(AppConstants.DEFAULT_SCHEMA);
			return (SchoolEntity) session.get(SchoolEntity.class, new Long(id));
		} catch (Exception ex) {
			logger.error("error while fetching School:", ex);
		} finally {
			sfUtil.closeSession(session);
		}
		return null;
	}

	public SchoolEntity save(SchoolEntity school) {
		logger.info("inside dao save():" + school);
		Session session = null;
		try {
			session = sfUtil.getSession(AppConstants.DEFAULT_SCHEMA);
			logger.info("Saving School...");
			Long id = (Long) session.save(school);
			if (id == null || id < 1) {
				logger.info("School created failed. id:" + id);
				return null;
			}
			logger.info("School created successfully. id:" + id);
			school.setId(id);
			return school;
		} catch (Exception ex) {
			logger.error("error while saving School", ex);
		} finally {
			sfUtil.closeSession(session);
		}
		return null;
	}

	public boolean update(SchoolEntity school) {
		logger.info("inside dao update():" + school.toString());
		Session session = null;
		Transaction txn = null;
		try {
			session = sfUtil.getSession(AppConstants.DEFAULT_SCHEMA);
			txn = session.beginTransaction();
			logger.info("Updating school...");
			session.update(school);
			txn.commit();
			return true;
		} catch (Exception ex) {
			logger.error("error while updating School", ex);
			if (txn != null) {
				try {
					txn.rollback();
				} catch (Exception e) {

				}
			}
		} finally {
			sfUtil.closeSession(session);
		}
		return false;
	}
}

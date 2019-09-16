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
		List<SchoolEntity> companyList = null;
		try {
			status = status > 0 ? status : 0;
			session = sfUtil.getSession(AppConstants.DEFAULT_SCHEMA);
			Query query = session.createQuery("from SchoolEntity c where c.status in (:status)");
			query.setParameter("status", status);
			companyList = (List<SchoolEntity>) query.list();
		} catch (Exception ex) {
			logger.error("error while fetching companies:" + ex);
		} finally {
			sfUtil.closeSession(session);
		}
		return companyList;
	}

	@SuppressWarnings("unchecked")
	public SchoolEntity getCompanyDetails(String companyId) {
		logger.info("inside dao getCompanyDetails():" + companyId);
		Session session = null;
		SchoolEntity company = null;
		try {
			session = sfUtil.getSession(AppConstants.DEFAULT_SCHEMA);
			Query query = session.createQuery("from SchoolEntity c where c.companyId = :companyId");
			query.setParameter("companyId", companyId);
			query.setReadOnly(true);
			List<SchoolEntity> companyList = (List<SchoolEntity>) query.list();
			if (companyList != null && companyList.size() > 0) {
				return companyList.get(0);
			}
		} catch (Exception ex) {
			logger.error("error while fetching companies:", ex);
		} finally {
			sfUtil.closeSession(session);
		}
		return company;
	}

	@SuppressWarnings("unchecked")
	public SchoolEntity verify(String website) {
		logger.info("inside dao verify():" + website);
		Session session = null;
		SchoolEntity company = null;
		try {
			session = sfUtil.getSession(AppConstants.DEFAULT_SCHEMA);
			Query query = session.createQuery("from SchoolEntity c where c.website = :website");
			query.setParameter("website", website);
			List<SchoolEntity> companyList = (List<SchoolEntity>) query.list();
			if (companyList != null && companyList.size() > 0) {
				return companyList.get(0);
			}
		} catch (Exception ex) {
			logger.error("error while fetching companies:", ex);
		} finally {
			sfUtil.closeSession(session);
		}
		return company;
	}

	public SchoolEntity getCompany(long id) {
		logger.info("inside dao getCompany():" + id);
		Session session = null;
		try {
			session = sfUtil.getSession(AppConstants.DEFAULT_SCHEMA);
			return (SchoolEntity) session.get(SchoolEntity.class, new Long(id));
		} catch (Exception ex) {
			logger.error("error while fetching companies:", ex);
		} finally {
			sfUtil.closeSession(session);
		}
		return null;
	}

	public SchoolEntity save(SchoolEntity company) {
		logger.info("inside dao save():" + company);
		Session session = null;
		try {
			session = sfUtil.getSession(AppConstants.DEFAULT_SCHEMA);
			logger.info("Saving company...");
			Long id = (Long) session.save(company);
			if (id == null || id < 1) {
				logger.info("Company created failed. id:" + id);
				return null;
			}
			logger.info("Company created successfully. id:" + id);
			company.setId(id);
			return company;
		} catch (Exception ex) {
			logger.error("error while saving company:", ex);
		} finally {
			sfUtil.closeSession(session);
		}
		return null;
	}

	public boolean update(SchoolEntity company) {
		logger.info("inside dao update():" + company.toString());
		Session session = null;
		Transaction txn = null;
		try {
			session = sfUtil.getSession(AppConstants.DEFAULT_SCHEMA);
			txn = session.beginTransaction();
			logger.info("Updating company...");
			session.update(company);
			txn.commit();
			return true;
		} catch (Exception ex) {
			logger.error("error while updating company:", ex);
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

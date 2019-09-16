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
import com.sv.entity.CustomerEntity;


/*
 * @author surya v
 * @date 23/08/2019
 * 
 */


@Repository
public class CustomerDao {
	Logger logger = LoggerFactory.getLogger(UserDao.class);

	@Autowired
	SessionFactoryUtilz sfUtil;

	@SuppressWarnings("unchecked")
	public CustomerEntity getCustomer(String mobileNo) {
		logger.info("inside dao getCustomerInfo() method:" + mobileNo);
		Session session = null;
		CustomerEntity custmer = null;
		try {
			session = sfUtil.getSession(AppConstants.DEFAULT_SCHEMA);
			Query query = session.createQuery("from CustomerEntity u where u.mobileNo=:mobileNumber");
			query.setString("mobileNumber", mobileNo);
			query.setReadOnly(true);
			List<CustomerEntity> userList = (List<CustomerEntity>) query.list();
			if (userList != null && userList.size() > 0) {
				custmer = userList.get(0);
			}
		} catch (Exception ex) {
			logger.error("error while fetching User:", ex);
		} finally {
			sfUtil.closeSession(session);
		}
		return custmer;
	}

	public CustomerEntity save(CustomerEntity customer) {
		logger.info("inside dao save() method:" + customer.toString());
		Session session = null;
		try {
			session = sfUtil.getSession(AppConstants.DEFAULT_SCHEMA);
			Long id = (Long) session.save(customer);
			if (id > 0) {
				logger.info("Customer created successfully, id:" + id);
				customer.setId(id);
				return customer;
			}
		} catch (Exception ex) {
			logger.error("error while saving customer:", ex);
		} finally {
			sfUtil.closeSession(session);
		}
		return null;
	}

	public CustomerEntity update(CustomerEntity customer) throws Exception {
		logger.info("inside dao update():");
		Session session = null;
		org.hibernate.Transaction txn = null;
		try {
			session = sfUtil.getSession(AppConstants.DEFAULT_SCHEMA);
			txn = session.beginTransaction();
			session.update(customer);
			txn.commit();
			return customer;
		} catch (Exception ex) {
			logger.error("error while updating customer:", ex);
			if (txn != null) {
				txn.rollback();
			}
			throw new Exception("Unable to update customer.");
		} finally {
			sfUtil.closeSession(session);
		}
	}
}

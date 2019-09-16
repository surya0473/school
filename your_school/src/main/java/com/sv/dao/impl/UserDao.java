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
import com.sv.entity.UserEntity;
import com.sv.entity.UserRoleEntity;

/*
 * @author surya v
 * @date 23/08/2019
 * 
 */

@Repository
public class UserDao {
	Logger logger = LoggerFactory.getLogger(UserDao.class);

	@Autowired
	SessionFactoryUtilz sfUtil;

	@SuppressWarnings("unchecked")
	public UserEntity getUserInfo(String searchStr) {
		logger.info("inside dao getUserInfo() method:" + searchStr);
		Session session = null;
		UserEntity userEntity = null;
		try {
			session = sfUtil.getSession(AppConstants.DEFAULT_SCHEMA);
			Query query = session.createQuery(
					"from UserEntity u where u.mobileNumber=:mobileNumber or u.userId=:userId or u.emailId=:emailId");
			query.setString("userId", searchStr);
			query.setString("mobileNumber", searchStr);
			query.setString("emailId", searchStr);
			List<UserEntity> userList = (List<UserEntity>) query.list();
			if (userList != null && userList.size() > 0) {
				userEntity = userList.get(0);
			}
		} catch (Exception ex) {
			logger.error("error while fetching User:", ex);
		} finally {
			sfUtil.closeSession(session);
		}
		return userEntity;
	}

	public UserEntity save(UserEntity user) {
		logger.info("inside dao save() method:" + user.toString());
		Session session = null;
		try {
			session = sfUtil.getSession(AppConstants.DEFAULT_SCHEMA);
			Long id = (Long) session.save(user);
			if (id > 0) {
				logger.info("User created successfully, id:" + id);
				user.setId(id);
				return user;
			}
		} catch (Exception ex) {
			logger.error("error while saving User:", ex);
		} finally {
			sfUtil.closeSession(session);
		}
		return null;
	}

	public UserEntity update(UserEntity user) throws Exception {
		logger.info("inside dao update():");
		Session session = null;
		org.hibernate.Transaction txn = null;
		try {
			session = sfUtil.getSession(AppConstants.DEFAULT_SCHEMA);
			txn = session.beginTransaction();
			session.update(user);
			txn.commit();
			return user;
		} catch (Exception ex) {
			logger.error("error while updating User:", ex);
			if (txn != null) {
				txn.rollback();
			}
			throw new Exception("Unable to update user.");
		} finally {
			sfUtil.closeSession(session);
		}
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	public UserEntity checkUser(String mobileNo, String emailId) {
		logger.info("inside dao checkUser() method:" + mobileNo + "," + emailId);
		Session session = null;
		UserEntity userEntity = null;
		try {
			session = sfUtil.getSession(AppConstants.DEFAULT_SCHEMA);
			Query query = session
					.createQuery("from UserEntity u where u.mobileNumber=:mobileNumber or u.emailId=:emailId");
			query.setString("mobileNumber", mobileNo);
			query.setString("emailId", emailId);
			List<UserEntity> userList = (List<UserEntity>) query.list();
			if (userList != null && userList.size() > 0) {
				userEntity = userList.get(0);
			}
		} catch (Exception ex) {
			logger.error("error while fetching User:", ex);
		} finally {
			sfUtil.closeSession(session);
		}
		return userEntity;
	}

	@SuppressWarnings("unchecked")
	public String getLastUserId() {
		String lastUserId = null;
		Session session = null;
		try {
			session = sfUtil.getSession(AppConstants.DEFAULT_SCHEMA);
			Query query = session.createQuery("from UserEntity u order by u.id desc");
			List<UserEntity> userList = (List<UserEntity>) query.list();
			if (userList != null && userList.size() > 0) {
				return userList.get(0).getUserId();
			}
		} catch (Exception ex) {
			logger.error("error while fetching last userId:", ex);
		} finally {
			sfUtil.closeSession(session);
		}
		return lastUserId;
	}

	@SuppressWarnings("unchecked")
	public List<UserEntity> getUsers(String companyId) {
		logger.info("inside dao getUsers() method:" + companyId);
		Session session = null;
		try {
			session = sfUtil.getSession(AppConstants.DEFAULT_SCHEMA);
			Query qry = session.createQuery("from UserEntity u where u.companyId=:companyId");
			qry.setParameter("companyId", companyId);
			List<UserEntity> users = qry.list();
			if (users != null) {
				logger.info("Users exists, count:" + users.size());
			}
			return users;
		} catch (Exception ex) {
			logger.error("error while fetching Users:", ex);
		} finally {
			sfUtil.closeSession(session);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<UserRoleEntity> getUserRoles(String userId) {
		logger.info("inside dao getUserRoles() :" + userId);
		Session session = null;
		try {
			session = sfUtil.getSession(AppConstants.DEFAULT_SCHEMA);
			return (List<UserRoleEntity>) session.createQuery("from UserRoleEntity u where u.userId=:userId").setString("userId", userId).list();
		} catch (Exception ex) {
			logger.error("error while fetching User roles:", ex);
		} finally {
			sfUtil.closeSession(session);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<UserRoleEntity> getUserActiveRoles(String userId) {
		logger.info("inside dao getUserRoles() :" + userId);
		Session session = null;
		try {
			session = sfUtil.getSession(AppConstants.DEFAULT_SCHEMA);
			return (List<UserRoleEntity>) session
					.createQuery("from UserRoleEntity u where u.userId=:userId and u.status=:status")
					.setString("userId", userId).setString("status", AppConstants.STATUS_ACTIVE).list();
		} catch (Exception ex) {
			logger.error("error while fetching User roles:", ex);
		} finally {
			sfUtil.closeSession(session);
		}
		return null;
	}

	public void saveUserRoles(List<UserRoleEntity> roles) {
		logger.info("inside dao saveUserRoles() method:");
		Session session = null;
		try {
			session = sfUtil.getSession(AppConstants.DEFAULT_SCHEMA);
			for (UserRoleEntity role : roles) {
				session.save(role);
			}
		} catch (Exception ex) {
			logger.error("error while saving User roles:", ex);
		} finally {
			sfUtil.closeSession(session);
		}
		return;
	}

	public void saveOrUpdateUserRoles(List<UserRoleEntity> roles) throws Exception {
		logger.info("inside dao saveOrUpdateUserRoles() method:");
		Session session = null;
		org.hibernate.Transaction txn = null;
		try {
			session = sfUtil.getSession(AppConstants.DEFAULT_SCHEMA);
			txn = session.beginTransaction();
			for (UserRoleEntity role : roles) {
				session.saveOrUpdate(role);
			}
			txn.commit();
		} catch (Exception ex) {
			logger.error("error while saveOrUpdateUserRoles User roles:", ex);
			try {
				txn.rollback();
			} catch (Exception exs) {

			}
			throw new Exception();
		} finally {
			sfUtil.closeSession(session);
		}
		return;
	}

	public void deactiveUserRoles(String userId, String status, String deactivatedDate) {
		Session session = null;
		try {
			session = sfUtil.getSession(AppConstants.DEFAULT_SCHEMA);
			Query query = session.createQuery(
					"update UserRoleEntity set status =:status, deActivatedDate=:date where userId=:userId");
			query.setParameter("status", status);
			query.setParameter("date", deactivatedDate);
			query.setParameter("userId", userId);
			int cnt = query.executeUpdate();
			logger.info("cnt:" + cnt);
		} catch (Exception ex) {
			logger.error("error while saving User roles:", ex);
		} finally {
			sfUtil.closeSession(session);
		}
		return;
	}
}

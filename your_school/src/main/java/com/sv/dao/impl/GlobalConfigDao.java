package com.sv.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sv.SessionFactoryUtilz;
import com.sv.constants.AppConstants;
import com.sv.entity.AppFeaturesEntity;
import com.sv.entity.AppsEntity;
import com.sv.entity.ConfigurationsEntity;
import com.sv.entity.FeatureMappingEntity;
import com.sv.entity.RolesEntity;
import com.sv.entity.RolesMappingEntity;
import com.sv.resp.bean.FeatureReqBean;
import com.sv.resp.bean.FeaturesMapRespBean;
import com.sv.resp.bean.UpdateFeatureMapReqBean;

/*
 * @author surya v
 * @date 21/08/2019
 * 
 */

@Repository
public class GlobalConfigDao {
	Logger logger = LoggerFactory.getLogger(GlobalConfigDao.class);

	@Autowired
	SessionFactoryUtilz sfUtil;

	@SuppressWarnings("unchecked")
	public List<AppFeaturesEntity> getFeatures() {
		logger.info("inside dao getAll()");
		Session session = null;
		List<AppFeaturesEntity> features = null;
		try {
			session = sfUtil.getSession(AppConstants.DEFAULT_SCHEMA);
			Query query = session.createQuery("from AppFeaturesEntity c");
			features = (List<AppFeaturesEntity>) query.list();
		} catch (Exception ex) {
			logger.error("error while fetching getAll:" + ex);
		} finally {
			sfUtil.closeSession(session);
		}
		return features;
	}

	@SuppressWarnings("unchecked")
	public List<FeaturesMapRespBean> getAppFeatures(String schoolId, String appName) {
		logger.info("inside dao getAppFeatures(),appName:" + appName + ",schoolId:" + schoolId);
		Session session = null;
		List<FeaturesMapRespBean> features = new ArrayList<>();
		try {
			session = sfUtil.getSession(AppConstants.DEFAULT_SCHEMA);
			SQLQuery query = session.createSQLQuery(
					"select f.app_name as appName,f.description,f.feature,ifnull(m.status,'-1') from app_features f "
							+ " left join features_mapping m  on m.feature = f.feature and m.app_name=f.app_name"
							+ " and m.app_name='" + appName + "'  and m.school_id='" + schoolId + "' "
							+ " where f.status='1' having appName = '" + appName + "'");
			List<Object[]> list = query.list();
			for (Object[] row : list) {
				FeaturesMapRespBean bean = new FeaturesMapRespBean();
				bean.setDescription(row[1].toString());
				bean.setFeature(row[2].toString());
				bean.setStatus(row[3].toString());
				features.add(bean);
			}
		} catch (Exception ex) {
			logger.error("error while fetching getAppFeatures:" + ex);
		} finally {
			sfUtil.closeSession(session);
		}
		return features;
	}

	@SuppressWarnings("unchecked")
	public boolean updateFeatures(UpdateFeatureMapReqBean reqBean) {
		logger.info("inside dao updateFeatures():" + reqBean.toString());
		Session session = null;
		Transaction txn = null;
		boolean isUpdated = false;
		try {
			session = sfUtil.getSession(AppConstants.DEFAULT_SCHEMA);
			Query query = session.createQuery("from FeatureMappingEntity where appName=:appName "
					+ " and feature=:feature and schoolId=:schoolId");
			txn = session.beginTransaction();
			for (FeatureReqBean feature : reqBean.getFeatures()) {
				logger.info("feature:" + feature.getFeature() + ", status :" + feature.getStatus());
				query.setParameter("appName", reqBean.getAppName());
				query.setParameter("feature", feature.getFeature());
				query.setParameter("schoolId", reqBean.getSchoolId());
				logger.info("Veryfying feature mapping status...");
				List<FeatureMappingEntity> list = query.list();
				if (list != null && list.size() > 0) {
					logger.info("feature already mapped, updating existing...");
					FeatureMappingEntity dbFeature = list.get(0);
					dbFeature.setStatus(feature.getStatus());
					session.update(dbFeature);
				} else {
					logger.info("feature doesn't mapped, inserting..");
					FeatureMappingEntity dbFeature = new FeatureMappingEntity();
					dbFeature.setAppName(reqBean.getAppName());
					dbFeature.setSchoolId(reqBean.getSchoolId());
					dbFeature.setFeature(feature.getFeature());
					dbFeature.setStatus(feature.getStatus());
					logger.info("Calling save..");
					session.save(dbFeature);
				}
			}
			logger.info("Updating done, comitting ....");
			txn.commit();
			logger.info("App features updated successfully..");
			isUpdated = true;
		} catch (Exception ex) {
			logger.info("Rollbacking update app features...");
			if (txn != null) {
				try {
					txn.rollback();
				} catch (Exception e) {

				}
			}
			logger.error("error while updating AppFeatures:", ex);
		} finally {
			sfUtil.closeSession(session);
		}
		return isUpdated;
	}

	@SuppressWarnings("unchecked")
	public List<ConfigurationsEntity> getConfigurations() {
		Session session = null;
		List<ConfigurationsEntity> features = null;
		try {
			session = sfUtil.getSession(AppConstants.DEFAULT_SCHEMA);
			Query query = session.createQuery("from ConfigurationsEntity c");
			features = (List<ConfigurationsEntity>) query.list();
		} catch (Exception ex) {
			logger.error("error while fetching getAll:" + ex);
		} finally {
			sfUtil.closeSession(session);
		}
		return features;
	}

	@SuppressWarnings("unchecked")
	public List<RolesEntity> getRoles(String[] appNames) {
		logger.info("inside dao getRoles() method:" + appNames);
		Session session = null;
		try {
			session = sfUtil.getSession(AppConstants.DEFAULT_SCHEMA);
			String sql = "from RolesEntity ";
			if (appNames != null && appNames.length > 0) {
				sql = sql + " where appName in (:appNames) ";
			}
			Query query = session.createQuery(sql);
			if (appNames != null && appNames.length > 0) {
				query.setParameterList("appNames", appNames);
			}
			return (List<RolesEntity>) query.list();
		} catch (Exception ex) {
			logger.error("error while fetching roles:", ex);
		} finally {
			sfUtil.closeSession(session);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<AppsEntity> getApps() {
		Session session = null;
		List<AppsEntity> features = null;
		try {
			session = sfUtil.getSession(AppConstants.DEFAULT_SCHEMA);
			Query query = session.createQuery("from AppsEntity c where c.status='1' and c.appName not in ('MIS')");
			features = (List<AppsEntity>) query.list();
		} catch (Exception ex) {
			logger.error("error while fetching apps:" + ex);
		} finally {
			sfUtil.closeSession(session);
		}
		return features;
	}

	@SuppressWarnings("unchecked")
	public String getAppsBySuperiorRoles(String superiorRoles) {
		logger.info("inside dao getAppsBySuperiorRoles() method:" + superiorRoles);
		Session session = null;
		String allowedApps = "";
		try {
			session = sfUtil.getSession(AppConstants.DEFAULT_SCHEMA);
			String sql = "from RolesMappingEntity where role in (:superiorRoles) and isUser = 0";
			Query query = session.createQuery(sql);
			query.setParameterList("superiorRoles", superiorRoles.split(","));
			List<RolesMappingEntity> list = (List<RolesMappingEntity>) query.list();
			if (list != null && list.size() > 0) {
				for (RolesMappingEntity allowedApp : list) {
					if (AppConstants.ALL.equalsIgnoreCase(allowedApp.getAllowedApps())) {
						logger.info("All apps...");
						return AppConstants.ALL;
					}
					allowedApps = allowedApps + "," + allowedApp.getAllowedApps();
				}
			}
			allowedApps = allowedApps.replaceFirst(",", "");
		} catch (Exception ex) {
			logger.error("error while fetching roles:", ex);
		} finally {
			sfUtil.closeSession(session);
		}
		return allowedApps;
	}

	@SuppressWarnings("unchecked")
	public String superiorRolesByUser(String userRole) {
		logger.info("inside dao superiorRolesByUser() method:" + userRole);
		Session session = null;
		try {
			session = sfUtil.getSession(AppConstants.DEFAULT_SCHEMA);
			String sql = "from RolesMappingEntity where role = :userRole";
			Query query = session.createQuery(sql);
			query.setParameter("userRole", userRole);
			List<RolesMappingEntity> list = (List<RolesMappingEntity>) query.list();
			if (list != null && list.size() > 0) {
				return list.get(0).getSuperiors();
			}
		} catch (Exception ex) {
			logger.error("error while fetching roles:", ex);
		} finally {
			sfUtil.closeSession(session);
		}
		return null;
	}

}

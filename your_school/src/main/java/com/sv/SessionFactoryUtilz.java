package com.sv;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SessionFactoryUtilz {

	Logger logger = LoggerFactory.getLogger(SessionFactoryUtilz.class);

	private SessionFactory sessionFactory = null;

	public SessionFactoryUtilz(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public Session getSession(String tenant) {
		logger.info("inside getSession() for tenant:" + tenant);
		return sessionFactory.withOptions().tenantIdentifier(tenant).openSession();
	}

	public void closeSession(Session session) {
		logger.info("inside closeSession()");
		try {
			if (session != null) {
				session.close();
			}
			logger.info("Session closed...");
		} catch (Exception e) {
			logger.error("error while closing session:", e);
		}
	}
}

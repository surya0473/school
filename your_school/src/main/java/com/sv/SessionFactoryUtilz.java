package com.sv;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
public class SessionFactoryUtilz {

	Logger logger = LoggerFactory.getLogger(SessionFactoryUtilz.class);

	@Autowired
	private SessionFactory s;

	public Session getSession(String tenant) {
		if (tenant == null) {
			tenant = "school";
		}
		logger.info("inside getSession() for tenant:" + tenant);
		return s.withOptions().tenantIdentifier(tenant).openSession();
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

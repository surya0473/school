package com.sv;

import java.util.Properties;

import javax.naming.NamingException;
import javax.sql.DataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class HibernateConfiguration {

	@Autowired
	private DataSource datasource;

	@Bean
	public SessionFactory sessionFactory() throws NamingException {
		LocalSessionFactoryBean localSessionFactory = new LocalSessionFactoryBean();
		try {
			localSessionFactory.setDataSource(datasource);
			localSessionFactory.setPackagesToScan(new String[] { "com.sv.entity" });
			localSessionFactory.setHibernateProperties(hibernateProperties());
			localSessionFactory.afterPropertiesSet();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return localSessionFactory.getObject();
	}

	private Properties hibernateProperties() {
		Properties properties = new Properties();
		properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
		properties.put("hibernate.connection.autocommit", "true");
		properties.put("hibernate.multiTenancy", "database");
		properties.put("hibernate.default_schema", "school");
		properties.put("hibernate.tenant_identifier_resolver", "com.sv.SchemaResolver");
		properties.put("hibernate.multi_tenant_connection_provider", "com.sv.MultiTenantProvider");
		properties.put("hibernate.show_sql", "true");
		properties.put("hibernate.format_sql", "true");
		properties.put("hibernate.hbm2ddl.auto", "create");
		System.out.println("properties:" + properties);
		return properties;
	}

	@Bean
	@Autowired
	public HibernateTransactionManager transactionManager(SessionFactory s) {
		HibernateTransactionManager txManager = new HibernateTransactionManager();
		txManager.setSessionFactory(s);
		return txManager;
	}
}

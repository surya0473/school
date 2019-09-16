package com.sv;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.hibernate.HibernateException;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.hibernate.service.spi.ServiceRegistryAwareService;
import org.hibernate.service.spi.ServiceRegistryImplementor;

@SuppressWarnings("serial")
public class MultiTenantProvider implements MultiTenantConnectionProvider, ServiceRegistryAwareService {

	private DataSource dataSource;

	@Override
	public boolean supportsAggressiveRelease() {
		return false;
	}

	@Override
	public void injectServices(ServiceRegistryImplementor serviceRegistry) {
		try {
			dataSource = dataSourceUtil().getDataSource();
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}

	public DataSourceUtil dataSourceUtil() {
		return new DataSourceUtil();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean isUnwrappableAs(Class clazz) {
		return false;
	}

	@Override
	public <T> T unwrap(Class<T> clazz) {
		return null;
	}

	@Override
	public Connection getAnyConnection() throws SQLException {
		final Connection connection = dataSource.getConnection();
		return connection;
	}

	@Override
	public Connection getConnection(String tenantIdentifier) throws SQLException {
		final Connection connection = getAnyConnection();
		try {
			System.out.println("inside getConnection('" + tenantIdentifier + "')");
			connection.createStatement().execute("USE  " + tenantIdentifier);
		} catch (final Exception e) {
			e.printStackTrace();
			throw new HibernateException("Error trying to alter schema [" + tenantIdentifier + "]", e);
		}
		return connection;
	}

	@Override
	public void releaseAnyConnection(Connection connection) throws SQLException {
		try {
			connection.createStatement().execute("use school");
		} catch (final Exception e) {
			e.printStackTrace();
			throw new HibernateException("Error trying to alter schema: school", e);
		}
		connection.close();
	}

	@Override
	public void releaseConnection(String tenantIdentifier, Connection connection) throws SQLException {
		releaseAnyConnection(connection);
	}
}
package com.sv;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.stereotype.Component;

@Component
public class SchemaResolver implements CurrentTenantIdentifierResolver {

	private String tenantIdentifier = "test";

	@Override
	public String resolveCurrentTenantIdentifier() {
		return tenantIdentifier;
	}

	@Override
	public boolean validateExistingCurrentSessions() {
		return false;
	}

	public void setTenantIdentifier(String tenantIdentifier) {
		this.tenantIdentifier = tenantIdentifier;
	}
}
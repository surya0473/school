package com.sv;


import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSourceUtil {
	
	@Autowired
    private DataSource dataSource;

	public DataSource getDataSource() {
		return dataSource;
	}

}

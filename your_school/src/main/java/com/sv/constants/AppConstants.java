package com.sv.constants;

public interface AppConstants {

	String JNDI_NAME = "java:comp/env/jdbc/school";
	String DEFAULT_SCHEMA = "school";
	String STATUS_ERROR = "ERROR";
	String STATUS_SUCCESS = "SUCCESS";
	String LANGUAGE = "language";
	String LOGIN_SUCCESS_MSG = null;
	String LOGIN_NOT_FOUND_ERROR_MSG_CODE = null;
	String SYSTEM_ERROR_MSG = null;
	String LOGIN_NOT_FOUND_ERROR_MSG = null;

	String STATUS_NEW = "-1";
	String STATUS_IN_ACTIVE = "0";
	String STATUS_ACTIVE = "1";
	String STATUS_CLOSED = "2";

	String ALL = "ALL";

	String TYPE_VALIDATION = "validation";
	String MIS = "MIS";
	String SUPERUSER = "SUPERUSER";
	String PARENT_APP="parentsApp";

}

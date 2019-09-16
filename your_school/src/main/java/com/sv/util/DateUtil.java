package com.sv.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateUtil {

	private static SimpleDateFormat dateFormat1 = null;
	private static SimpleDateFormat dateFormat2 = null;
	private static SimpleDateFormat dateFormat3 = null;
	private static SimpleDateFormat dateFormat4 = null;
	private static SimpleDateFormat dateFormatHMMSS = null;
	static {
		dateFormat1 = new SimpleDateFormat("dd-MM-yyyy");
		dateFormat2 = new SimpleDateFormat("dd-MM-yyyy H:mm:ss");
		dateFormat3 = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat4 = new SimpleDateFormat("yyyy-MM-dd H:mm:ss");
		dateFormatHMMSS = new SimpleDateFormat("H:mm:ss");
	}

	public static TimeZone getLocalTimeZone(String timeZone) {
		if (timeZone == null || timeZone.isEmpty()) {
			timeZone = "UTC";
		}
		return TimeZone.getTimeZone(timeZone);
	}

	public static String getCurrentDate(String timeZone) {
		dateFormat3.setTimeZone(getLocalTimeZone(timeZone));
		return dateFormat3.format(new Date());
	}

	public static String getCurrentDateTime(String timeZone) {
		dateFormat4.setTimeZone(getLocalTimeZone(timeZone));
		return dateFormat4.format(new Date());
	}

	public static String getCurrentDate1(String timeZone) {
		dateFormat1.setTimeZone(getLocalTimeZone(timeZone));
		return dateFormat1.format(new Date());
	}

	public static String getCurrentDateTime1(String timeZone) {
		dateFormat2.setTimeZone(getLocalTimeZone(timeZone));
		return dateFormat2.format(new Date());
	}

	public static String getCurrentHourAndMinutes(String timeZone) {
		dateFormatHMMSS.setTimeZone(getLocalTimeZone(timeZone));
		return dateFormatHMMSS.format(new Date());
	}

	public static String getHoursAndMinutesAndSecs(int hrs, int mins, int secs, String timeZone) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeZone(getLocalTimeZone(timeZone));
		calendar.setTime(new Date());
		calendar.set(Calendar.HOUR_OF_DAY, hrs);
		calendar.set(Calendar.MINUTE, mins);
		calendar.set(Calendar.SECOND, secs);
		dateFormatHMMSS.setTimeZone(getLocalTimeZone(timeZone));
		return dateFormatHMMSS.format(calendar.getTime());
	}

	public static String getDate(int interval, String timeZone) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeZone(getLocalTimeZone(timeZone));
		calendar.setTime(new Date());
		calendar.add(Calendar.DATE, interval);
		dateFormat3.setTimeZone(getLocalTimeZone(timeZone));
		return dateFormat3.format(calendar.getTime());
	}

	public static String getWeekDateInterval(int interval, String timeZone) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeZone(getLocalTimeZone(timeZone));
		calendar.setTime(new Date());
		calendar.set(Calendar.DAY_OF_WEEK, 1);
		calendar.add(Calendar.DATE, interval);
		dateFormat3.setTimeZone(getLocalTimeZone(timeZone));
		return dateFormat3.format(calendar.getTime());
	}

	public static String getMonthDateInterval(int interval, String timeZone) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeZone(getLocalTimeZone(timeZone));
		calendar.setTime(new Date());
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.add(Calendar.DATE, interval);
		dateFormat3.setTimeZone(getLocalTimeZone(timeZone));
		return dateFormat3.format(calendar.getTime());
	}

	public static String getYearDateInterval(int interval, String timeZone) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeZone(getLocalTimeZone(timeZone));
		calendar.setTime(new Date());
		calendar.set(Calendar.DAY_OF_YEAR, 1);
		calendar.add(Calendar.DATE, interval);
		dateFormat3.setTimeZone(getLocalTimeZone(timeZone));
		return dateFormat3.format(calendar.getTime());
	}

	public static String getCurrentMonthFirstDate(String timeZone) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeZone(getLocalTimeZone(timeZone));
		calendar.setTime(new Date());
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		dateFormat3.setTimeZone(getLocalTimeZone(timeZone));
		return dateFormat3.format(calendar.getTime());
	}

	public static String getCurrentWeekFirstDate(String timeZone) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeZone(getLocalTimeZone(timeZone));
		calendar.setTime(new Date());
		calendar.set(Calendar.DAY_OF_WEEK, 1);
		dateFormat3.setTimeZone(getLocalTimeZone(timeZone));
		return dateFormat3.format(calendar.getTime());
	}

	public static String getCurrentYearFirstDate(String timeZone) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeZone(getLocalTimeZone(timeZone));
		calendar.setTime(new Date());
		calendar.set(Calendar.DAY_OF_YEAR, 1);
		dateFormat3.setTimeZone(getLocalTimeZone(timeZone));
		return dateFormat3.format(calendar.getTime());
	}

	public static Date getDate(String date, String timeZone) throws Exception {
		dateFormat3.setTimeZone(getLocalTimeZone(timeZone));
		return dateFormat3.parse(date);
	}

	public static String getFirstDayOfWeek(int type, String timeZone) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeZone(getLocalTimeZone(timeZone));
		cal.set(type, cal.getFirstDayOfWeek());
		return dateFormat3.format(cal.getTime());
	}
}

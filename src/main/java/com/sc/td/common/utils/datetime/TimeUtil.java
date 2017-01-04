package com.sc.td.common.utils.datetime;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.format.DateTimeFormat;

/**
 * 时间类
 * 
 * @author Administrator
 *
 */
public class TimeUtil {

	public static final String DBMONTHFormat = "yyyyMM";
	public static final String DBdayFormat = "yyyyMMdd";
	public static final String DSpdayFormat = "yyyy-MM-dd";
	public static final String UIdayFormat = "yyyy年MM月dd日";
	public static final String DBdaytimeFormat = "yyyyMMddHHmmss";
	public static final String UIdaytimeFormat = "yyyy年MM月dd日 HH:mm:ss";
	public static final String DSPdaytimeFormat = "yyyy-MM-dd HH:mm:ss";
	public static final String DBtimeFormat = "HHmmss";
	public static final String UItimeFormat = "HH:mm:ss";

	public static final String YEARS = "YEARS"; // 年
	public static final String MONTHS = "MONTHS"; // 月
	public static final String DAYS = "DAYS"; // 日
	public static final String HOURS = "HOURS"; // 时
	public static final String MINUTES = "MINUTES"; // 分
	public static final String SECONDS = "SECONDS"; // 秒
	public static final String WEEKS = "WEEKS"; // 星期

	/**
	 * 比较两个时间的大小
	 * 
	 * @param dt1
	 * @param dt2
	 * @return
	 */
	public static int compare(DateTime dt1, DateTime dt2) {
		return dt1.compareTo(dt2);
	}

	/**
	 * 增加时间
	 * 
	 * @param time
	 * @return 返回DateTime
	 */
	public static DateTime plusTime(DateTime dateTime, int time, String type) {
		if (YEARS.equals(type)) {
			dateTime = dateTime.plusYears(time);
		} else if (MONTHS.equals(type)) {
			dateTime = dateTime.plusMonths(time);
		} else if (DAYS.equals(type)) {
			dateTime = dateTime.plusDays(time);
		} else if (HOURS.equals(type)) {
			dateTime = dateTime.plusHours(time);
		} else if (MINUTES.equals(type)) {
			dateTime = dateTime.plusMinutes(time);
		} else if (SECONDS.equals(type)) {
			dateTime = dateTime.plusSeconds(time);
		} else if (WEEKS.equals(type)) {
			dateTime = dateTime.plusWeeks(time);
		}
		return dateTime;
	}

	/**
	 * 减少时间
	 * 
	 * @param dateTime
	 * @param time
	 * @param type
	 * @return
	 */
	public static DateTime minusTime(DateTime dateTime, int time, String type) {
		if (YEARS.equals(type)) {
			dateTime = dateTime.minusYears(time);
		} else if (MONTHS.equals(type)) {
			dateTime = dateTime.minusMonths(time);
		} else if (DAYS.equals(type)) {
			dateTime = dateTime.minusDays(time);
		} else if (HOURS.equals(type)) {
			dateTime = dateTime.minusHours(time);
		} else if (MINUTES.equals(type)) {
			dateTime = dateTime.minusMinutes(time);
		} else if (SECONDS.equals(type)) {
			dateTime = dateTime.minusSeconds(time);
		} else if (WEEKS.equals(type)) {
			dateTime = dateTime.minusWeeks(time);
		}
		return dateTime;
	}

	/**
	 * 计算一个日期加上几天后，此段时间内有几个星期六
	 * 
	 * @param dt
	 * @param plus
	 * @return
	 */
	public static int countSatd(DateTime dt, int plusdays) {
		int count = 0;
		for (int i = 0; i < plusdays; i++) {
			dt = dt.plusDays(1);
			if (dt.getDayOfWeek() == DateTimeConstants.SATURDAY) {
				count++;
			}
		}
		return count;
	}

	/**
	 * 日期格式转化为String
	 * 
	 * @param dateTime
	 * @param type
	 * @return
	 */
	public static String dateTime2Str(DateTime dateTime, String formatType) {
		return dateTime.toString(formatType);
	}

	/**
	 * String格式转化为DateTime格式
	 * 
	 * @param time
	 * @param formatType
	 * @return
	 */
	public static DateTime str2DateTime(String time, String formatType) {
		return DateTimeFormat.forPattern(formatType).parseDateTime(time);
	}
}

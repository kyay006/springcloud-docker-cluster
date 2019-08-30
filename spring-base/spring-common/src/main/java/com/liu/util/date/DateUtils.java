package com.liu.util.date;

import com.liu.util.string.StringUtils;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtils {
	private static final Log LOG = LogFactory.getLog(DateUtils.class);

	private static final String SHORT_DATE_FORMAT_STR_NOT_LINE = "yyyyMMdd";
	private static final String SHORT_DATE_FORMAT_STR = "yyyy-MM-dd";
	private static final String SHORT_DATE_FORMAT2_STR = "yyyy/MM/dd";
	public static final String LONG_DATE_FORMAT_STR = "yyyy-MM-dd HH:mm:ss";

	private static final String EARLY_TIME = "00:00:00";
	private static final String LATE_TIME = "23:59:59";

	/**获取当前年份**/
	public static int getNowYear()
	{
		return Calendar.getInstance().get(Calendar.YEAR);
	}

	/**
	 * 使用预设Format格式化Date成字符串
	 *
	 * @return String
	 */
	public static String format(Date date) {
		return date == null ? "" : format(date, LONG_DATE_FORMAT_STR);
	}

	/**
	 * 获取当前时间
	 * 格式为：yyyy-MM-dd HH:mm:ss
	 * @return String
	 */
	public static String getNowDateString() {
		return format(new Date(), LONG_DATE_FORMAT_STR);
	}

	/**
	 * 使用预设Format格式化Date成字符串 yyyy-MM-dd
	 *
	 * @return String
	 */
	public static String getShorDateformat() {
		return format(new Date(), SHORT_DATE_FORMAT_STR);
	}

	/**
	 * 使用预设Format格式化Date成字符串 yyyyMMdd
	 *
	 * @return String
	 */
	public static String getShorDateformatNotLine() {
		return format(new Date(), SHORT_DATE_FORMAT_STR_NOT_LINE);
	}

	/**
	 * 获取当前时间
	 * @return Timestamp
	 */
	public static Timestamp getNowDateTimestamp() {
		return new Timestamp(System.currentTimeMillis());
	}

	/**
	 * 获取转换后的时间
	 * @return String yyyy-MM-dd HH:mm:ss
	 */
	public static String getDateTimestamp(Timestamp timestamp) {
		DateFormat sdf = new SimpleDateFormat(LONG_DATE_FORMAT_STR);
		return sdf.format(timestamp);
	}

	/**
	 * 使用参数Format格式化Date成字符串
	 *
	 * @return String
	 */
	public static String format(Date date, String pattern) {
		return date == null ? "~" : new SimpleDateFormat(pattern).format(date);
	}

	/**
	 * 使用参数Format格式化Date成字符串
	 *
	 * @return String
	 */
	public static String formatShor(Date date) {
		return date == null ? "~" : new SimpleDateFormat(SHORT_DATE_FORMAT_STR).format(date);
	}

	/**
	 * 字符串解析成 java.sql.Date 日期
	 *
	 * @author jianguo.xu
	 * @param shortDateStr
	 * @return
	 */
	public static java.sql.Date parserShortDate(String shortDateStr) {
		if (StringUtils.isBlank(shortDateStr))
			return null;
		DateFormat dateFormate = new SimpleDateFormat(SHORT_DATE_FORMAT_STR);
		try {
			Date date = dateFormate.parse(shortDateStr);
			return new java.sql.Date(date.getTime());
		} catch (ParseException e) {
			DateFormat dateFormate2 = new SimpleDateFormat(SHORT_DATE_FORMAT2_STR);
			try {
				Date date = dateFormate2.parse(shortDateStr);
				return new java.sql.Date(date.getTime());
			} catch (Exception e1) {
				e1.printStackTrace();
			}

			LOG.error("parser java.sql.Date error", e);
			return null;
		}
	}


	/**
	 * 字符串解析成 java.sql.Date 日期
	 *
	 * @author jianguo.xu
	 * @param shortDateStr
	 * @param format
	 * @return
	 */
	public static java.sql.Date parserShortDate(String shortDateStr,
			String format) {
		if (StringUtils.isBlank(shortDateStr))
			return null;
		DateFormat dateFormate = new SimpleDateFormat(format);
		try {
			Date date = dateFormate.parse(shortDateStr);
			return new java.sql.Date(date.getTime());
		} catch (ParseException e) {
			LOG.error("parser java.sql.Date error", e);
			return null;
		}
	}

	/**
	 * 字符串解析成日期
	 *
	 * @author jianguo.xu
	 * @param dateStr
	 * @param format
	 * @return
	 */
	public static Date parserDate(String dateStr, String format) {
		if (StringUtils.isBlank(dateStr))
			return null;
		DateFormat dateFormate = new SimpleDateFormat(format);
		try {
			Date date = dateFormate.parse(dateStr);
			return new Date(date.getTime());
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 根据TimeUnit增加指定日期的的时间
	 *
	 * @author jianguo.xu
	 * @param date
	 *            要增加的日期
	 * @param timeUnit
	 *            增加的日历字段（只能取 DAYS 到 MILLISECONDS 之间的枚举，否则报错）
	 * @param value
	 *            增加的值(当值为负数时表示减少)
	 * @return
	 */
	public static Date add(Date date, TimeUnit timeUnit, int value) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(calField(timeUnit), value);
		return cal.getTime();
	}

	/**
	 * 根据TimeUnit设定指定日期的的时间
	 *
	 * @author jianguo.xu
	 * @param date
	 *            要设定的日期
	 * @param timeUnit
	 *            设定的日历字段（只能取 DAYS 到 MILLISECONDS 之间的枚举，否则报错）
	 * @param value
	 *            设定的值(当值为负数时表示减少)
	 * @return
	 */
	public static Date set(Date date, TimeUnit timeUnit, int value) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(calField(timeUnit), value);
		return cal.getTime();
	}
	public static enum TimeUnit {
		YEAR, MONTH, WEEK_OF_YEAR, WEEK_OF_MONTH,DAY_OF_WEEK, DAYS,DAY_OF_MONTH, HOURS, MINUTES, SECONDS, MILLISECONDS;
	}
	private static int calField(TimeUnit timeUnit) {

		int field = 0;
		if (timeUnit == TimeUnit.YEAR)
			field = Calendar.YEAR;
		else if (timeUnit == TimeUnit.MONTH)
			field = Calendar.MONTH;
		else if (timeUnit == TimeUnit.WEEK_OF_YEAR)
			field = Calendar.WEEK_OF_YEAR;
		else if (timeUnit == TimeUnit.WEEK_OF_MONTH)
			field = Calendar.WEEK_OF_MONTH;
		else if (timeUnit == TimeUnit.DAY_OF_WEEK)
			field = Calendar.DAY_OF_WEEK;
		else if (timeUnit == TimeUnit.DAYS)
			field = Calendar.DAY_OF_YEAR;
		else if (timeUnit == TimeUnit.DAY_OF_MONTH)
			field = Calendar.DAY_OF_MONTH;
		else if (timeUnit == TimeUnit.HOURS)
			field = Calendar.HOUR_OF_DAY;
		else if (timeUnit == TimeUnit.MINUTES)
			field = Calendar.MINUTE;
		else if (timeUnit == TimeUnit.SECONDS)
			field = Calendar.SECOND;
		else if (timeUnit == TimeUnit.MILLISECONDS)
			field = Calendar.MILLISECOND;
		else
			throw new RuntimeException("timeUnit error");
		return field;
	}



	/**
	 * 根据TimeUnit得到指定日期的值
	 *
	 * @author jianguo.xu
	 * @param date
	 * @param timeUnit
	 * @return
	 */
	public static int getValue(Date date, TimeUnit timeUnit) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(calField(timeUnit));
	}
	/**
	 * 根据日期得到当前星期数<br/>
	 * 星期一为1、 星期二为2 ... 星期日为7
	 * @param date
	 * @return
	 * @author  xu.jianguo
	 */
	public static int getDayOfWeek(Date date) {
		int dayOfWeek =  DateUtils.getValue(date, TimeUnit.DAY_OF_WEEK);
		return (dayOfWeek == 1)?7:dayOfWeek-1;
	}

	/**
	 * 根据TimeUnit清除指定的日历字段
	 *
	 * @author jianguo.xu
	 * @param date
	 *            要清除的日期
	 * @param timeUnit
	 *            清除的日历字段（只能取 DAYS 到 MILLISECONDS 之间的枚举，否则报错）
	 * @return
	 */
	public static Date clear(Date date, TimeUnit timeUnit) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.clear(calField(timeUnit));
		return cal.getTime();
	}

	/**
	 * <br>
	 * 第一个日期减去第二个日期后得到的天数</br> <br>
	 * 如果减去的后的天数有不满足一整天的，则不计入天数内</br>
	 *
	 * @param date
	 *            被减日期
	 * @param other
	 *            减去的日期
	 * @return 返回减去后的天数
	 */
	public static long subtractDay(Date date, Date other) {
		return subtractSecond(date, other) / (24 * 60 * 60);
	}

	/**
	 * 两个日期相减得到相差的秒数
	 *
	 * @param date
	 * @param other
	 * @return
	 */
	public static long subtractSecond(Date date, Date other) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		long dateTimeInMillis = calendar.getTimeInMillis();
		Calendar otherCalendar = Calendar.getInstance();
		otherCalendar.setTime(other);
		long otherTimeInMillis = otherCalendar.getTimeInMillis();
		return (dateTimeInMillis - otherTimeInMillis) / (1000);
	}

	/**
	 * 字符串解析成 java.sql.Time 时间
	 *
	 * @author jianguo.xu
	 * @param timeStr
	 * @param timeFormat
	 * @return
	 */
	public static java.sql.Time parserTime(String timeStr, String timeFormat) {
		DateFormat dateFormate = new SimpleDateFormat(timeFormat);
		try {
			Date date = dateFormate.parse(timeStr);
			return new java.sql.Time(date.getTime());
		} catch (ParseException e) {
			LOG.error("parser java.sql.Time error", e);
			return null;
		}
	}



	/**
	 * 根据参数获取此日期当天早上零分整点日期对象，如：2017-08-12 00:00:00
	 * @param date
	 * @return
	 * @throws java.text.ParseException
	 */
	public static Date getEarlyInTheDay(Date date) {
        DateFormat shortFormat = new SimpleDateFormat(SHORT_DATE_FORMAT_STR);
        DateFormat longFormat = new SimpleDateFormat(LONG_DATE_FORMAT_STR);
		String dateString = shortFormat.format(date) + " " + EARLY_TIME;
		try {
			return longFormat.parse(dateString);
		} catch (ParseException e) {
			throw new RuntimeException("parser date error.", e);
		}
	}

	/**
	 * 根据参数获取此日期当天晚上23点59分59秒日期对象，如：2017-08-12 23:59:59
	 *
	 * @param date
	 * @return
	 * @throws java.text.ParseException
	 */
	public static Date getLateInTheDay(Date date) {
        DateFormat shortFormat = new SimpleDateFormat(SHORT_DATE_FORMAT_STR);
        DateFormat longFormat = new SimpleDateFormat(LONG_DATE_FORMAT_STR);
		String dateString = shortFormat.format(date) + " " + LATE_TIME;
		try {
			return longFormat.parse(dateString);
		} catch (ParseException e) {
			throw new RuntimeException("parser date error.", e);
		}
	}

	/**
	 * 根据年龄计算出生日
	 *
	 * @author jianguo.xu
	 * @param age
	 * @return
	 */
	public static java.sql.Date getBirthday(int age) {
		Date date = new Date();
		date = add(date, TimeUnit.YEAR, -age);
		return new java.sql.Date(date.getTime());
	}

	/**
	 * 得到当前日期的毫秒数
	 *
	 * @return
	 */
	public static long getNowTime() {
		return new Date().getTime();
	}
	/**
	 * 得到指定日期所在周的最早日期(星期一)
	 * @param date
	 * @return
	 */
	public static Date getEarlyIntheWeek(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
		return getEarlyInTheDay(cal.getTime());
	}

	/**
	 * 得到指定日期所在周的最晚日期(星期日)
	 * @param date
	 * @return
	 */
	public static Date getLateInTheWeek(Date date) {
		return getLateInTheDay(add(getEarlyIntheWeek(date), TimeUnit.DAYS, 6));
	}
	/**
	 * 得到指定日期所在月的最早日期(1号)
	 * @param date
	 * @return
	 */
	public static Date getEarlyIntheMonth(Date date) {
		return getEarlyInTheDay(DateUtils.set(date, TimeUnit.DAY_OF_MONTH, 1));
	}

	/**
	 * 得到指定日期所在月的最晚日期
	 * @param date
	 * @return
	 */
	public static Date getLateInTheMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		cal.set(Calendar.DAY_OF_MONTH, maxDay);
		return getLateInTheDay(cal.getTime());
	}

	/**
	 * 得到指定日期所在年的最早日期(1月1号)
	 * @param date
	 * @return
	 */
	public static Date getEarlyInTheYear(Date date) {
		Date result = DateUtils.getEarlyInTheDay(new DateTime(date).withDayOfYear(1).toDate());
		return result;
	}

	/**
	 * 得到指定日期所在年的最晚日期(12月31号)
	 * @param date
	 * @return
	 */
	public static Date getLateInTheYear(Date date) {
		Date result = DateUtils.getLateInTheDay(new DateTime(date).withDayOfYear(1).plusYears(1).minusDays(1).toDate());
		return result;
	}

    /**
     * 获取当前时间的前几天或者后几天的时间
     * @param days -表示前几天，+表示后几天,,,负数表示前几天,,,正数表示后几天
     * @return
     * @author ranjin 2014年12月3日
     * @throws java.text.ParseException
     */
    public static Date getNextDay(int days) throws ParseException{
        
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, days);    //-表示前几天，+表示后几天
        Date date = calendar.getTime();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.parse(df.format(date));
        
    }
    
    /**
     * 判断今天 昨天 前天
     *
     * @param createTime
     * @return
     * @author Administrator 2016年6月29日
     */
    public static String convertDate(Date createTime){    
        try {
            String ret = "";
            long create = createTime.getTime();
            Calendar now = Calendar.getInstance();
            long ms  = 1000*(now.get(Calendar.HOUR_OF_DAY)*3600+now.get(Calendar.MINUTE)*60+now.get(Calendar.SECOND));//毫秒数
            long msNow = now.getTimeInMillis();
            if(msNow-create<ms){
                ret = format(createTime,"HH:mm");
            }else if(msNow-create<(ms+24*3600*1000)){
                ret = "昨天";
            }else if(msNow-create<(ms+24*3600*1000*2)){
                ret = "前天";
            }else{
                ret = format(createTime,"MM-dd");
            }
            return ret;
        } catch (Exception e) {
                e.printStackTrace();
        }
        return null;
    }




	//获取上周的开始时间
	@SuppressWarnings("unused")
	public static Date getBeginDayOfLastWeek() {
		Date date = new Date();
		if (date == null) {
			return null;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int dayofweek = cal.get(Calendar.DAY_OF_WEEK);
		if (dayofweek == 1) {
			dayofweek += 7;
		}
		cal.add(Calendar.DATE, 2 - dayofweek - 7);
		return getDayStartTime(cal.getTime());
	}
	//获取上周的结束时间
	public static Date getEndDayOfLastWeek(){
		Calendar cal = Calendar.getInstance();
		cal.setTime(getBeginDayOfLastWeek());
		cal.add(Calendar.DAY_OF_WEEK, 6);
		Date weekEndSta = cal.getTime();
		return getDayEndTime(weekEndSta);
	}
	//获取本月的开始时间
	public static Date getBeginDayOfMonth() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(getNowYearNum(), getNowMonth() - 1, 1);
		return getDayStartTime(calendar.getTime());
	}
	//获取本月的结束时间
	public static Date getEndDayOfMonth() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(getNowYearNum(), getNowMonth() - 1, 1);
		int day = calendar.getActualMaximum(5);
		calendar.set(getNowYearNum(), getNowMonth() - 1, day);
		return getDayEndTime(calendar.getTime());
	}
	//获取上月的开始时间
	public static Date getBeginDayOfLastMonth() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(getNowYearNum(), getNowMonth() - 2, 1);
		return getDayStartTime(calendar.getTime());
	}

	//获取本年的开始时间
	public static java.util.Date getBeginDayOfYear() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, getNowYearNum());
		// cal.set
		cal.set(Calendar.MONTH, Calendar.JANUARY);
		cal.set(Calendar.DATE, 1);

		return getDayStartTime(cal.getTime());
	}
	//获取本年的结束时间
	public static java.util.Date getEndDayOfYear() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, getNowYearNum());
		cal.set(Calendar.MONTH, Calendar.DECEMBER);
		cal.set(Calendar.DATE, 31);
		return getDayEndTime(cal.getTime());
	}



	//获取某个日期的开始时间
	public static Timestamp getDayStartTime(Date d) {
		Calendar calendar = Calendar.getInstance();
		if(null != d) calendar.setTime(d);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),    calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return new Timestamp(calendar.getTimeInMillis());
	}


	//获取某个日期的结束时间
	public static Timestamp getDayEndTime(Date d) {
		Calendar calendar = Calendar.getInstance();
		if(null != d) calendar.setTime(d);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),    calendar.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		return new Timestamp(calendar.getTimeInMillis());
	}

	//获取今年是哪一年
	public static Integer getNowYearNum() {
		Date date = new Date();
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		return Integer.valueOf(gc.get(1));
	}
	//获取本月是哪一月
	public static int getNowMonth() {
		Date date = new Date();
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		return gc.get(2) + 1;
	}


	/**
	 * 计算两个时间相差多少个年
	 * @param start
	 * @param end
	 * @return
	 * @throws ParseException
	 */
	public static int yearsBetween(Date start, Date end){
		String formatPeriod = DurationFormatUtils.formatPeriod(start.getTime(), end.getTime(), "yyyy-MM-dd");
		String[] val = formatPeriod.split("-");
		return Integer.parseInt(val[0]);
	}

}
package com.ajian.util.UtilLibrary.date;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import com.ajian.util.UtilLibrary.string.StringUtils;

/**
 * 提供一些日期转换的功能
 */
public class DateUtils {

	/**
	 * @param val
	 *            String date formatted string This Date class provides a string
	 *            wrapper for all three sql types: java.sql.Date, java.sql.Time
	 *            and java.sql.Timestamp <br>
	 *            Three types of string formats are allowed:
	 *            <li>year-month-day "2001-05-20"
	 *            <li>hour:min:sec "01:01:01"
	 *            <li>year-month-day hour:min:sec "2001-05-20 01:01:01" <br>
	 *            If "2001:05:20 01:01:01" is passed in, year, month and day = 0
	 *            <br>
	 *            Date will be set to current time if val = ""
	 */
	public static java.util.Date getDateTime(java.lang.String val) {

		java.util.Date date = null;

		if ((val.equals("")) || (val.equals("0"))) {
			date = new java.sql.Date(new java.util.Date().getTime());
			return date;
		}

		GregorianCalendar cal = new GregorianCalendar();
		java.lang.String[] ts = StringUtils.split(val, " ");
		java.lang.String[] ymd, hms;
		int year = 0, month = 0, day = 0, hour = 0, min = 0, sec = 0;

		// parse "2001-05-20"
		if (ts[0].indexOf("-") > -1) {
			ymd = StringUtils.split(ts[0], "-");
			year = Integer.parseInt(ymd[0]);
			month = Integer.parseInt(ymd[1]) - 1;
			day = Integer.parseInt(ymd[2]);
		}
		// parse "01:01:01"
		else if (ts[0].indexOf(":") > -1) {
			hms = StringUtils.split(ts[0], ":");
			hour = Integer.parseInt(hms[0]);
			min = Integer.parseInt(hms[1]);
			sec = Integer.parseInt(hms[2]);
		}

		// parse "01:01:01" in second array element
		if (ts.length > 1) {
			hms = StringUtils.split(ts[1], ":");
			hour = Integer.parseInt(hms[0]);
			min = Integer.parseInt(hms[1]);
			sec = Integer.parseInt(hms[2]);
		}

		cal.set(year, month, day);
		cal.set(Calendar.HOUR_OF_DAY, hour);
		cal.set(Calendar.MINUTE, min);
		cal.set(Calendar.SECOND, sec);

		// cannot get millis from calendar in JDK1.3 so first we get a
		// java.util.Date
		date = cal.getTime();
		// date = new java.sql.Date( utilDate.getTime() );
		return date;
	}

	/**
	 * 将日期格式化成"yyyy年M月d日"的形式，如将2000-01-01转换为2000年1月1日
	 * 
	 * @param date
	 * @return
	 */
	public static String getChineseDate(java.util.Date date) {
		DateFormat df = DateFormat.getDateInstance(DateFormat.LONG,
				Locale.CHINESE);
		String strDate = df.format(date);
		return strDate;
	}

	/**
	 * 将日期格式化成"yyyy-MM-dd HH:mm:ss"的形式，如"2000-12-3 12:53:48"
	 * 
	 * @param date
	 * @return
	 */
	public static String getLongTime(java.util.Date date) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// DateFormat.getDateTimeInstance(DateFormat.LONG,
																	// DateFormat.LONG,
																	// Locale.CHINESE);
		String strDate = df.format(date);
		return strDate;
	}

	/**
	 * 将日期格式化成"yyyy-MM-dd HH:mm"的形式，如"2000-12-3 12:53"
	 * 
	 * @param date
	 * @return
	 */
	public static String getLongFormatTime(java.util.Date date) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");// DateFormat.getDateTimeInstance(DateFormat.LONG,
																	// DateFormat.LONG,
																	// Locale.CHINESE);
		String strDate = df.format(date);
		return strDate;
	}

	/**
	 * 将日期格式化成"yyyy-MM-dd"的形式，如"2000-12-3"
	 * 
	 * @param date
	 * @return
	 */
	public static String getLongDate(java.util.Date date) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");// DateFormat.getDateTimeInstance(DateFormat.LONG,
															// DateFormat.LONG,
															// Locale.CHINESE);
		String strDate = df.format(date);
		return strDate;
	}

	public static java.util.Date getDate(java.lang.String val) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date date = null;
		java.sql.Date sdate = null;
		try {
			date = df.parse(val);
			sdate = new java.sql.Date(date.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return sdate;
	}

	/**
	 * 将毫秒数换算成x天x时x分x秒x毫秒
	 * @param ms
	 * @return
	 * @author Ajian
	 * @version Mar 4, 2013 12:46:04 PM
	 */
	public static String getRemainTimeReturnXDXHXM(long ms) {// 将毫秒数换算成x天x时x分x秒x毫秒
		int ss = 1000;
		int mi = ss * 60;
		int hh = mi * 60;
		int dd = hh * 24;

		long day = ms / dd;
		long hour = (ms - day * dd) / hh;
		long minute = (ms - day * dd - hour * hh) / mi;
		long second = (ms - day * dd - hour * hh - minute * mi) / ss;
		long milliSecond = ms - day * dd - hour * hh - minute * mi - second
				* ss;

		String strDay = day < 10 ? "0" + day : "" + day;
		String strHour = hour < 10 ? "0" + hour : "" + hour;
		String strMinute = minute < 10 ? "0" + minute : "" + minute;
		String strSecond = second < 10 ? "0" + second : "" + second;
		String strMilliSecond = milliSecond < 10 ? "0" + milliSecond : "" + milliSecond;
		strMilliSecond = milliSecond < 100 ? "0" + strMilliSecond : ""
				+ strMilliSecond;
		return strDay + "天" + strHour + ":" + strMinute + ":" + strSecond + ":" + strMilliSecond;
	}
	
	/**
	 * 根据输入时间计算与现在的时间差，返回剩余时间的字符串表示
	 * @param time
	 * @return
	 * @author Ajian
	 * @version Mar 4, 2013 10:50:28 AM
	 */
	public static String getRemainTime(Date time){
		int hour;
		long targetTime = time.getTime();
		long now = System.currentTimeMillis();
		if(targetTime<=now){
			return "无".intern();
		}else if((hour=((int)(targetTime-now)/(60*60*1000)))>24){
			return hour/24+"天".intern();
		}else if(hour>=1){
			return hour+"小时".intern();
		}else{
			return (targetTime-now)/(60*1000)+"分钟".intern();
		}
	}
	
	/**
	 * 获取几天之前几小时之前..
	 * @return
	 * @author Ajian
	 * @version Mar 4, 2013 2:28:09 PM
	 */
	public static String getBeforeTimeToString(Date time){
		int hour=0,minute=0;
		long target=time.getTime(),now=System.currentTimeMillis();
		if(target>now)return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time);
		else if((hour=(int)((now-target)/(1000*60*60)))>=24){
			if(hour/24>=5)return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time);
			return hour/24+"天前".intern();
		}
		else if(hour>=1)return hour+"小时前".intern();
		else if((minute=((int)(now-target)/(1000*60)))>=1)return minute+"分钟前".intern();
		else return "刚刚".intern(); 
	}
	
	public static void main(String[] args) throws ParseException {
		long l = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2012-9-14 9:41:38").getTime() - new Date().getTime();
		System.out.println(getRemainTimeReturnXDXHXM(l));
	}
}

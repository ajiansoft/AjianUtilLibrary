package com.ajian.util.UtilLibrary.date;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * 时间操作器
 */
public class TimeFormator {

	/**
	 * 时间转换，把time转换成何时间相关的数据
	 * 
	 * @param time
	 * @return
	 */
	public static String timeSwitch(Long time, String stime) {
		time = time / 60000;// 60000=1min
		if (time / 60 == 0) {
			return String.valueOf(time) + "分钟前 ";
		} else {
			String msg = "";
			if (time / (60 * 24) == 0 && stime.contains(getToday())) {
				msg = "今天  " + stime.substring(10, 16);
			} else {
				msg = stime.substring(0, 10);
			}
			return msg;
		}
	}

	/**
	 * 获取两个时间之间的时间差
	 * 
	 * @param start
	 *            yyyy-MM-dd HH:mm:ss
	 * @param end
	 *            yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String getIntervalOfTwoTime(String start, String end) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Long time = null;
		try {
			Date start_time = sdf.parse(start);
			Date end_time = sdf.parse(end);
			time = (end_time.getTime() - start_time.getTime());
			return timeSwitch(time, start);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获取两个时间之间的时间差
	 * @return Calendar，通过Calendar.get获取两个时间差相差多少天多少小时多少分钟...
	 */
	public static Calendar getIntervalOfTwoTime(Date start, Date end) {
		long time = (end.getTime() - start.getTime());
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MONTH,(int)(time/(1000*60*60*24)));
		cal.set(Calendar.HOUR,(int)((time%(1000*60*60*24))/(1000*60*60)));
		cal.set(Calendar.MINUTE,(int)((time%(1000*60*60*24)%(1000*60*60))/(1000*60)));
		return cal;
	}
	public static void main(String[] args) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar cal = getIntervalOfTwoTime(sdf.parse("2012-10-10 00:00:00"),sdf.parse("2012-10-10 00:01:00"));
		System.out.println(cal.get(Calendar.MONTH)+","+cal.get(Calendar.HOUR)+","+cal.get(Calendar.MINUTE));
		
		System.out.println(getIntervalOfNowToTime(sdf.parse("2013-4-15 13:53:00")));
		
		System.out.println(sdf.format(getBeforeTimeByHour(16)));
	}
	/**
	 * 获取time和今天时间之间的时间间隔
	 * 
	 * @param time
	 *            yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String getIntervalOfNowToTime(String time) {
		return getIntervalOfTwoTime(time, getNow());
	}
	
	/**
	 * 获取两个时间之差，返回分钟数表示.
	 * @param time
	 * @return
	 * @author Ajian
	 * @version Apr 17, 2013 3:53:37 PM
	 */
	public static long getIntervalOfNowToTime_returnMinute(long time) {
		long now = System.currentTimeMillis();
		if(now<=time)return 0;
		return (now-time)/1000/60;
	}

	/**
	 * 计算从Timestamp到现在的时间差
	 * 
	 * @param time
	 * @return
	 */
	public static String getIntervalOfNowToTime(Timestamp time) {
		SimpleDateFormat m_format = new SimpleDateFormat("yyyy-MM-dd,HH:mm:ss");
		try {
			long start = m_format.parse(m_format.format(time)).getTime();
			long end = new Date().getTime();
			return timeSwitch(end - start, m_format.format(time));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 计算从Timestamp到现在的时间差
	 * @param time
	 * @return 返回距离现在的小时数，不足一小时按0算。
	 * @author Ajian
	 * @version Apr 15, 2013 3:42:41 PM
	 */
	public static int getIntervalOfNowToTime(Date time) {
		long start = time.getTime();
		long end = new Date().getTime();
		if(end<=start)return 0;
		return (int) ((end - start) /3600000);
	}
	
	

	/**
	 * @return eg:2011-03-14
	 */
	public static Date getNowDate() {
		return new java.sql.Date(new java.util.Date().getTime());
	}

	/**
	 * @return 2011-03-14 16:26:33
	 */
	public static Timestamp getNowDatetime() {
		return new Timestamp(new java.util.Date().getTime());
	}

	/**
	 * 获取今天时间 格式YY-MM-DD eg:2010-04-19
	 * 
	 * @return
	 */
	public static String getToday() {
		String str = "";
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
		str = dateformat.format(new Date());
		return str;
	}

	/**
	 * 获取今天时间 格式YY-MM-DD eg:2010-04-19
	 * 
	 * @return
	 */
	public String getYesterday() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		return new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
	}

	/**
	 * 获取现在时间 格式： yy-mm-dd HH:mm:ss eg:2010-04-19 22:42:13
	 * 
	 * @return
	 */
	public static String getNow() {
		String str = "";
		Calendar c = Calendar.getInstance();
		SimpleDateFormat dateformat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		str = dateformat.format(c.getTime());
		return str;
	}

	/**
	 * 获取现在时间 格式： yy-mm-dd HH:mm:ss eg:2010-04-19 22:42:13.0
	 * 
	 * @return TIMESTAMP
	 */
	public static Timestamp getNowTimestamp() {
		String str = "";
		SimpleDateFormat dateformat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		str = dateformat.format(new Date());
		return fromStringToTimestamp(str);
	}

	// dat格式为："1900-02-21 12:23:33";
	public Date fromStrToDatetime(String dat) {
		SimpleDateFormat dateformat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = dateformat.parse(dat);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	// dat格式为："1900-02-21";
	public static Date fromStrToDate(String dat) {
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = dateformat.parse(dat);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	public static Date fromStrToDateAndTime(String dat) {
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = dateformat.parse(dat);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 获取现在的时间 格式：xx年xx月xx日 xx:xx:ss eg:2010年4月19日 22:42:13
	 * 
	 * @return
	 */
	public static String getNowWithChin() {
		Date now = new Date();
		DateFormat d6 = DateFormat.getDateTimeInstance(DateFormat.LONG,
				DateFormat.MEDIUM);
		String str6 = d6.format(now);
		return str6;
	}

	/**
	 * eg:2010年4月19日
	 * 
	 * @return
	 */
	public static String getTodayWithChin() {
		return getNowWithChin().substring(0, 10);
	}

	/**
	 * String 转化为 timestamp
	 * 
	 * @param time
	 * @return
	 */
	public static Timestamp fromStringToTimestamp(String time) {
		Timestamp ts = Timestamp.valueOf(time);
		return ts;
	}

	/**
	 * 获取间隔今天N天的日期 负数为过去
	 * 
	 * @param n
	 * @return
	 */
	public String getTheDay(int n) {
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
		long day = 0;
		long temp = 0;
		String days = "";
		try {
			Date date = new Date();
			day = date.getTime();
			temp = 24 * 60 * 60 * 1000 * (n);
			days = myFormatter.format(day + temp);
		} catch (Exception e) {
			return "";
		}
		return days;
	}

	/**
	 * 获取间隔今天N天的日期 负数为过去
	 * 
	 * @param n
	 * @return YYYY-MM-DD HH:mm:ss
	 */
	public String getTheDayFromADay(int n, String time) {

		String times = "";
		String temp = this.getTheDate(n);
		temp = temp + " 23:59:59";
		return times;

	}

	/**
	 * 得到二个日期间的间隔天数
	 */
	public static String getTwoDay(String sj1, String sj2) {
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
		long day = 0;
		try {
			java.util.Date date = myFormatter.parse(sj1);
			java.util.Date mydate = myFormatter.parse(sj2);
			day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
		} catch (Exception e) {
			return "";
		}
		return day + "";
	}

	// 获取当天时间
	public String getNowTime(String dateformat) {
		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat(dateformat);// 可以方便地修改日期格式
		String hehe = dateFormat.format(now);
		return hehe;
	}

	/**
	 * 获取上一月月
	 * 
	 * @return
	 */
	public static String getTheDateForMonth() {
		Calendar cd = Calendar.getInstance();
		int day = cd.get(Calendar.DATE);
		int month = cd.get(Calendar.MONTH);
		int year = cd.get(Calendar.YEAR);

		return year + "-" + month + "-" + day;
	}

	/**
	 * 获取上月的第一天
	 * 
	 * @return
	 */
	public String getPreviousMonthFirst() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar lastDate = Calendar.getInstance();
		lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
		lastDate.add(Calendar.MONTH, -1);// 减一个月，变为下月的1号
		str = sdf.format(lastDate.getTime());
		return str;
	}

	// 获得当前日期与本周日相差的天数
	private static int getMondayPlus() {
		Calendar cd = Calendar.getInstance();
		// 获得今天是一周的第几天，星期日是第一天，星期二是第二天......
		int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK) - 1; // 因为按中国礼拜一作为第一天所以这里减1
		if (dayOfWeek == 1) {
			return 0;
		} else {
			return 1 - dayOfWeek;
		}
	}

	// 获得下个月最后一天的日期
	public String getNextMonthEnd() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.MONTH, 1);// 加一个月
		lastDate.set(Calendar.DATE, 1);// 把日期设置为当月第一天
		lastDate.roll(Calendar.DATE, -1);// 日期回滚一天，也就是本月最后一天
		str = sdf.format(lastDate.getTime());
		return str;
	}

	/**
	 * 获取长时间计算 返回和今天相差多少天的日期
	 * 
	 * @param time
	 * @param n
	 * @return yyyy-mm-dd
	 */
	@SuppressWarnings("static-access")
	public String getTheDate(int n) {

		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar date = Calendar.getInstance();

		date.roll(date.DATE, n);
		str = sdf.format(date.getTime());
		return str;

	}

	/**
	 * 返回和今天相差月数日期
	 * 
	 * @param n
	 * @return
	 */
	@SuppressWarnings("static-access")
	public String getTheMonthDay(int n) {

		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar date = Calendar.getInstance();
		Date now = date.getTime();
		long now1 = now.getTime();
		int k = (n / 12) + 1;
		date.roll(date.MONTH, n);

		if (n > 0 && (date.getTime().getTime() < now1))
			date.roll(date.YEAR, k);
		str = sdf.format(date.getTime());
		return str;

	}

	/**
	 * 比较时间是否大于今天
	 * 
	 * @param time
	 *            yyyy-MM-dd
	 * @return
	 */
	public boolean TimeCompareToday(String time) {

		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");

		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		try {
			c2.setTime(dateformat.parse(time));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (c2.after(c1)) {
			return true;
		} else
			return false;
	}

	/**
	 * 获取本月天数
	 * 
	 * @return
	 */
	public static int getNowMonthDays() {
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;

		return getDaysInTheMonth(year, month);
	}

	/**
	 * 获取当前周一的日期
	 * 
	 * @return
	 */
	public static String getWeekday() {

		int mondayPlus = getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus);
		Date monday = currentDate.getTime();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String preMonday = df.format(monday);
		return preMonday;
	}

	/**
	 * 获取当前周一的号数
	 * 
	 * @return
	 */
	public static int getMondayInMonth() {
		String k = getWeekday();
		String t = k.substring(8);
		int m = Integer.parseInt(t);
		return m;
	}

	/**
	 * 获取本月第一天的星期
	 * 
	 * @return
	 */
	public static int getMonthFirstWeek() {

		int temp = 0;

		Calendar date = Calendar.getInstance();
		date.set(Calendar.DATE, 1);// 把日期设置为当月第一天
		temp = date.get(Calendar.DAY_OF_WEEK);

		if (temp - 1 == 0) // 由于星期制度不同，因此设置
			temp = 7;
		else
			temp = temp - 1;

		return temp;
	}

	/**
	 * 获取指定月的天数
	 * 
	 * @param year
	 *            年 ：2010
	 * @param month
	 *            月：2
	 * @return
	 */
	public static int getDaysInTheMonth(int year, int month) {
		int days = 0;
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month - 1);
		days = cal.getActualMaximum(Calendar.DATE);
		return days;
	}

	public static int compareDate(String DATE1, String DATE2) {

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		try {
			Date dt1 = df.parse(DATE1);
			Date dt2 = df.parse(DATE2);
			if (dt1.getTime() > dt2.getTime()) {
				//System.out.println("dt1 在dt2前");
				return 1;
			} else if (dt1.getTime() < dt2.getTime()) {
				//System.out.println("dt1在dt2后");
				return -1;
			} else {
				return 0;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return 0;
	}

	/**
	 * 判断当前时间是否是本周最后一天
	 */
	public static boolean isLastDayOfWeek() {
		Calendar calendar = Calendar.getInstance();
		int current = calendar.get(Calendar.DAY_OF_WEEK);// 获取当天周内天数,1:星期天，2：星期一...
		if (current == 1) {
			return true;
		}
		return false;
	}

	/**
	 * 判断当前时间是否是本月最后一天
	 */
	@SuppressWarnings("deprecation")
	public static boolean isLastDayOfMonth() {
		Date now = new Date();
		Calendar cDay1 = Calendar.getInstance();
		cDay1.setTime(now);
		final int lastDay = cDay1.getActualMaximum(Calendar.DAY_OF_MONTH);
		Date lastDate = cDay1.getTime();
		lastDate.setDate(lastDay);
		System.out.println(now.getDate());
		if (now.getDate() == lastDate.getDate()) {
			return true;
		}
		return false;
	}

	/**
	 * 获取当前日期是星期几<br>
	 * <br>
	 * 
	 * @return dayForWeek 判断结果<br>
	 * @Exception 发生异常
	 *                <br>
	 */
	public static int dayForWeek() {

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(format.parse(format.format(new Date())));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		int dayForWeek = 0;
		if (c.get(Calendar.DAY_OF_WEEK) == 1) {
			dayForWeek = 7;
		} else {
			dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
		}

		return dayForWeek;
	}

	/**
	 * 得到当前日期几小时后的时间
	 * @param hhmmss
	 * @return
	 * @author Ajian
	 * @version Sep 14, 2012 4:07:22 PM
	 */
	public static Date getEndTimeByHour(int hours, int minutes, int second) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.HOUR_OF_DAY, Math.abs(hours));
		cal.add(Calendar.MINUTE, Math.abs(minutes));
		cal.add(Calendar.SECOND, Math.abs(second));
		// System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cal.getTime()));
		return cal.getTime();
	}
	
	/**
	 * 得到当前日期几天几小时后的时间
	 * @param hhmmss
	 * @return
	 * @author Ajian
	 * @version Sep 14, 2012 4:07:22 PM
	 */
	public static Date getEndTimeByDay(int day,int hours, int minutes) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DAY_OF_MONTH, Math.abs(day));
		cal.add(Calendar.HOUR_OF_DAY, Math.abs(hours));
		cal.add(Calendar.MINUTE, Math.abs(minutes));
		// System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cal.getTime()));
		return cal.getTime();
	}
	/**
	 * 得到当前日期几天几小时后的时间
	 * @param hhmmss
	 * @return
	 * @author Ajian
	 * @version Sep 14, 2012 4:07:22 PM
	 */
	public static Date getEndTimeByDay(int day,int hours) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DAY_OF_MONTH, Math.abs(day));
		cal.add(Calendar.HOUR_OF_DAY, Math.abs(hours));
		// System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cal.getTime()));
		return cal.getTime();
	}
	
	/**
	 * 获取当前时间几小时之前的时间。
	 * @param hours
	 * @return
	 * @author Ajian
	 * @version Apr 15, 2013 4:13:08 PM
	 */
	public static Date getBeforeTimeByHour(int hours){
		Calendar cal = Calendar.getInstance(Locale.CHINA);
		cal.set(Calendar.HOUR_OF_DAY, cal.get(Calendar.HOUR_OF_DAY)-hours);
		return cal.getTime();
	}
	/**
	 * 获取指定时间几小时之前的时间。
	 * @param hours
	 * @return
	 * @author Ajian
	 * @version Apr 15, 2013 4:13:08 PM
	 */
	public static Date getBeforeTimeByHour(Date time,int hours){
		Calendar cal = Calendar.getInstance(Locale.CHINA);
		cal.setTime(time);
		cal.set(Calendar.HOUR_OF_DAY, cal.get(Calendar.HOUR_OF_DAY)-hours);
		return cal.getTime();
	}
	
	/**
	 * 获得明天的这个时候的时间
	 * @param time
	 * @param date
	 * @return
	 * @author zhangxin
	 * @since 2012.09.25
	 */
	@SuppressWarnings("static-access")
	public static String tomorrowTime(String time,Date date){
		DateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar cd=Calendar.getInstance();
		 cd.setTime(date);	
         cd.add(cd.DATE, 1);
		return df.format(cd.getTime());
	}
	
	/**
	 * 获取当前年份-短
	 * @return String
	 * 		-当前年份
	 */
	public static int getCurrentYear() {
		Calendar c = Calendar.getInstance();
		Integer year = c.get(Calendar.YEAR);
		return year;
	}
	/**
	 * 获取当前号数
	 * 
	 * @return
	 */
	public static int getTheDateToday() {
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.DATE);
	}
	/**
	 * 获取当前月
	 * 
	 * @return
	 */
	public static int getTheMonthToday() {
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.MONTH) + 1;
	}
	
	/**
	 * 根据日期获得年龄
	 * @param birthday
	 * @return
	 * @author zhangxin
	 * @since 2012.11.27
	 */
	public static int getAge(Date birthDay){
        Calendar cal = Calendar.getInstance();

        if (cal.before(birthDay)) {
            throw new IllegalArgumentException(
                "The birthDay is before Now.It's unbelievable!");
        }

        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH);
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
        cal.setTime(birthDay);

        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

        int age = yearNow - yearBirth;

        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth) {
                    age--;
                } else {
                }
            } else {
                age--;
            }
        } else {
        }
        return age;
    }
	
	/**
	 * 根据传入日期获取号数
	 * @param date
	 * @return
	 */
	public static int getDays(Date date){
		Calendar cd=Calendar.getInstance();
		cd.setTime(date);
		return cd.get(Calendar.DATE);
	}
	
	/**
	 * 根据传入日期获取月份
	 * @param date
	 * @return
	 */
	public static int getMonth(Date date){
		Calendar cd=Calendar.getInstance();
		cd.setTime(date);
		return cd.get(Calendar.MONTH)+1;
	}

	/**
	 * 将String日期转换为date
	 * @param dateString
	 * @return
	 */
	public static Date changeDate(String dateString){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = sdf.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	private static SimpleDateFormat sdf_YYYYMMDDHHMMDD = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static Date formatDateByYYYYMMDDHHMMSS(Date date){
		try {
			return sdf_YYYYMMDDHHMMDD.parse(sdf_YYYYMMDDHHMMDD.format(date));
		} catch (ParseException e) {
			return date;
		}
	}
	
	public static String formatDateByYYYYMMDDHHMMSS_returnString(Date date){
		return sdf_YYYYMMDDHHMMDD.format(date);
	}
}

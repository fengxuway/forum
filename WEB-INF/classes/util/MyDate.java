package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class MyDate {
	private static SimpleDateFormat sdf; 
	static{
		sdf = new SimpleDateFormat();
	} 
	
	/**
	 * 获取当前时间对象
	 */
	public static Date get(){
		return get(System.currentTimeMillis());
	}
	
	/**
	 * 获取指定毫秒数的Date
	 * @param timeMillis 毫秒数
	 */
	public static Date get(long timeMillis){
		return new Date(timeMillis);
	}
	
	/**
	 * 获取指定年/月/日的Date对象
	 * @param year 年
	 * @param month 月
	 * @param date 日
	 */
	public static Date get(int year, int month, int date){
		return get(year, month, date, 0,0,0);
	}
	
	/**
	 * 获取指定年/月/日  时:分:秒的Date对象
	 * @param year 年
	 * @param month 月
	 * @param date 日
	 * @param hrs 小时
	 * @param min 分钟
	 */
	public static Date get(int year, int month, int date, int hrs, int min){
		return get(year, month, date, hrs, min,0);
	}
	
	/**
	 * 获取指定年/月/日  时:分:秒的Date对象
	 * @param year 年
	 * @param month 月
	 * @param date 日
	 * @param hrs 小时
	 * @param min 分钟
	 * @param sec 秒
	 */
	public static Date get(int year, int month, int date, int hrs, int min, int sec){
		return new GregorianCalendar(year, month-1, date, hrs, min, sec).getTime();
	}
	
	/*public static long getMillis(int year, int month, int date, int hrs, int min, int sec){
		
	}*/
	
	/**
	 * 将指定字符串解析为Date对象<br>
	 * 以yyyy-MM-dd格式解析 
	 * @param time 表示时间的字符串.如:"2012-10-10"
	 */
	public static Date get(String time){
		return get(time, "yyyy-MM-dd");
	}
	
	/**
	 * 将指定字符串解析为指定格式的Date对象<br>
	 * @param time 表示时间的字符串.如:"2012-10-10"
	 * @param pattern 解析格式
	 */
	public static Date get(String time, String pattern){
		sdf.applyPattern(pattern);
		try {
			return sdf.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 将传入的Date对象转换为yyyy-MM-dd格式的字符串
	 * @param pattern 指定格式
	 * @return 字符串
	 */
	public static String format(Date date){
		return format(date, "yyyy-MM-dd");
	}
	
	/**
	 * 将Date对象转换为指定格式的字符串
	 * @param pattern 指定格式
	 * @return 字符串
	 */
	public static String format(Date date, String pattern){
		sdf.applyPattern(pattern);
		return sdf.format(date);
	}
	
	
	
///////////////////////////////////////////////////////////////////////////////////////////
	//Date date =null;
	Calendar calendar = new GregorianCalendar();
	
	/**
	 * 以当前时间创建MyDate对象
	 */
	public MyDate(){
		this(new Date());
	}
	
	/**
	 * 以指定Date对象创建MyDate对象
	 * @param date
	 */
	public MyDate(Date date){
		//this.date = date;
		calendar.setTime(date);
	}
	
	/**
	 * 设置新的Date对象
	 * @param date
	 */
	public void setTime(Date date){
		calendar.clear();	
		calendar.setTime(date);
	}
	
	/**
	 * 获取年YEAR
	 * @return int
	 */
	public int getYear(){
		return calendar.get(Calendar.YEAR);
	}
	/**
	 * 获取月MONTH
	 * @return int
	 */
	public int getMonth(){
		return calendar.get(Calendar.MONTH)+1;
	}
	/**
	 * 获取日DATE
	 * @return int
	 */
	public int getDate(){
		return calendar.get(Calendar.DATE);
	}
	/**
	 * 获取小时(24小时制)HOUR_OF_Day
	 * @return int
	 */
	public int getHour(){
		return calendar.get(Calendar.HOUR_OF_DAY);
	}
	/**
	 * 获取分MINUTE
	 * @return int
	 */
	public int getMinute(){
		return calendar.get(Calendar.MINUTE);
	}
	/**
	 * 获取秒SECOND
	 * @return int
	 */
	public int getSecond(){
		return calendar.get(Calendar.SECOND);
	}

	/**
	 * 更改年
	 * @param year
	 * @return this
	 */
	public MyDate setYear(int year){
		calendar.set(Calendar.YEAR, year);
		return this;
	}
	/**
	 * 更改月
	 * @param month
	 * @return this
	 */
	public MyDate setMonth(int month){
		calendar.set(Calendar.MONTH, month - 1);
		return this;
	}
	/**
	 * 更改日
	 * @param date
	 * @return this
	 */
	public MyDate setDate(int date){
		calendar.set(Calendar.DATE, date);
		return this;
	}
	/**
	 * 更改小时
	 * @param hour
	 * @return this
	 */
	public MyDate setHour(int hour){
		calendar.set(Calendar.HOUR_OF_DAY, hour);
		return this;
	}
	/**
	 * 更改分钟
	 * @param minute
	 * @return this
	 */
	public MyDate setMinute(int minute){
		calendar.set(Calendar.MINUTE, minute);
		return this;
	}
	/**
	 * 更改秒
	 * @param second
	 * @return this
	 */
	public MyDate setSecond(int second){
		calendar.set(Calendar.SECOND, second);
		return this;
	}
	
	/**
	 * 获取MyDate对象的Date对象
	 * @return
	 */
	public Date getTime(){
		return calendar.getTime();
	}
	
	/**
	 * 获取MyDate当前毫秒数
	 * @return 
	 */
	public long getTimeInMillis(){
		return calendar.getTimeInMillis();
	}
	
	
	public static void main(String[] args) throws Throwable {
		/*MyDate date = new MyDate();
		System.out.println(date.getYear());
		System.out.println(date.getMonth());
		System.out.println(date.getDate());
		System.out.println(date.getHour());
		System.out.println(date.getMinute());
		System.out.println(date.getSecond());
		System.out.println(System.currentTimeMillis());
		date.setMonth(3);
		System.out.println(date.getMonth());
		System.out.println(date.getTimeInMillis());*/
		Date today = null, month = null, year = null;
		Date now = MyDate.get();
		MyDate myDate = new MyDate(now);
		int y = myDate.getYear();
		int m = myDate.getMonth();
		int d = myDate.getDate();
		today = MyDate.get(y, m, d);
		month = MyDate.get(y, m, 1);
		year = MyDate.get(y, 1, 1);
		System.out.println("today: "+today);
		System.out.println("month: "+month);
		System.out.println("year: "+MyDate.format(year));
		System.out.println(today.after(year));
		
	}
	
	
	
	
	
	

}

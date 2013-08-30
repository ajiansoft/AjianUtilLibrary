package com.ajian.util.UtilLibrary.string;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class RandomNumber {
	private static final char space[]={'1','2','3','4','5','6','7','8','9','0'};
	private static final char send[] = {'a','b','c','d','e','f','g','h','i','j','k'};  
	private static String[] randomValues = new String[]{"0","1","2","3","4","5","6","7","8","9",
		"a","b","c","d","e","f","g","h","i","j","k","l","m","n","u",
		"t","s","o","x","v","p","q","r","w","y","z"};

	/**
	 * 当前系统时间和一个指定的字符串rank组成一个随机数
	 * @param 14+rank.length 这个长度的随机
	 * @return
	 */
	public static String currentDate(String rank){
		Date currentTime=new Date();
		SimpleDateFormat formatter=new SimpleDateFormat("yyyyMMddHHmmss");
		String dateString=formatter.format(currentTime)+rank;
		return dateString;
	}
	
	/**
	 * 当前系统时间和一个指定的字符串组成一个随机数
	 * @return
	 */
	private static SimpleDateFormat formatter=new SimpleDateFormat("yyMMddHHmmssms");
	public static String currentDate(){
		Date currentTime=new Date();
		Random r = new Random(System.nanoTime());  
	    int index=0;
	    StringBuilder formatStr = new StringBuilder();
		for (int i = 0; i < send.length; i++) {
			index = Math.abs(r.nextInt()) % (send.length - 1);
			formatStr.append(send[index]).append(space[r.nextInt(space.length)]);
		}  
		return formatStr.insert(0,formatter.format(currentTime)).append(r.nextInt(100000)).toString();
	}
	public static void main(String[] args) {
//		System.out.println(formatter.format(new Date()));
		/*for (int i = 0; i < 1000; i++) {
			System.out.println(currentDate());
		}*/
		//System.out.println(RandomNumber.currentDate().concat(FileOperation.getExtention("张三.jpg")));
		
		System.out.println(getNumber());
	}
		
	/**
	 * 当前系统时间，精确到秒， 最多16位 ，基本上不会重复
	 * @param num
	 * @return （当前时间 + 0~100） 之间的一个随机数 eg:2010091401010189
	 */
	public static String currentTime(){
		Date currentTime = new Date();
		Random r = new Random();
		SimpleDateFormat formater = new SimpleDateFormat("yyyyMMddHHmmss");
		String dateString = formater.format(currentTime);
		return dateString + r.nextInt(100);
	}
	
	/**
	 * 随机数
	 * @return
	 */
	public static String rankNumber(){
		return String.valueOf(System.currentTimeMillis())+Math.abs((new Random()).nextLong());
	}
	
	/**
	 * 产生一个6位随机数
	 * @return
	 */
	public static  String getNumber(){
		StringBuffer sb = new StringBuffer();
		for(int i = 0;i < 6; i++)
		{
		Double number=Math.random()*(randomValues.length-1);
		sb.append(randomValues[number.intValue()]);
		}
		return sb.toString();
	}
}

/*  
 * Copyright (c) 2010-2020 Chongqing Suilong Technology Co. Ltd. All Rights Reserved.  
 *  
 * This software is the confidential and proprietary information of  
 * Founder. You shall not disclose such Confidential Information  
 * and shall use it only in accordance with the terms of the agreements  
 * you entered into with Founder.  
 *  
 */  
package com.ajian.util.UtilLibrary.string;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * HTML标签操作工具
 * @author Ajian
 * @version Apr 12, 2013 1:33:44 PM
 */
public class HtmlUtils {
	
	/**
	 * 从一段包含img标签的html文本中获取该img标签的src的属性值.<br> 
	 * eg:<br/>HTML文本：&ltbr/&gt&ltimg src="http:www.example.com/example_10001.jpg" title="1001.jpg" /&gt&ltbr/&gt这是一场人类与病毒的战争&ltbr/&gt&ltimg src="http://www.example.com/10002.jpg" /&gt&ltbr/&gt;
	 * 	<br/>转换后集合数据：[http:www.example.com/example_10001.jpg,http://www.example.com/10002.jpg]
	 * @param imgTag:包含img标签的html文本.
	 * @return:返回包含各个img标签的src属性值的集合.
	 * @author Ajian
	 * @version Apr 12, 2013 1:34:56 PM
	 */
	public static List<String> getSrcFromImgtag(String imgTag){
		Pattern pattern = Pattern.compile("<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>");
		Matcher matcher = pattern.matcher(imgTag);
		List<String> list = new ArrayList<String>();
		while(matcher.find()){
			String s = matcher.group(1);
			list.add(s==null?"":s);
		}
		return list;
	}
	
	/**
	 * 添加点击事件在img标签上
	 * @param html
	 * @return
	 * @author Ajian
	 * @version Apr 17, 2013 7:05:29 PM
	 */
	public static String addOnclickEventInImgTag(String html){
		Pattern pattern = Pattern.compile("<img[^>]+\\s*[^>]*>");
		Matcher matcher = pattern.matcher(html);
		StringBuilder s1=new StringBuilder(),s2=new StringBuilder();
		while(matcher.find()){
			s1.delete(0, s1.length());
			s1.append(matcher.group());
			s2.delete(0, s2.length());
			s2.append(s1.substring(0, s1.lastIndexOf("/>"))).append(" onclick=function:alert(\"sdfasdfsa\");").append(" />");
			html = html.replace(s1.toString(), s2.toString());
		}
		return html;
	}
	
	public static void main(String[] args) {
		addOnclickEventInImgTag("<p><br/>等等等等<img src=\"http://192.168.1.106:8080/news/content/content_1304151418331833a6j1b0d3i2i1i0i8e0a2g074015.jpg\"/><br/><img/>南都讯 记者吴瑶 发自北京 冰岛总理西于尔扎多蒂13日抵达中国进行正式访问。</p>");
	}
}

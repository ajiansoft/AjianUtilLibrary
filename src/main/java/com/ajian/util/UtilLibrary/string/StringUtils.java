package com.ajian.util.UtilLibrary.string;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLDecoder;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * StringUtils has code borrowed from org.apache.turbine.util.StringUtils
 */
public class StringUtils
{
	
	/**
	 * 使用模型驱动的时候将模型驱动vo进行字符串解码
	 * 
	 * @param obj
	 *            待转码的vo
	 * @throws IllegalArgumentException
	 *             参数错误！参数不能为空。。。
	 * @throws IllegalAccessException
	 *             参数错误！
	 * @throws UnsupportedEncodingException
	 *             不支持的编码方式
	 */
	public static void decodeObject(Object obj) throws IllegalArgumentException, IllegalAccessException,
			UnsupportedEncodingException {
		if (StringUtils.isEmpty(obj)) {
			return;
		}
		// 取得该对象里面所有定义的字段,并对每个字段进行转码
		for (Field field : obj.getClass().getDeclaredFields()) {
			// 将此对象的 accessible 标志设置为指示的布尔值。(即,当该字段为private时,也可以访问)
			field.setAccessible(true);
			// 回指定对象上此 Field 表示的字段的值。(即,取得传入对象中改字段的值)
			Object fieldObj = field.get(obj);
			if (!StringUtils.isEmpty(fieldObj)) {
				// 只有在字段为String类型的时候才有中文乱码,因为如果是其他类型的话,在类型转换的时候就出错了
				if (field.getType() == String.class) {
					// 将指定对象变量上此 Field 对象表示的字段设置为指定的新值。(即,将传入的对象里面的这个字段设置为转码后的值)
					field.set(obj, !StringUtils.isEmpty(fieldObj.toString()) ? URLDecoder.decode(fieldObj.toString(),
							"UTF-8") : null);
				}
			}
		}
	}

	/**
	 * 判断对象是否为空
	 * 
	 * @param obj
	 *      -参数对象
	 * @return boolean
	 * 		-true:表示对象为空;false:表示对象为非空
	 */
	public static boolean isEmpty(Object obj) {
		if(obj==null)return true;
		if(obj instanceof String){
			return isEmpty(obj.toString());
		}
		return false;
	}
	/**
	 * 字符串是否为空
	 * @param str
	 * @return
	 * @author Ajian
	 * @version Jan 4, 2011 9:39:54 AM
	 */
	public static boolean isEmpty(String str){
		if(str==null)return true;
		int strLen=0;
        if((strLen = str.trim().length()) == 0)return true;
        for (int i = 0; i < strLen; i++) {
        	 if ((Character.isWhitespace(str.charAt(i)) == false))return false;
        }
        return true;
	}
	
    /**
     * Remove Underscores from a string and replaces first
     * Letters with Capitals.  foo_bar becomes FooBar
     */
    public static String removeUnderScores (String data)
    {
        String temp = null;
        StringBuffer out = new StringBuffer();
        temp = data;

        StringTokenizer st = new StringTokenizer(temp, "_");
        while (st.hasMoreTokens())
        {
            String element = (String) st.nextElement();
            out.append ( capitalize(element));
        }
        return out.toString();
    }

    /**
     * Makes the first letter caps and leaves the rest as is.
     */
    public static String capitalize ( String data )
    {
        StringBuffer sbuf = new StringBuffer(data.length());
        sbuf.append(data.substring(0, 1).toUpperCase())
            .append(data.substring(1));
        return sbuf.toString();
    }

    /**
     * Capitalizes a letter.
     */
    public static String capitalize ( java.lang.String data, int pos )
    {
        StringBuffer buf = new StringBuffer(data.length());
        buf.append( data.substring(0,pos -1) );
        buf.append( data.substring(pos - 1,pos).toUpperCase() );
        buf.append( data.substring(pos, data.length()) );
        return buf.toString();
    }

    /**
     * un-capitalizes a letter.
     */
    public static String unCapitalize ( String data, int pos )
    {
        StringBuffer buf = new StringBuffer(data.length());
        buf.append( data.substring(0, pos -1) );
        //System.out.println("1:" + buf.toString());
        buf.append( data.substring(pos - 1, pos).toLowerCase() );
        //System.out.println("1:" + buf.toString());
        buf.append( data.substring(pos, data.length()) );
        //System.out.println("1:" + buf.toString());
        return buf.toString();
    }

    /**
     * Splits the provided CSV text into a list.
     *
     * @param text      The CSV list of values to split apart.
     * @param pos       The piece of the array
     * @return          The list of values.
     */
    public static String[] split(String text, String separator)
    {
        StringTokenizer st = new StringTokenizer(text, separator);
        String[] values = new String[st.countTokens()];
        int pos = 0;
        while (st.hasMoreTokens())
        {
            values[pos++] = st.nextToken();
        }
        return values;
    }




    /**
     * Joins the elements of the provided array into a single string
     * containing a list of CSV elements.
     *
     * @param list      The list of values to join together.
     * @param separator The separator character.
     * @return          The CSV text.
     */
    public static String join(String[] list, String separator)
    {
        StringBuffer csv = new StringBuffer();
        for (int i = 0; i < list.length; i++)
        {
            if (i > 0)
            {
                csv.append(separator);
            }
            csv.append(list[i]);
        }
        return csv.toString();
    }

    /**
     * 将字符串中tag替换成为指定的info，只替换一次
     * @param source，原来的字符串
     * @param info，替换tag的字符串
     * @param tag，要被替换掉的tag
     * @return 替换后的内容
     */
    public static StringBuffer replaceOnlyOne(String source, String info, String tag) {
        if ((source == null) || (source.length() == 0))
            return new StringBuffer("");

    	if ((info == null) || (tag == null) || (tag.length() == 0))
            return new StringBuffer(source);

        int index = source.indexOf(tag);
        if (index < 0)
            return new StringBuffer(source);

        int length = tag.length();
        return (new StringBuffer(source)).replace(index, index+length, info);
    }

    /**
     * 将字符串中所有的tag全部替换成为指定的info
     * @param source，原来的字符串
     * @param infos:替换tag的字符串集合，将使用此集合中的值依次替换tag
     * @param tag:要被替换掉的tag
     * @return 替换后的内容
     */
    public static String replaceAll(String source, String tag,List<String> infos) {
    	if(infos==null||infos.size()==0)return source;
		Pattern mpattern = Pattern.compile(tag);
		Matcher mmatcher = mpattern.matcher(source);
		StringBuffer msg = new StringBuffer();
		int i = 0;
		while(mmatcher.find()){
			if(infos.size()<=i)continue;
			mmatcher.appendReplacement(msg, infos.get(i));
			i++;
		}
		mmatcher.appendTail(msg);
		return msg.toString();
    }

    /**
     * 将输入字符中的SQL保留字进行替换，目前只替换英文半角的单引号'
     * 单引号替换方法：一个单引号换成连续的两个单引号，例如'ABC'D替换成''ABC''D
     * @param s
     * @return
     */
    public static String getSQLencode(String s){
	if ((s == null) || (s.length() == 0))
	    return "";
	StringBuffer sb = new StringBuffer();
	char c;
	for(int i = 0; i < s.length(); i++) {
	    c = s.charAt(i);
	    switch (c){
		case '\'':
		    sb.append("''");
		    break;
		default:
		    sb.append(c);
	    }
	}
	return sb.toString();
 }

 /**
  * 将输入字符中的格式化成precision指定的程度,截掉的部分用'...'布齐
  */
  public static String getFormatString(String s,int precision){
    String retValue="";
    if((s == null) || (s.length() == 0)) retValue="";
    if(s.length()<=precision)  retValue=s;
    if(s.length()==precision+1) retValue=s;
    if(s.length()>precision+1) retValue=s.substring(0,precision-1)+"...";
    return retValue;
  }
  
  	/**
  	 * 截取指定长字符串
   	* @param s
 	* @param precision
 	* @return
 	*/
  public static String subString(String s,int precision){
	    String retValue="";
	    if((s == null) || (s.length() == 0)) retValue="";
	    if(precision >= 0) retValue=s;
	    if(s.length()<=precision)  retValue=s;
	    if(s.length()==precision+1) retValue=s;
	    if(s.length()>precision+1) retValue=s.substring(0,precision-1);
	    return retValue;
	  }
  
	public static String string2Json(String s) {     
	    StringBuffer sb = new StringBuffer ();     
	    for (int i=0; i<s.length(); i++) {     
	   
	        char c = s.charAt(i);     
	        switch (c) {     
	        case '\"':     
	            sb.append("\\\"");     
	            break;     
	        case '\\':     
	            sb.append("\\\\");     
	            break;     
	        case '/':     
	            sb.append("\\/");     
	            break;     
	        case '\b':     
	            sb.append("\\b");     
	            break;     
	        case '\f':     
	            sb.append("\\f");     
	            break;     
	        case '\n':     
	            sb.append("\\n");     
	            break;     
	        case '\r':     
	            sb.append("\\r");     
	            break;     
	        case '\t':     
	            sb.append("\\t");     
	            break;     
	        default:     
	            sb.append(c);   
	        } 
	    }
	    return sb.toString();     
	 }  
	
	/**
	 * 截取字符串
	 * 
	 * @param str
	 * @param beginIndex
	 *            字符串开始索引
	 * @param endIndex
	 *            字符串结束索引
	 * @param type
	 *            返回类型，0：结尾处无省略号 1：结尾处用“...”替代
	 * @return
	 */
	public static String subStr(String str, Integer beginIndex,
			Integer endIndex, String type) {
		if (endIndex < str.length()) {
			if (type.equals("0")) {
				return str.substring(beginIndex, endIndex);
			} else {
				return str.substring(beginIndex, endIndex) + ". . .";
			}
		} else {
			return str;
		}
	}

	/**
	 * 返回中英混合内容的长度
	 * 
	 * @param str
	 * @return
	 */
	public static int getStringLength(String str) {
		return (str.getBytes().length + str.length()) / 2;
	}
	
	/**
	 * 将String的性别转换为boolean
	 * @param sex
	 * @return
	 */
	public static boolean changeSex(String sex){
		if(sex.equals("0")){
			return true;
		}
		return false;
	}
}
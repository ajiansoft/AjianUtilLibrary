/*  
 * Copyright (c) 2010-2020 Chongqing Suilong Technology Co. Ltd. All Rights Reserved.  
 *  
 * This software is the confidential and proprietary information of  
 * Founder. You shall not disclose such Confidential Information  
 * and shall use it only in accordance with the terms of the agreements  
 * you entered into with Founder.  
 *  
 */  
package com.ajian.util.UtilLibrary.http;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;

public class ShowRequestData {
	public static  void show(HttpServletRequest request){
		String con = request.getHeader("Content-Type");
		InputStream in = null;
		try {
			in = request.getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		OutputStream out = new ByteArrayOutputStream();
		byte[] buffer = new byte[4096];
		int c = 0;
		try {
			while ((c = in.read(buffer)) != -1){
				out.write(buffer, 0, c);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Content-Type:"+con+"\n\n"+out.toString());
		try {
			in.close();
			out.close(); 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

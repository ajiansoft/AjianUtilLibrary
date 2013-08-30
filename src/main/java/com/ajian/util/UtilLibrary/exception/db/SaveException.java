/*  
 * Copyright (c) 2010-2020 Chongqing Suilong Technology Co. Ltd. All Rights Reserved.  
 *  
 * This software is the confidential and proprietary information of  
 * Founder. You shall not disclose such Confidential Information  
 * and shall use it only in accordance with the terms of the agreements  
 * you entered into with Founder.  
 *  
 */  
package com.ajian.util.UtilLibrary.exception.db;


public class SaveException extends Exception{
	private static final long serialVersionUID = 4251239001833663670L;
	
	public SaveException(){
		super();
	}
	
	public SaveException(Exception e){
		super(e);
	}
	
	public SaveException(String msg){
		super(msg);
	}

}

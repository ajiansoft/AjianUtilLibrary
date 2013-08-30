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


/**
 * 删除异常
 * @author Ajian
 * @version Mar 9, 2013 4:11:53 PM
 */
public class DeleteException extends Exception{
	private static final long serialVersionUID = 5043372924478954981L;

	public DeleteException(){
		super();
	}
	
	public DeleteException(Exception e){
		super(e);
	}
	
	public DeleteException(String msg){
		super(msg);
	}
	
	
}

/*  
 * Copyright (c) 2010-2020 Chongqing Suilong Technology Co. Ltd. All Rights Reserved.  
 *  
 * This software is the confidential and proprietary information of  
 * Founder. You shall not disclose such Confidential Information  
 * and shall use it only in accordance with the terms of the agreements  
 * you entered into with Founder.  
 *  
 */   
package com.ajian.util.UtilLibrary.exception.operation.login;

import com.ajian.util.UtilLibrary.exception.BusinessException;

/**
 * 用户登录异常<br>
 * 该异常表示用户状态为禁用. 
 * @author Ajian
 * @version Dec 13, 2012 12:40:09 PM
 */
public class UserStateStopException extends BusinessException{
	private static final long serialVersionUID = 7852583917107891489L;
	
	public UserStateStopException(int execption){
		super(execption);
	}
	public UserStateStopException(){
	}

}

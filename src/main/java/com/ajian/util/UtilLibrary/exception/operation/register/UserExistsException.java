/*  
 * Copyright (c) 2010-2020 Chongqing Suilong Technology Co. Ltd. All Rights Reserved.  
 *  
 * This software is the confidential and proprietary information of  
 * Founder. You shall not disclose such Confidential Information  
 * and shall use it only in accordance with the terms of the agreements  
 * you entered into with Founder.  
 *  
 */   
package com.ajian.util.UtilLibrary.exception.operation.register;

import com.ajian.util.UtilLibrary.exception.BusinessException;

/**
 * 用户注册异常<br>
 * 该异常表示用户已经存在. 
 * @author Ajian
 * @version Dec 13, 2012 11:47:51 AM
 */
public class UserExistsException extends BusinessException{
	private static final long serialVersionUID = -8723806332804870034L;
	
	public UserExistsException(int execption){
		super(execption);
	}
	public UserExistsException(){
	}
	
}

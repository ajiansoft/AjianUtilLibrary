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
 * 此异常表示用户密码匹配失败.
 * @author Ajian
 * @version Dec 13, 2012 12:55:53 PM
 */
public class UserPasswordWrongException extends BusinessException {

	private static final long serialVersionUID = -2689537507490409533L;

	public UserPasswordWrongException(int execption){
		super(execption);
	}
	public UserPasswordWrongException(){
	}
}

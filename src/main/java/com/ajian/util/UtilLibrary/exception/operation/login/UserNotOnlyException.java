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

public class UserNotOnlyException extends BusinessException {
	private static final long serialVersionUID = -3501718155943442400L;
	public UserNotOnlyException(int execption){
		super(execption);
	}
	public UserNotOnlyException(){
	}
}

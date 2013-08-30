/*  
 * Copyright (c) 2010-2020 Chongqing Suilong Technology Co. Ltd. All Rights Reserved.  
 *  
 * This software is the confidential and proprietary information of  
 * Founder. You shall not disclose such Confidential Information  
 * and shall use it only in accordance with the terms of the agreements  
 * you entered into with Founder.  
 *  
 */   
package com.ajian.util.UtilLibrary.exception.operation;

import com.ajian.util.UtilLibrary.exception.BusinessException;


/**
 * 数据重复异常<br>
 * 该异常表示此数据已经存在. 
 * @author Ajian
 * @version Dec 13, 2012 11:47:51 AM
 */
public class DataExistsException extends BusinessException{
	private static final long serialVersionUID = -8723806332804870034L;
	
	public DataExistsException(int execption){
		super(execption);
	}
	public DataExistsException(){
		super();
	}
	public DataExistsException(String msg){
		super(msg);
	}
}

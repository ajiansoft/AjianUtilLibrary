package com.ajian.util.UtilLibrary.exception;


/**   
 * BusinessException 概要说明  
 * 业务异常   
 * @author Ajian
 * @version Dec 13, 2011 10:35:57 AM
 */
public class BusinessException extends Exception {
	private static final long serialVersionUID = -5178401728244870709L;
	/**
	 * 异常信息编号
	 */
	protected Integer  ExceptionNumber;
	/**
	 * 异常信息通知,常用于对前端UI定制异常提示消息
	 */
	protected String ExceptionNotice;
	
	public BusinessException() {
		super();
	}
	public BusinessException(String message, Throwable cause) {
		super(message, cause);
	}
	public BusinessException(String message) {
		super(message);
	}
	public BusinessException(int exceptionNumber,String message) {
		super(message);
		this.ExceptionNumber=exceptionNumber;
	}
	public BusinessException(int exceptionNumber) {
		this.ExceptionNumber=exceptionNumber;
	}
	public BusinessException(int exceptionNumber,String notice,String message) {
		super(message);
		this.ExceptionNumber=exceptionNumber;
		this.ExceptionNotice=notice;
	}
	public BusinessException(String notice,int exceptionNumber){
		super();
		this.ExceptionNumber=exceptionNumber;
		this.ExceptionNotice=notice;
	}
	public BusinessException(Throwable cause) {
		super(cause);
	}

	public Integer getExceptionNumber() {
		return ExceptionNumber;
	}

	public String getExceptionNotice() {
		return ExceptionNotice;
	}

	public void setExceptionNumber(int exceptionNumber) {
		ExceptionNumber = exceptionNumber;
	}

	public void setExceptionNotice(String exceptionNotice) {
		ExceptionNotice = exceptionNotice;
	}

}

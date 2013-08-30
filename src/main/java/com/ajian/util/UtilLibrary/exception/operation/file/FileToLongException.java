package com.ajian.util.UtilLibrary.exception.operation.file;

import com.ajian.util.UtilLibrary.exception.BusinessException;


public class FileToLongException extends BusinessException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6919753827231508995L;
	
	public FileToLongException(int execption){
		super(execption);
	}
	public FileToLongException(){
		super("gif图片不能超过1M!");
	}
}

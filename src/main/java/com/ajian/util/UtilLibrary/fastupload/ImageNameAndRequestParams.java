/*  
 * Copyright (c) 2010-2020 Chongqing Suilong Technology Co. Ltd. All Rights Reserved.  
 *  
 * This software is the confidential and proprietary information of  
 * Founder. You shall not disclose such Confidential Information  
 * and shall use it only in accordance with the terms of the agreements  
 * you entered into with Founder.  
 *  
 */  
package com.ajian.util.UtilLibrary.fastupload;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sourceforge.fastupload.MultiPart;

/**
 * 用于传递上传文件名称和请求参数的实体对象.<br>
 * ps:fastupload上传文件成功后解析专用
 * @author Ajian
 * @version Feb 25, 2013 10:15:39 AM
 */
public class ImageNameAndRequestParams {
	private List<String> imageNames;
	private Map<String, String> params;
	
	
	/**
	 * 从请求对象MultiPart中获取请求参数
	 * @param multiPartList
	 * @return
	 * @author Ajian
	 * @version Feb 25, 2013 11:21:34 AM
	 */
	public static Map<String, String> getParamsFromRequest(List<MultiPart> multiPartList){
		Map<String, String> map = new HashMap<String, String>();
		if(multiPartList!=null){
			for (MultiPart m : multiPartList) {
				if(!m.isFile()){
					try {
						map.put(m.getFieldName(), m.getString("UTF-8"));
					} catch (UnsupportedEncodingException e) {}
				}
			}
		}
		return map;
	}
	
	public final List<String> getImageNames() {
		return imageNames;
	}
	public final Map<String, String> getParams() {
		return params;
	}
	public final void setImageNames(List<String> imageNames) {
		this.imageNames = imageNames;
	}
	public final void setParams(Map<String, String> params) {
		this.params = params;
	}
	public ImageNameAndRequestParams(List<String> imageNames,
			Map<String, String> params) {
		super();
		this.imageNames = imageNames;
		this.params = params;
	}
	public ImageNameAndRequestParams() {
		super();
	}
}

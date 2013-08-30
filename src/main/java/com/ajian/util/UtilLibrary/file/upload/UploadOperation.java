package com.ajian.util.UtilLibrary.file.upload;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;

import com.ajian.util.UtilLibrary.string.StringUtils;

/**
 * 上传操作工具
 * @author Ajian
 * @version Nov 27, 2012 10:25:41 AM
 */
public class UploadOperation extends com.ajian.util.UtilLibrary.file.FileOperation {
	
	/**
	 * 文件上传
	 * @param url 为空上传到默认地址 不为空上传到指定路径(格式：/xxx) eg: xxx/upload
	 * @param dst 获取的临时文件
	 * @param filename 存在服务器中的文件名  
	 * @return
	 * @throws Exception 
	 */
	public static File upload(File dst,String url,String filename) throws Exception{
		StringBuffer dir = new StringBuffer();
		/*if(url==null||StringUtils.isEmpty(url)){
			dir.append(servletContext.getRealPath("/upload"));
		}else{
			dir.append(servletContext.getRealPath("/")).append(url);
			if(!endCharIsSystemSeparator(url)){
				dir.append(System_Separator);
			}
		}*/
		File aimFile =  new File(dir.toString());
		if(!aimFile.exists()){
			aimFile.mkdirs();
			aimFile=null;
		}
		if(filename==null||StringUtils.isEmpty(filename)){
			filename = getNewFileName(dst.getName());
		}
		dir.append(filename);
	 	
		return DoUpload.doUpload(dst,dir.toString());
	}
	
	
	/**
	 * 批量上传文件
	 * @param files
	 * @param names
	 * @param savePath
	 * @return 返回上传文件名称集合
	 * @author Ajian
	 * @version Sep 14, 2012 6:03:07 PM
	 * @throws Exception 
	 */
	public static List<String> batchUpload(List<File> files,List<String> names,String savePath) throws Exception{
		int s = files.size();
		String[] oldImgNameList = new String[s];
		File file = new File(savePath);
		if(!file.exists()){
			file.mkdirs();file=null;
		}
		for (int i = 0; i < s; i++) {
			String newName = getNewFileName(names.get(i));
			String fn = (savePath+newName);
			try {
				DoUpload.doUpload(files.get(i), fn);
				oldImgNameList[i]=newName;
			} catch (Exception e) {
				e.printStackTrace();
			} finally{
				fn = null;
				newName=null;
			}
		}
		return Arrays.asList(oldImgNameList);
	}
	
	/**
	 * 多线程批量上传文件
	 * @param es:线程池
	 * @param files
	 * @param names
	 * @param savePath
	 * @author Ajian
	 * @version Sep 14, 2012 6:03:07 PM
	 * @throws Exception 
	 */
	public static void batchUpload(ExecutorService es,final List<File> files,List<String> names,String savePath) throws Exception{
		for (int i = 0; i < files.size(); i++) {
			final String fn = (savePath+getNewFileName(names.get(i)));
			final File file = files.get(i);
			es.execute(new Runnable(){
				@Override
				public void run() {
					try {
						DoUpload.doUpload(file, fn);
					} catch (Exception e) {}
				}
			});
		}
	}
	
}

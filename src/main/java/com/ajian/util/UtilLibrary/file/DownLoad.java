package com.ajian.util.UtilLibrary.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class DownLoad{

	public static void down(HttpServletRequest request,HttpServletResponse response,String filename,String fPath){
		try {
			BufferedInputStream bis = null;
			BufferedOutputStream bos = null;
			OutputStream fos = null;
			InputStream fis = null;
			
			//如果是从服务器上取就用这个获得系统的绝对路径方法。 String filepath = servlet.getServletContext().getRealPath("/" + path);
			String filepath= request.getSession().getServletContext().getRealPath("/"+fPath);
			File uploadFile = new File(filepath);
			fis = new FileInputStream(uploadFile);
			bis = new BufferedInputStream(fis);
			fos = response.getOutputStream();
			bos = new BufferedOutputStream(fos);
			//这个就就是弹出下载对话框的关键代码
			response.reset();
			response.setHeader("Content-disposition",
			     "attachment;filename=" +new String( filename.getBytes("GB2312"), "ISO8859-1" ));//URLEncoder.encode(filename, "UTF-8"));
			response.setContentLength(fis.available());
			int bytesRead = 0;
			
			byte[] buffer = new byte[8192];
			while ((bytesRead = bis.read(buffer, 0, 8192)) != -1) {
			bos.write(buffer, 0, bytesRead);
			}
			bos.flush();
			fis.close();
			bis.close();
			fos.close();
			bos.close();
		} catch (IOException e) {
		}
	}
	
	/**
	 * 从指定URI中下载一份文件，保存到指定路径中
	 * @param rui
	 * @param savePath
	 * @return
	 * @throws IOException 
	 */
	public static File downInternetFile(URL url,String fileName,String savePath) throws IOException{
		InputStream is = url.openStream();
		OutputStream os = null;
		File f = new File(savePath.replace("\\", "/"));
		if(!f.exists()){
			f.mkdirs();
		}
		os = new FileOutputStream(savePath+fileName);
		int bytesRead = 0;
		byte[] buffer = new byte[1024];
		while((bytesRead=is.read(buffer, 0, 1024)) != -1){
			os.write(buffer,0,bytesRead);
		}
		is=null;os=null;f=null;buffer=null;
		return new File(savePath+fileName);
	}
	
	public static void main(String[] args) {
		//http://192.168.1.106:8080/HelpSystem/newFile/system/help.apk
		try {
			DownLoad.downInternetFile(new URL("http://60.12.121.68:8080/HelpSystem/newFile/system/help.apk"),"help_download.apk", "E:\\新建文件夹\\");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
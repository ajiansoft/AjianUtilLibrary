package com.ajian.util.UtilLibrary.file.upload;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sourceforge.fastupload.FastUploadParser;
import net.sourceforge.fastupload.FileFactory;
import net.sourceforge.fastupload.MultiPart;

/**
 * 执行上传操作
 * @author Ajian
 * @version Nov 27, 2011 10:26:10 AM
 */
public class DoUpload {
	/**
	 * 上传核心
	 * @param sourceFile 资源文件
	 * @param aim 目标文件
	 * @throws Exception 
	 */
	public static File doUpload(File sourceFile, String aim) throws Exception {
		InputStream in = null;
		OutputStream out = null;
		FileInputStream fis = null;
		FileOutputStream fos = null;
		byte[] buffer = new byte[1024];
		try {
			File f = new File(aim);
			fis = new FileInputStream(sourceFile);
			fos = new FileOutputStream(f);
			in = new BufferedInputStream(fis,1024);
			out = new BufferedOutputStream(fos, 1024);
			long num = sourceFile.length() / 1024;
			for (int i = 0; i <= num; i++) {
				out.write(buffer, 0, in.read(buffer));
			}                            
			out.flush();
			return f;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(fos!=null){fos.flush();fos.close();fos=null;}
			if(fis!=null){fis.close();fis=null;}
			if(in!=null){in.close();in = null;}
			if(out!=null) {out.close();out = null;}
			buffer = null;
		}
	}
	
	/**
	 * fastupload组件上传文件
	 * @return 上传成功文件对象集合，返回null，上传失败.
	 * @throws Exception
	 * @author Ajian
	 * @version Sep 17, 2012 3:58:40 PM
	 */
	public static List<MultiPart> doUpload(HttpServletRequest request){
		try {
			FileFactory factory = FileFactory.getInstance();
			factory.setThreshold(1024*1024*5);//每个文件大小,单位字节
			factory.setMaxContentLength(1024*1024*10);//总请求流大小,单位字节
			FastUploadParser uploadParser = new FastUploadParser(request,factory);
			//ProgressListener listener = new ProgressListener(uploadParser);
			List<MultiPart> list = uploadParser.parseList();
			//System.out.println("已上传："+listener.progress()+"byte");
			return list;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}

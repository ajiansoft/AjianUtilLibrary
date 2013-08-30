package com.ajian.util.UtilLibrary.file.upload.uploadImg;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sourceforge.fastupload.FileFactory;
import net.sourceforge.fastupload.MultiPart;

import com.ajian.util.UtilLibrary.exception.operation.file.FileToLongException;
import com.ajian.util.UtilLibrary.fastupload.ImageNameAndRequestParams;
import com.ajian.util.UtilLibrary.file.FileOperation;
import com.ajian.util.UtilLibrary.file.upload.DoUpload;
import com.ajian.util.UtilLibrary.file.upload.UploadOperation;


/**
 * 图片文件上传处理工具
 * @author Ajian
 * @version Nov 27, 2012 1:27:26 PM
 */
public class UploadImageOperation extends UploadOperation{
	
	/**
	 * 批量上传图片,并删除指定文件
	 * @param files
	 * @param names
	 * @param savePath
	 * @return 返回上传文件名称集合
	 * @author zhangxin
	 * @version Sep 14, 2012 6:03:07 PM
	 * @throws Exception 
	 */
	public static List<String> batchUploadImage(List<File> files,List<String> names,String savePath,String oldfilePath) throws Exception{
		List<String> oldImgNameList = batchUpload(files,names,savePath);
		FileOperation.delFile(oldfilePath);
		return oldImgNameList;
	}
	
	/**
	 * 批量上传图片文件.
	 * @param request
	 * @param savePath 上传图片保存路径
	 * @return 返回上传成功的图片名称
	 * @author Ajian
	 * @version Feb 20, 2013 2:54:54 PM
	 */
	public static List<String> batchUploadImage(HttpServletRequest request,String savePath){
		List<MultiPart> multiList = DoUpload.doUpload(request);
		return batchUploadImage(multiList, savePath).getImageNames();
	}
	
	/**
	 * 批量上传图片文件.
	 * @param request
	 * @param savePath 上传图片保存路径
	 * @return 返回上传成功的图片名称
	 * @author Ajian
	 * @version Feb 20, 2013 2:54:54 PM
	 */
	public static ImageNameAndRequestParams batchUploadImageAndParams(HttpServletRequest request,String savePath){
		List<MultiPart> multiList = DoUpload.doUpload(request);
		return batchUploadImage(multiList, savePath);
	}
	/**
	 * 批量上传图片文件.
	 * @param request
	 * @param savePath 上传图片保存路径
	 * @return 返回上传成功的图片名称
	 * @author Ajian
	 * @version Feb 20, 2013 2:54:54 PM
	 * @throws FileToLongException 
	 */
	public static ImageNameAndRequestParams batchUploadImageAndParam(HttpServletRequest request,String savePath) throws Exception{
		List<MultiPart> multiList = DoUpload.doUpload(request);
		return batchUploadImages(multiList, savePath);
	}
	
	/**
	 * 批量上传图片文件.
	 * @param multiList 多文件对象
	 * @param savePath 上传图片保存路径
	 * @return 返回上传成功的图片名称和请求参数对象
	 * @author Ajian
	 * @version Feb 20, 2013 2:54:54 PM
	 */
	public static ImageNameAndRequestParams batchUploadImage(List<MultiPart> multiList,String savePath){
		if(multiList!=null && savePath!=null){
			File f = new File(savePath);
			if(!f.isDirectory())f.mkdirs();
			List<String> fileNameList = new ArrayList<String>(8);
			Map<String, String> params = new HashMap<String, String>();
			for (MultiPart m : multiList) {
				if(m.isFile()){
					if(m.getBytes()==0)continue;
					String newName = getNewFileName(m.getFileName());
					try {
						m.toFile((savePath.endsWith("\\")||savePath.endsWith("/"))?savePath+newName:savePath+FileOperation.System_Separator+newName);
					} catch (IOException e) {
						e.printStackTrace();
					}
					//生成文件名称
					fileNameList.add(newName);
				}else{
					try {
						params.put(m.getFieldName(), URLDecoder.decode(m.getString(),"UTF-8"));
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					} catch (NullPointerException e) {
						params.put(m.getFieldName(), "");
					}
				}
			}
			return new ImageNameAndRequestParams(fileNameList,params);
		}
		return null;
	}
	
	/**
	 * 批量上传图片文件.
	 * @param multiList 多文件对象
	 * @param savePath 上传图片保存路径
	 * @return 返回上传成功的图片名称和请求参数对象
	 * @author Ajian
	 * @version Feb 20, 2013 2:54:54 PM
	 */
	public static ImageNameAndRequestParams batchUploadImages(List<MultiPart> multiList,String savePath) throws Exception{
		if(multiList!=null && savePath!=null){
			File f = new File(savePath);
			if(!f.isDirectory())f.mkdirs();
			List<String> fileNameList = new ArrayList<String>(8);
			Map<String, String> params = new HashMap<String, String>();
			for (MultiPart m : multiList) {
				if(m.isFile()){
					if(m.getBytes()==0)continue;
					String newName = getNewFileName(m.getFileName());
					try {
						m.toFile((savePath.endsWith("\\")||savePath.endsWith("/"))?savePath+newName:savePath+FileOperation.System_Separator+newName);
					} catch (IOException e) {
						e.printStackTrace();
					}
					//生成文件名称
					fileNameList.add(newName);
				}else{
					try {
						params.put(m.getFieldName(), m.getString("UTF-8"));
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
				}
			}
			return new ImageNameAndRequestParams(fileNameList,params);
		}
		return null;
	}
	
	/**
	 * 通过fastupload组件上传文件，此处专为上传图片文件设置文件生成工厂参数.
	 * @return
	 * @author Ajian
	 * @version Feb 20, 2013 2:38:14 PM
	 */
	public static FileFactory getUploadImageFileFactory(){
		FileFactory fileFactory = FileFactory.getInstance();
		fileFactory.setAllowedTypes("image/jpeg");
		fileFactory.setAllowedExtensions(".jpg,.png,.jpeg,.bmp,.gif");
		fileFactory.setThreshold(1024*2*1000000);//设置每个文件最大大小(单位：字节)
		return fileFactory;
	}
	
	
}

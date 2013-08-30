package com.ajian.util.UtilLibrary.file;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.swing.ImageIcon;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * 图片操作工具类
 * @author Ajian
 * @version Nov 27, 2012 1:15:24 PM
 */
public class ImageOperation extends FileOperation{
	
	/**
	 * 默认小图的前缀标志
	 */
	public static final String DEFAULT_SMALLIMG_PREFIX="SMALL_";
	/**
	 * 默认图片后缀
	 */
	public static final String DEFAULT_IMAGE_PREFIX="jpg";
	/**
	 *	默认小图的宽 
	 */
	protected static final int DEFAULT_SMALLIMG_WIDTH = 120;
	/**
	 * 默认小图的长
	 */
	protected static final int DEFAULT_SMALLIMG_HEIGHT= 120;
	
	/**
	 * 批量处理图片(根据原图生成缩略图)
	 * @param rootPath:保存路径根目录
	 * @param oldImgName:原图名称
	 * @param imageNamePrefix:缩略图前缀
	 * @param targetWidth:目标宽度
	 * @param targetHeight:目标高度
	 * @return 新的缩略图名称集合
	 * @author Ajian
	 * @version Sep 14, 2012 6:03:07 PM
	 */
	public static List<String> batchDisposeImg(String rootPath,List<String> oldImgName,String imageNamePrefix,Integer targetWidth,Integer targetHeight){
		int size = oldImgName.size();
		String[] newImgNames = new String[size];
		File file = null;
		for (int i=0;i<size;i++) {
			try {
				String str = oldImgName.get(i);
				//对图片进行缩放处理
				file = new File(endCharIsSystemSeparator(rootPath)?rootPath+str:rootPath+System_Separator+str);
				newMyPicBySize(file, rootPath,newImgNames[i]=(imageNamePrefix==null?DEFAULT_SMALLIMG_PREFIX:imageNamePrefix)+str,targetWidth,targetHeight);
			} catch(Exception e){
				e.printStackTrace();
			} finally{
				file=null;
			}
		}
		return Arrays.asList(newImgNames);
	}
	
	/**
	 * 批量处理图片(根据原图生成缩略图)
	 * @param rootPath:保存路径根目录
	 * @param oldImgName:原图名称
	 * @return 新的缩略图名称集合
	 * @author Ajian
	 * @version Sep 14, 2012 6:03:07 PM
	 * @throws Exception 
	 */
	public static List<String> batchDisposeImg(String rootPath,List<String> oldImgName){
		return batchDisposeImg(rootPath, oldImgName, null, null, null);
	}
	
	/**
	 * 采用缩略图算法，等比缩放原图长宽
	 * @param sourceFile 源文件
	 * @param rootPath 保存路径的根目录，eg:E:/upload/image/
	 * @param filename 保存的文件名，eg:20121012s3df2f9dfg32h43.jpg
	 * @param targetWidth 目标宽度，为null则采用默认宽度120
	 * @param targetHeight 目标高度，为null则采用默认高度120
	 * @author Ajian
	 * @version Sep 24, 2012 6:20:48 PM
	 */
	public static void newMyPicBySize(File sourceFile,String rootPath,String fileName,Integer targetWidth,Integer targetHeight) throws IOException{
		String smallImgSavePath = (endCharIsSystemSeparator(rootPath)?rootPath+fileName:rootPath+System_Separator+fileName);
		//判断图片那一边需要缩小
		BufferedImage bSrc = null;
		try {
			//读入源文件
			bSrc = ImageIO.read(sourceFile);
			//得到原图宽、高
			int old_width = bSrc.getWidth();
			int old_height = bSrc.getHeight();
			//得到目标文件宽、高
			int new_width=targetWidth==null?DEFAULT_SMALLIMG_WIDTH:targetWidth,new_height=targetHeight==null?DEFAULT_SMALLIMG_HEIGHT:targetHeight;
			if(old_width<=new_width)new_width= old_width;
			if(old_height<=new_height)new_height= old_height;
			// 目标图像缓冲对象
			//BufferedImage bTarget = new BufferedImage(new_width, new_height,BufferedImage.TYPE_3BYTE_BGR);
			// 得到目标图片与源图片宽度、高度的比例。
			double sx = (double)new_width/(double)old_width;
			double sy = (double)new_height/(double)old_height;
			new_width=(int)(sx*old_width);
			new_height=(int)(sy*old_height);
			
			BufferedImage bfImage = new BufferedImage(new_width, new_height,
					BufferedImage.TYPE_INT_RGB);
			/*
			Image.SCALE_SMOOTH //平滑优先
			Image.SCALE_FAST//速度优先
			Image.SCALE_AREA_AVERAGING //区域均值
			Image.SCALE_REPLICATE //像素复制型缩放
			Image.SCALE_DEFAULT //默认缩放模式
			*/
			bfImage.getGraphics().drawImage(
					bSrc.getScaledInstance(new_width, new_height,
							Image.SCALE_SMOOTH), 0, 0, null);
			FileOutputStream os = new FileOutputStream(smallImgSavePath);
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(os);
			encoder.encode(bfImage);
			os.close();

			/*
			// 构造图像变换对象
			AffineTransform transform = new AffineTransform();
			// 设置图像转换的比例
			transform.setToScale(sx, sy);
			// 构造图像转换操作对象
			AffineTransformOp ato = new AffineTransformOp(transform, null);
			// 实现转换，将bSrc转换成bTarget
			ato.filter(bSrc, bTarget);
			//输出目标图片
			String[] ss = fileName.split("\\.");
			ImageIO.write(bTarget,ss.length==1?DEFAULT_IMAGE_PREFIX:ss[ss.length-1], new File(smallImgSavePath));*/
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			if(bSrc!=null)bSrc.flush();
			bSrc=null;
		}
	}
	
	/**
	 * 采用缩略图算法，等比缩放原图长宽
	 * @param sourceFile 源文件
	 * @param rootPath 保存路径的根目录，eg:E:/upload/image/
	 * @param filename 保存的文件名，eg:20121012s3df2f9dfg32h43.jpg
	 * @author Ajian
	 * @version Sep 24, 2012 6:20:48 PM
	 * @throws IOException 
	 */
	public static void newMyPicBySize(File sourceFile,String rootPath,String fileName) throws IOException{
		newMyPicBySize(sourceFile, rootPath, fileName, null, null);
	}
	
	/**
	 * 给图片添加文字水印
	 * @param filePath 需要添加水印的图片的路径
	 * @param markContent 水印的文字
	 * @param markContentColor 水印文字的颜色
	 * @param qualNum 图片质量
	 * @param position 字体出现位置 1：左上 4：左下 暂时只提供这两个位置。
	 * @param style 字体样式
	 * @param fontSize 字体大小
	 * @return
	 */
	public static String createMark(HttpServletRequest request, String filePath, String markContent, Color markContentColor, float qualNum, int position, String style, int fontSize) {
		/* 设置水印文字出现的内容
		 * 1.左上:10， 10
		 * 2.右上:width -100 ,10
		 * 3.中间:width /2 , height/2
		 * 4.左下: 0 , height - 10
		 * 5.右下: width - 100 , height -10
		 */
		int pW = 0;
		int pH = 0;
		//String path = ServletActionContext.getServletContext().getRealPath("/").replace("\\", "/") + filePath;
		String path = filePath;
		
		ImageIcon imgIcon = new ImageIcon(path);
		Image theImg = imgIcon.getImage();
		int width = theImg.getWidth(null);
		int height = theImg.getHeight(null);
		BufferedImage bimage = new BufferedImage(width, height,	BufferedImage.TYPE_INT_RGB);
		Graphics2D g = bimage.createGraphics();
		/**设置字体属性*/
		g.setBackground(Color.white);
		g.drawImage(theImg, 0, 0, null);
		if(position == 1){
			pW = 10;pH = 15;
		}else if (position == 2){
			pW= width/ 2; pH = 20;
		}else if (position == 3){//中间
			pW = 10;pH = height / 2;
		}else if (position == 4){
			pW = 10;pH = height - 10;
		}else if (position == 5){
			pW =width / 2;pH = height - 10;
		}
		g.setColor(markContentColor);
		//
		g.setFont(new Font(style, 1, fontSize));
		g.drawString(markContent, pW , pH); // 添加水印的文字和设置水印文字出现的内容
		g.dispose();
		/*try {
			FileOutputStream out = new FileOutputStream(path);
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(bimage);
			encoder.encode(bimage, param);
			out.close();
		} catch (Exception e) {
			log.error(e);
			return null;
		}*/
		return path;
	}
	

	/**
	 * 图片水印
	 * @param position 位置 1,2,3,4分别为左上角，右上角，左下角，右下角
	 * @param alpha 透明度
	 */
	public static int imageWaterMark(FileOutputStream fileOut,String path,String waterFilePath,int position,float alpha){
		
		int padding = 10;
		File imgFile = new File(path);
		File waterFile = new File(waterFilePath);
		Image src = null;
		Image waterMark = null;
		try {
			src = javax.imageio.ImageIO.read(imgFile);
			waterMark = javax.imageio.ImageIO.read(waterFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		int srcWidth = src.getWidth(null);
		int srcHeight = src.getHeight(null);
		
		int waterMarkWidth = waterMark.getWidth(null);
		int waterMarkHeight = waterMark.getHeight(null);
		
		if(srcWidth < waterMarkWidth + padding || srcHeight < waterMarkHeight + padding){
			return -1;
		}
		BufferedImage bufferedImage = new BufferedImage(srcWidth,srcHeight,BufferedImage.TYPE_INT_RGB);//图像缓冲区
		Graphics2D g = bufferedImage.createGraphics();
		g.drawImage(src, 0, 0, srcWidth, srcHeight, null);//绘制原始图像
		
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,alpha));//设置水印图片透明度
		if(position == 1){
			g.drawImage(waterMark, padding, padding, waterMarkWidth, waterMarkHeight , null);  //左上角
		}else if(position == 2){
			g.drawImage(waterMark, srcWidth - padding - waterMarkWidth, padding, waterMarkWidth, waterMarkHeight, null);  //右上角
		}else if(position == 3){
			g.drawImage(waterMark, padding, srcHeight - padding - waterMarkHeight, waterMarkWidth, waterMarkHeight, null);  //左下角
		}else if(position == 4){
			g.drawImage(waterMark, srcWidth - padding - waterMarkWidth, srcHeight - padding - waterMarkHeight, waterMarkWidth, waterMarkHeight, null);  //右下角
		}
		g.dispose();
		/*JPEGImageEncoder encoder = null;
		try {
			encoder = JPEGCodec.createJPEGEncoder(fileOut);
			encoder.encode(bufferedImage); // 近JPEG编码
			fileOut.close();
		} catch (Exception e) {
			e.printStackTrace();
		} */
		return 1;
	}
	
	
	/**
	 * 文字水印
	 * @return
	 */
	public static int textWaterMark(FileOutputStream out,File imgFile,String content,int position,float alpha){
		int padding = 10;
		Image src = null;
		try {
			src = javax.imageio.ImageIO.read(imgFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		int srcWidth = src.getWidth(null);
		int srcHeight = src.getHeight(null);
		
		if(srcWidth <  padding || srcHeight < padding){
			return -1;
		}
		BufferedImage bufferedImage = new BufferedImage(srcWidth,srcHeight,BufferedImage.TYPE_INT_RGB);//图像缓冲区
		Graphics2D g = bufferedImage.createGraphics();
		g.drawImage(src, 0, 0, srcWidth, srcHeight, null);//绘制原始图像
		g.setColor(Color.white);
		
		int fontSize = 22;
		g.setFont(new Font("楷体",Font.BOLD,fontSize));
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,alpha));//设置水印图片透明度
		if(position == 1){
			g.drawString(content, padding + fontSize, padding + fontSize); //左上角
		}else if(position == 2){
			g.drawString(content, srcWidth - padding - content.length()*fontSize, padding + fontSize);  //右上角
		}else if(position == 3){
			g.drawString(content, padding + fontSize,srcHeight - padding - fontSize);  //左下角
		}else if(position == 4){
			g.drawString(content, srcWidth - padding - content.length()*fontSize, srcHeight - padding - fontSize);  //右下角
		}
		g.dispose();
		/*JPEGImageEncoder encoder = null;
		try {
			encoder = JPEGCodec.createJPEGEncoder(out);
			encoder.encode(bufferedImage); // 近JPEG编码
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		return 1;
	}
	
	
	/*******
	 * 废弃方法
	 */

	/**
	 * 缩略图算法(以width为准缩放) 并生成缩略图
	 * @param file2 源文件
	 * @param url 上传到指定路径(格式：/xxx) eg: xx/upload  不能为空
	 * @param width
	 * @param filename 缩略图文件名 eg:small_20110111111111.jpg
	 * @throws Exception
	 *//*
	@Deprecated
	private int newMyPicBySize(File file2,String url,float width,String filename){
		String dir = null;
		if(url == null||url.trim().isEmpty()){
			return 0;
		}
		
		dir = servletContext.getRealPath("/") + url;
		File aimFile =  new File(dir.replace("\\", "/"));
		if(!aimFile.exists()){
			aimFile.mkdirs();
		}
		String filepath = dir + "\\" + filename;//缩略图文件路径
		filepath = filepath.replace("\\", "/");
		
		BufferedImage src = null;
		try {
			src = javax.imageio.ImageIO.read(file2);
		} catch (IOException e) {
			e.printStackTrace();
		}
		int new_w = 0;
		int new_h = 0; 
		float sx = 0f;
		@SuppressWarnings("unused")
		float sy = 0f;
		
		//将原图宽高比保存
		percentage = src.getWidth()+"*"+ src.getHeight() +"";
		
		if(width < src.getWidth()){
		  	sx = (float)width/src.getWidth();
		  	sy =(float)width/src.getHeight();
		}else{
			sx = 1.0f;
			sy = 1.0f;
		}
	  	float tempdouble;
//	  	if(sx>sy){
//	  		tempdouble = sy;
//	  	}
//	  	else {
//	  		tempdouble= sx;
//	  	}
	  	tempdouble = sx;
	  	new_w = Math.round(tempdouble*src.getWidth());
	  	new_h = Math.round(tempdouble*src.getHeight());
		
		BufferedImage tag = new BufferedImage(new_w, new_h,src.getType());	//创建流
		FileOutputStream newimage;
		if(imgType.equals("PNG")){
	  		Graphics2D g=tag.createGraphics();
	  		g.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
	  		g.drawRenderedImage(src,AffineTransform.getScaleInstance(sx,sx));
	  		g.dispose();
			try {
				newimage = new FileOutputStream(filepath);
				ImageIO.write(tag,imgType,newimage);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			tag.getGraphics().drawImage(src.getScaledInstance(new_w,new_h,Image.SCALE_SMOOTH), 0, 0, null);
//			tag.getGraphics().drawImage(src.getScaledInstance(new_w, new_h, Image.SCALE_AREA_AVERAGING), 0, 0, null); 
			try{
				newimage = new FileOutputStream(filepath); // 输出到文件流
				JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(newimage);
				encoder.encode(tag); // 近JPEG编码
				newimage.close();
			}catch(Exception e){
				e.printStackTrace();
				return 0;
			}
		}
		return 1;
	}
	
	*//**
	 * 缩略图算法(以height为准)，等比缩放原图长宽
	 * @param file2 源文件
	 * @param url 上传到指定路径(格式：/xxx) eg: kg/upload  不能为空
	 * @param filename 缩略图文件名 eg:small_20110111111111.jpg
	 * @param height
	 * @throws Exception
	 *//*
	@Deprecated
	private int newMyPicBySize(File file2,String url,String filename,float height){
		String dir = null;
		if(url == null){
			return 0;
		}
		
		dir = root + url;
		File aimFile =  new File(dir.replace("\\", "/"));
		if(!aimFile.exists()){
			aimFile.mkdirs();
		}
		String filepath = dir + "\\" + filename;//缩略图文件路径
		filepath = filepath.replace("\\", "/");
		
		BufferedImage src = null;
		try {
			src = javax.imageio.ImageIO.read(file2);
		} catch (IOException e) {
			e.printStackTrace();
		}
		int new_w = 0;
		int new_h = 0; 
		float sx = 0f;
		float sy = 0f;
		
		//将原图宽高比保存
		percentage = src.getWidth()+"*"+ src.getHeight() +"";
		
		if(height < src.getHeight()){
		  	sx = (float)height/src.getWidth();
		  	sy =(float)height/src.getHeight();
		}else{
			sx = 1.0f;
			sy = 1.0f;
		}
	  	float tempdouble;
	  	tempdouble = sx;
	  	new_w = Math.round(tempdouble*src.getWidth());
	  	new_h = Math.round(tempdouble*src.getHeight());
		
		BufferedImage tag = new BufferedImage(new_w, new_h,src.getType());	//创建流
		FileOutputStream newimage;
		if(imgType.equals("PNG")){
	  		Graphics2D g=tag.createGraphics();
	  		g.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
	  		g.drawRenderedImage(src,AffineTransform.getScaleInstance(sy,sy));
	  		g.dispose();
			try {
				newimage = new FileOutputStream(filepath);
				ImageIO.write(tag,imgType,newimage);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			tag.getGraphics().drawImage(src.getScaledInstance(new_w,new_h,Image.SCALE_SMOOTH), 0, 0, null);
			try{
				newimage = new FileOutputStream(filepath); // 输出到文件流
				JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(newimage);
				encoder.encode(tag); // 近JPEG编码
				newimage.close();
			}catch(Exception e){
				e.printStackTrace();
				return 0;
			}
		}
		return 1;
	}*/
	
	public static void main(String[] args) {
		try {
			newMyPicBySize(new File("F:\\aa.gif"), "F:\\", "bb.gif", 100, 100);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

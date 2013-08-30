package com.ajian.util.UtilLibrary.file;

public class FileType {

	/**
	 * @param i
	 * i=0 所有文件
	 * i=1 返回常见的视屏文件格式
	 * i=2 返回常见的音频文件格式
	 * i=3 返回常见的图片文件格式
	 * i=4 psd图片格式
	 * i=5  返回常见的文本文件格式   pdf文件和office系列文件
	 * i=6 返回常见的压缩文件格式
	 * @return
	 */
	public static String getFileType(int i){
		String type[] = new String[7];
		type[0] = "所有文件;*";
		type[1] = "视频文件;*.asx;*.asf;*.mpg;*.wmv;*.3gp;*.mp4;*.mov;*.avi;*.flv;*.wmv9;*.rm;*.rmvb";
		type[2] = "音频文件;*.mp3;*.wma;";
		type[3] = "图片文件;*.jpg;*.jpeg;*.gif;*.png;*.bmp;";
		type[4] = "psd文件;*.psd";
		type[5] = "文本文件;*.pdf;*.mdb;*.xls;*.doc;*.ppt;*.docx;*.docm;*.dotx;*.dotm;*.xlsx;*.xlsm;*.xltx;*.xltm;*.xlsb;*.xlam;*.pptx;*.pptm;*.ppsx;*.potx;*.potm;*.ppam;";
		type[6] = "压缩文件;*.zip;*.rar;*.jar";
		return type[i];
	}
}

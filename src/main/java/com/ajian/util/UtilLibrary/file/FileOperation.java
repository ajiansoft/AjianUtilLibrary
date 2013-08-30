package com.ajian.util.UtilLibrary.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import com.ajian.util.UtilLibrary.string.RandomNumber;



/**
 * 文件操作工具类
 * @author Ajian
 * @version Nov 27, 2011 10:17:37 AM
 */
public class FileOperation {
	/**
	 * 系统分隔符(windows下为'\\',UNIX为'/')
	 */
	protected static final Character System_Separator = File.separatorChar;
	/**
	 * Servlet上下文对象
	protected static final ServletContext servletContext = ServletActionContext.getServletContext();
	 */
	
	
	 /** 
	  * 创建文件夹(必须是上一级目录必须存在才能创建）
     * @param path  目录 eg: d:/5  或   d:\\4
     */
    public static void createDir(String path){
        File dir=new File(path);
        if(!dir.exists())
            dir.mkdir();
    }
    
	 /** 
	  * 创建文件夹（可以同时创建多级目录）
    * @param path  目录 eg: d:/51/4  或   d:\\41\\4
    */
   public static void createDirs(String path){
       File dir=new File(path);
       if(!dir.exists())
           dir.mkdirs();
   }
    
    /** 
     * 创建新文件
     * @param path 目录 eg: d:/4
     * @param filename 文件名 eg: 1234.doc
     * @throws IOException
     */
    public static void createFile(String path,String filename){
        File file=new File(path+System_Separator+filename);
        File dir=new File(path);
        if(!dir.exists())
            dir.mkdirs();
        if(!file.exists())
			try {
				file.createNewFile();
			} catch (IOException e) {
			}
    }

	/**
	 * 删除文件  
	 * @param path 文件的完整目录  eg: d:\\1.txt 或 d:/1.txt
	 */  
	public static void delFile(String path){   
	    File file=new File(path);   
	    if(file.exists()&&file.isFile())   
	        file.delete();   
	    file=null;
	}  
	
	/**
	 * 从服务器中删除文件
	 * @param url  eg:upload/aasdfasdf.jpg
	 * @return
	 */
	public static int deleteFileFromServer(String url){
//		String path = ServletActionContext.getServletContext().getRealPath("/").replace("\\", "/") + url.substring(0,url.lastIndexOf("/")+1);
		/*String path = ServletActionContext.getServletContext().getRealPath("/").replace("\\", "/");
		delFile(path + url);*/
		return 1;
	}
	
	 /**
	  * 递归删除文件夹
     * @param path eg: d:/1
     */
    public static void delDir(String path){
        File dir=new File(path);
        if(dir.exists()){
            File[] tmp=dir.listFiles();
            for(int i=0;i<tmp.length;i++){
                if(tmp[i].isDirectory()){
                    delDir(path+System_Separator+tmp[i].getName());
                }
                else{
                    tmp[i].delete();
                }
            }
            dir.delete();
        }
    }
    
    /**
     * 以文件流的方式复制文件  
     * @param src 文件源目录  
     * @param dest 文件目的目录  
     * @throws IOException    
     */  
    public static void copyFileByStream(String src,String dest){   
        FileInputStream in;
		try {
			in = new FileInputStream(src);
        File file=new File(dest);   
        if(!file.exists())   
            file.createNewFile();   
	        FileOutputStream out=new FileOutputStream(file);   
	        int c;   
	        byte buffer[]=new byte[1024];   
	        while((c=in.read(buffer))!=-1){   
	            for(int i=0;i<c;i++)   
	                out.write(buffer[i]);           
	        }   
	        in.close();   
	        out.close();   
		} catch (Exception e) {
			e.printStackTrace();
		}   
    }
    
    /**
     * 以NIO的方式复制文件  
     * @param src 文件源目录  
     * @param dest 文件目的目录 
     * @author Ajian
     * @version Dec 24, 2012 2:23:13 PM
     */
    public static void copyFileByNIO(String src,String dest){
	    try {   
	        File srcFile = new File(src);   
	        File distFile = new File(dest);   
	        if (!distFile.exists())distFile.createNewFile();   
	
	        FileInputStream fin = new FileInputStream(srcFile);   
	        FileOutputStream fout = new FileOutputStream(distFile);   
	        FileChannel inChannel = fin.getChannel();   
	        FileChannel outChannel = fout.getChannel();   
	        int ByteBufferSize = 1024 * 100;   
	        ByteBuffer buff = ByteBuffer.allocate(ByteBufferSize);   
	
	        while (inChannel.read(buff) > 0) {   
	            buff.flip();   
	            if (inChannel.position() == inChannel.size()) {// 判断是不是最后一段数据   
	                int lastRead = (int) (inChannel.size() % ByteBufferSize);   
	                byte[] bytes = new byte[lastRead];   
	                buff.get(bytes, 0, lastRead);   
	                outChannel.write(ByteBuffer.wrap(bytes));   
	                buff.clear();   
	            } else {   
	                outChannel.write(buff);   
	                buff.clear();   
	            }   
	        }// 这个使用FileChannel 自带的复制   
	        //outChannel.transferFrom(inChannel, 0, inChannel.size());   
	        outChannel.close();   
	        inChannel.close();   
	        fin.close();   
	        fout.close();   
	    } catch (Exception e) {   
	        e.printStackTrace();   
	    }   
    }
	
	/**
	 * 把已知内容（content）写入文件 ,写入后会覆盖文件中已有的内容
	 * @param path 文件路径 eg:  d:/1.txt
	 * @param content 即将写入文件的内容
	 */
    public static void PrintStreamDemo(String path,String content){   
        try {   
            FileOutputStream out=new FileOutputStream(path);   
            PrintStream p=new PrintStream(out);   
            p.println(content);   
        } catch (FileNotFoundException e){   
        	e.printStackTrace();
        }   
    }  
    
    /**
     * 把已知内容（content）写入文件 ,写入后 不会 覆盖文件中已有的内容
     * @param path
     * @param content
     * @throws IOException
     */
    public static void StringBufferDemo(String path,String content) throws IOException{   
        File file=new File(path);   
        if(!file.exists())   
            file.createNewFile();   
        FileOutputStream out=new FileOutputStream(file,true);               
        out.write(content.toString().getBytes("utf-8"));
        out.close();   
    } 
    
    /**
     * 检查文件是否存在
     * @param path
     * @param name
     * @return 1 存在 0 不存在
     */
    public static int check(String path,String name){
    	File file = new File(path + System_Separator + name);
    	if(file.exists()){
    		return 1;
    	}else{
    		return 0;
    	}
    }
    
    /** 
     * 文件重命名  
     * @param path 文件目录  
     * @param oldname  原来的文件名  
     * @param newname 新文件名  
     * @return 0 重命名成功 1 新命名的文件已存在  2：新旧文件名相同 
     */  
    public static int renameFile(String path,String oldname,String newname,boolean override){   
        if(!oldname.equals(newname)){//新的文件名和以前文件名不同时,才有必要进行重命名   
            File oldfile=new File((path+System_Separator+oldname).replace("\\", "/"));   
            File newfile=new File((path+System_Separator+newname).replace("\\", "/"));   
            if(override){
            	oldfile.renameTo(newfile);
            	return 0;
            }else if(newfile.exists())//若在该目录下已经有一个文件和新文件名相同，则不允许重命名   
                return 1;  
            else{   
            	oldfile.renameTo(newfile);
                return 0;
            }
        }else{
        	return 2;
        }
    } 
    
    /** 
     * 转移文件目录
     * @param filename 文件名
     * @param oldpath 旧目录
     * @param newpath 新目录
     * @param cover 若新目录下存在和转移文件具有相同文件名的文件时，是否覆盖新目录下文件，cover=true将会覆盖原文件，否则不操作
     */
    public static void changeDirectory(String filename,String oldpath,String newpath,boolean cover){
        if(!oldpath.equals(newpath)){
            File oldfile=new File(oldpath+System_Separator+filename);
            File newfile=new File(newpath+System_Separator+filename);
            if(newfile.exists()){//若在待转移目录下，已经存在待转移文件
                if(cover)//覆盖
                    oldfile.renameTo(newfile);
                //else
                    //System.out.println("在新目录下已经存在："+filename);
            }
            else{
                oldfile.renameTo(newfile);
            }
        }       
    }
    
    /** 
     * 读文件 读取出来的文件重新排版，元文本每行之间读取出来后被几个空格隔开
     * @param path eg: "d:/1.txt"
     * @return
     * @throws IOException
     */
    public static String BufferedReaderDemo(String path) throws IOException{
        File file=new File(path);
        if(!file.exists()||file.isDirectory())
            throw new FileNotFoundException();
        BufferedReader br=new BufferedReader(new FileReader(file));
        String temp=null;
        StringBuffer sb=new StringBuffer();
        temp=br.readLine();
        while(temp!=null){
            sb.append(temp).append(" ");
            temp=br.readLine();
        }
        return sb.toString();
    }
    
    /** 
     * 读文件 按照文本原来的格式，例如分行，分段；
     * @param path
     * @return
     * @throws IOException
     */
    public static String FileInputStreamDemo(String path) throws IOException{
        File file=new File(path);
        if(!file.exists()||file.isDirectory())
            throw new FileNotFoundException();
        FileInputStream fis=new FileInputStream(file);
        byte[] buf = new byte[1024];
        StringBuffer sb=new StringBuffer();
        while((fis.read(buf))!=-1){
            sb.append(new String(buf));    
            buf=new byte[1024];//重新生成，避免和上次读取的数据重复
        }
        return sb.toString();
    }
    
    /**
	 * 获取扩展名，默认为.jpg
	 * @param fileName
	 * @return
	 */
	public static String getExtention(String MyfileFileName)  {
	        int pos = MyfileFileName.lastIndexOf(".");
	        if(pos==-1)return ".jpg";
	        return MyfileFileName.substring(pos).replace("_", "").trim();
	} 
	
	/**
	 * 判断URL是否是以系统路径分隔符结束
	 * @param url
	 * @return
	 * @author Ajian
	 * @version Nov 27, 2012 11:20:50 AM
	 */
	public static boolean endCharIsSystemSeparator(String url){
		boolean i = url.endsWith(String.valueOf(System_Separator));
		url=null;return i;
	}
	

	/**
	 * 生成新名字
	 * （当前时间 + 0~100） 之间的一个随机数 eg:2010091401010189+文件后缀
	 * @param fileFileName
	 * @return
	 */
	public static String getNewFileName(String fileFileName){
		return RandomNumber.currentDate().concat(getExtention(fileFileName));
	}
	
}

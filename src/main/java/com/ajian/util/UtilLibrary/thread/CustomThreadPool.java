package com.ajian.util.UtilLibrary.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * 自定义系统线程池
 * @author Ajian
 * @version Nov 14, 2012 1:07:42 PM
 */
public class CustomThreadPool{
	
	private long keepAliveTime = 10L;//单位：秒
	
	private ExecutorService threadPool;
	
	private CustomThreadPool(){}
	private static class HelpSystemThreadPoolHolder{
		private static CustomThreadPool instance = new CustomThreadPool();
	}
	/**
	 * 获取对象
	 * @return
	 * @author Ajian
	 * @version Nov 14, 2012 1:38:36 PM
	 */
	public static CustomThreadPool getInstance() {
		return HelpSystemThreadPoolHolder.instance;
	}
	
	
	/**
	 * 得到线程池中的一个线程
	 * @return
	 * @author Ajian
	 * @version Nov 14, 2012 3:59:55 PM
	 */
	public ExecutorService createThreadPool(){
		synchronized (this) {
			if(threadPool==null){
				this.threadPool = new ThreadPoolExecutor(0, Integer.MAX_VALUE,keepAliveTime, TimeUnit.SECONDS,
						new SynchronousQueue<Runnable>(),Executors.defaultThreadFactory());
			}
		}
		return threadPool;
	}
}

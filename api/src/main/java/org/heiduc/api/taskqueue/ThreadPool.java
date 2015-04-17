package org.heiduc.api.taskqueue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPool {
	
	private static CustomThreadPoolExecutor instance = null;
	
	private ThreadPool(){}
	
	public synchronized static CustomThreadPoolExecutor getInstance(){
		if(instance == null){
//			System.out.println("ThreadPool init................");
			BlockingQueue<Runnable> blockingQueue = new LinkedBlockingQueue<Runnable>(50);
	        CustomThreadPoolExecutor executor = new CustomThreadPoolExecutor(10, 20, 3, TimeUnit.SECONDS, blockingQueue);
	        executor.setRejectedExecutionHandler(new RejectedExecutionHandler() {
	            @Override
	            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor)
	            {
	                System.out.println("拒绝策略");
	            }
	        });
	        //Let start all core threads initially
	        executor.prestartAllCoreThreads();
	        instance = executor;
		}
		return instance;
	}

}

package org.heiduc.api.taskqueue;

import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class QueueObserver implements Observer {

	@Override
	public void update(Observable o, Object arg) {
//		System.out.println("============update in============");
//		System.out.println("已运行的线程数"+Thread.activeCount());
		
		TaskOptions task = (TaskOptions)arg;
//		System.out.println("taskNmae is "+task.getTaskName());
//		System.out.println("开始初始化..................");
		CustomThreadPoolExecutor executor = ThreadPool.getInstance();
//		System.out.println("调用线程执行");
        executor.execute(new TaskThread(task));
//        System.out.println("线程池运行的线程数"+executor.getActiveCount());
//		System.out.println("============update out============");
	}

}

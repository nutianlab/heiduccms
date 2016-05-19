package org.heiduc.api.taskqueue;

import java.util.Observable;
import java.util.Observer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QueueObserver implements Observer {
	
	private  static final Logger logger = LoggerFactory.getLogger(QueueObserver.class);

	@Override
	public void update(Observable o, Object arg) {
		logger.info("已运行的线程数"+Thread.activeCount());
		TaskOptions task = (TaskOptions)arg;
		logger.info("taskNmae is "+task.getTaskName());
		logger.info("开始初始化..................");
		CustomThreadPoolExecutor executor = ThreadPool.getInstance();
		logger.info("调用线程执行");
        executor.execute(new TaskThread(task));
		logger.info("线程池运行的线程数"+executor.getActiveCount());
	}

}

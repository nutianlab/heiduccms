package org.heiduc.api.taskqueue;

import java.util.Observable;
import java.util.Observer;

public class QueueObserver implements Observer {
	
//	private  static final Logger logger = LoggerFactory.getLogger(QueueObserver.class);

	@Override
	public void update(Observable o, Object arg) {
		TaskOptions task = (TaskOptions)arg;
		CustomThreadPoolExecutor executor = ThreadPool.getInstance();
        executor.execute(new TaskThread(task));
	}

}

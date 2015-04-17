package org.heiduc.api.taskqueue;

import java.util.Observable;

public class QueueImpl extends Observable implements Queue {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4590193449933481334L;
	
	private final String queueName;
	
	private String defaultUrl() {
		return "/_ah/queue/" + this.queueName;
	}
	
	@Override
	public String getQueueName() {
		return this.queueName;
	}

	public QueueImpl(String queueName) {
		this.queueName = queueName;
		this.addObserver(new QueueObserver());
	}

	@Override
	public TaskHandle add(TaskOptions param) {
		
		TaskOptions task = new TaskOptions(param);
		task.taskName("task_1");
		TaskHandle handle = new TaskHandle(task, this.queueName);
		
		super.setChanged();
		super.notifyObservers(task);
		
		return handle;
	}

	

	

}

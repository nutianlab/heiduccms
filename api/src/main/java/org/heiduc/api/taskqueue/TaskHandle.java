package org.heiduc.api.taskqueue;

import java.io.Serializable;

public class TaskHandle implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 780425654049798128L;
	
	private String taskName;
	private String queueName;
	private long etaUsec;
	private long etaMillis;
	private Integer retryCount;
	private TaskOptions options;
	
	TaskHandle(TaskOptions options, String queueName, Integer retryCount)
	  {
	    validateTaskName(options.getTaskName());
	    this.queueName = queueName;
	    this.retryCount = retryCount;
	    this.taskName = null;
	    this.etaMillis = 0L;
	    this.options = new TaskOptions(options);
	    setEtaUsecFromOptions(this.options);
	  }
	
	public TaskHandle(TaskOptions param, String queueName) {
		this(param, queueName, Integer.valueOf(0));
	}
	
	

	public static void validateTaskName(String taskName) {
		if ((taskName == null) || (taskName.length() == 0) || (!QueueConstants.TASK_NAME_PATTERN.matcher(taskName).matches()))
	    {
	      throw new IllegalArgumentException("Task name does not match expression " + QueueConstants.TASK_NAME_REGEX + "; given taskname: '" + taskName + "'");
	    }
	}
	
	private void setEtaUsecFromOptions(TaskOptions options) {
	    if ((options != null) && (options.getEtaMillis() != null))
	      this.etaUsec = (options.getEtaMillis().longValue() * 1000L);
	    else
	      this.etaUsec = 0L;
	  }

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getQueueName() {
		return queueName;
	}

	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}

	public long getEtaUsec() {
		return etaUsec;
	}

	public void setEtaUsec(long etaUsec) {
		this.etaUsec = etaUsec;
	}

	public long getEtaMillis() {
		return etaMillis;
	}

	public void setEtaMillis(long etaMillis) {
		this.etaMillis = etaMillis;
	}

	public Integer getRetryCount() {
		return retryCount;
	}

	public void setRetryCount(Integer retryCount) {
		this.retryCount = retryCount;
	}

	public TaskOptions getOptions() {
		return options;
	}

	public void setOptions(TaskOptions options) {
		this.options = options;
	}
	
	
	
}

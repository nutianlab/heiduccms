package org.heiduc.api.taskqueue;

public interface Queue {

	String DEFAULT_QUEUE = "default";

	String DEFAULT_QUEUE_PATH = "/_ah/queue";

	String getQueueName();

	TaskHandle add(TaskOptions param);

}



package com.heiduc.business.imex.task;

public class TaskTimeoutException extends Exception {
	
	public TaskTimeoutException() {
		super("Task timeout exception");
	}

	public TaskTimeoutException(String msg) {
		super(msg);
	}
}

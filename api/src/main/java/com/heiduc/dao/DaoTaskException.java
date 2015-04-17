

package com.heiduc.dao;

public class DaoTaskException extends Exception {
	
	public DaoTaskException() {
		super("Task exception");
	}

	public DaoTaskException(String msg) {
		super(msg);
	}
}

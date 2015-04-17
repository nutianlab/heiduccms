

package com.heiduc.business.impl.imex.task;

import com.heiduc.dao.DaoTaskException;

public class DaoTaskTimeoutException extends DaoTaskException {
	
	public DaoTaskTimeoutException() {
		super("Task finished");
	}
}



package com.heiduc.common;

/**
 * @author Alexander Oleynik
 */
public class AccessDeniedException extends Exception {
	
	public AccessDeniedException() {
		super("Access denied");
	}

	public AccessDeniedException(String msg) {
		super(msg);
	}
}



package com.heiduc.update;

/**
 * @author Alexander Oleynik
 */
public interface UpdateTask {

	String getFromVersion();

	String getToVersion();
	
	String update() throws UpdateException;
	
}

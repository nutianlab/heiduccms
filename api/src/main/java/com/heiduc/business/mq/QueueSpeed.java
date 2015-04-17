

package com.heiduc.business.mq;

/**
 * 
 * @author Alexander Oleynik
 *
 */
public enum QueueSpeed {

	/**
	 * Task queue rate 10/sec.
	 */
	HIGH,
	
	/**
	 * Task queue rate 1/sec.
	 */
	MEDIUM,

	/**
	 * Task queue rate once in 10sec.
	 */
	LOW;
	
}

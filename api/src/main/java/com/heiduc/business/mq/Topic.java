

package com.heiduc.business.mq;

/**
 * 
 * @author Alexander Oleynik
 *
 */
public enum Topic {
	
	/**
	 *  SimpleMessage: message -> user email
	 */
	LOGIN,

	/**
	 *  SimpleMessage: message -> file url
	 */
	FILE_CHANGED,

	/**
	 *  ExportMessage
	 */
	EXPORT,
	
	/**
	 *  ImportMessage
	 */
	IMPORT,
	
	/**
	 * SimpleMessage
	 */
	IMPORT_FILE,
	
	/**
	 * SimpleMessage
	 */
	IMPORT_FOLDER,

	/**
	 *  PageMessage
	 */
	PAGES_CHANGED,

	/**
	 *  PageMessage
	 */
	PAGES_DELETED,

	PAGE_CACHE_CLEAR,
	
	PAGE_PUBLISH_CRON,
	
	/**
	 * Full search reindex. Receives IndexMessage.
	 */
	REINDEX,
	
	/**
	 * SimpleMessage: message -> entity name
	 */
	ENTITY_REMOVE;
	
}

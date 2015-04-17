

package com.heiduc.business.mq.message;

import com.heiduc.business.mq.AbstractMessage;
import com.heiduc.business.mq.QueueSpeed;
import com.heiduc.business.mq.Topic;

/**
 * 
 * @author Alexander Oleynik
 *
 */
public class ImportMessage extends AbstractMessage {

	public static class Builder {
		private String filename;
		private int start;
		private String currentFile;
		private int fileCounter;
		
		public ImportMessage create() {
			return new ImportMessage(filename, start, currentFile, fileCounter);
		}
		
		public Builder setFilename(String filename) {
			this.filename = filename;
			return this;
		}
		
		public Builder setStart(int start) {
			this.start = start;
			return this;
		}
		
		public Builder setCurrentFile(String currentFile) {
			this.currentFile = currentFile;
			return this;
		}
		
		public Builder setFileCounter(int fileCounter) {
			this.fileCounter = fileCounter;
			return this;
		}
		
	}
	
	private String filename;
	private int start;
	private String currentFile;
	private int fileCounter;

	public ImportMessage(String filename, int start, String currentFile,
			int fileCounter) {
		super();
		setTopic(Topic.IMPORT.name());
		setSpeed(QueueSpeed.LOW);
		this.filename = filename;
		this.start = start;
		this.currentFile = currentFile;
		this.fileCounter = fileCounter;
	}

	public String getFilename() {
		return filename;
	}

	public int getStart() {
		return start;
	}

	public String getCurrentFile() {
		return currentFile;
	}

	public int getFileCounter() {
		return fileCounter;
	}
	
}

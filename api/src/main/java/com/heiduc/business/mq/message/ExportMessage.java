

package com.heiduc.business.mq.message;

import java.util.List;

import com.heiduc.business.mq.AbstractMessage;
import com.heiduc.business.mq.QueueSpeed;
import com.heiduc.business.mq.Topic;

/**
 * 
 * @author Alexander Oleynik
 *
 */
public class ExportMessage extends AbstractMessage {

	public static class Builder {
		
		private String filename;
		private String currentFile;
		private int fileCounter;
		private String exportType;
		private List<Long> ids;
		private List<Long> structureIds;
		private Long folderId;

		public ExportMessage create() {
			return new ExportMessage(filename, currentFile, fileCounter,
					exportType, ids, folderId, structureIds);
		}

		public Builder setFilename(String filename) {
			this.filename = filename;
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

		public Builder setExportType(String exportType) {
			this.exportType = exportType;
			return this;
		}

		public Builder setIds(List<Long> ids) {
			this.ids = ids;
			return this;
		}

		public Builder setStructureIds(List<Long> ids) {
			this.structureIds = ids;
			return this;
		}

		public Builder setFolderId(Long folderId) {
			this.folderId = folderId;
			return this;
		}
		
	}
	
	
	private String filename;
	private String currentFile;
	private int fileCounter;
	private String exportType;
	private List<Long> ids;
	private List<Long> structureIds;
	private Long folderId;
	
	private ExportMessage(String filename, String currentFile, int fileCounter,
			String exportType, List<Long> ids, Long folderId, 
			List<Long> structureIds) {
		super();
		setTopic(Topic.EXPORT.name());
		setSpeed(QueueSpeed.LOW);
		this.filename = filename;
		this.currentFile = currentFile;
		this.fileCounter = fileCounter;
		this.exportType = exportType;
		this.ids = ids;
		this.folderId = folderId;
		this.structureIds = structureIds;
	}

	public String getFilename() {
		return filename;
	}

	public String getCurrentFile() {
		return currentFile;
	}

	public int getFileCounter() {
		return fileCounter;
	}

	public String getExportType() {
		return exportType;
	}

	public List<Long> getIds() {
		return ids;
	}

	public Long getFolderId() {
		return folderId;
	}

	public List<Long> getStructureIds() {
		return structureIds;
	}
	
}

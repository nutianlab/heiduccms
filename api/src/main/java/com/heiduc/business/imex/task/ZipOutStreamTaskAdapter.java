

package com.heiduc.business.imex.task;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public interface ZipOutStreamTaskAdapter {

	ZipOutputStream getOutStream();

	void setOutStream(ZipOutputStream out);

	String getStartFile();

	void setStartFile(String startFile);

	String getCurrentFile();

	void setCurrentFile(String currentFile);

	boolean isSkip(String filePath);

	void putNextEntry(ZipEntry entry) throws IOException, TaskTimeoutException;

	void closeEntry() throws IOException;

	void write(byte[] data) throws IOException;

	ByteArrayOutputStream getOutData();

	void setOutData(ByteArrayOutputStream outData);
	
	int getFileCounter();

	void setFileCounter(int fileCounter);
	
	void nextFile();

}
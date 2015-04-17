

package com.heiduc.business.imex;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.zip.ZipEntry;

import org.dom4j.DocumentException;

import com.heiduc.business.imex.task.TaskTimeoutException;
import com.heiduc.business.imex.task.ZipOutStreamTaskAdapter;
import com.heiduc.dao.DaoTaskException;

public interface SiteExporter {

	boolean isSiteContent(final ZipEntry entry)
			throws UnsupportedEncodingException;

	void exportSite(final ZipOutStreamTaskAdapter out) 
			throws IOException, TaskTimeoutException;
	
	void readSiteContent(final ZipEntry entry, final String xml)
			throws DocumentException, DaoTaskException;
	
	boolean importSystemFile(ZipEntry entry, ByteArrayOutputStream data) 
			throws DocumentException, DaoTaskException, 
			UnsupportedEncodingException;

	boolean importSystemFile(String name, String xml) 
			throws DocumentException, DaoTaskException, 
			UnsupportedEncodingException;

}



package com.heiduc.business.imex;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.zip.ZipEntry;

import org.dom4j.DocumentException;

import com.heiduc.business.decorators.TreeItemDecorator;
import com.heiduc.business.imex.task.TaskTimeoutException;
import com.heiduc.business.imex.task.ZipOutStreamTaskAdapter;
import com.heiduc.dao.DaoTaskException;
import com.heiduc.entity.FolderEntity;

public interface ResourceExporter {

	String getFolderSystemFile(FolderEntity folder);

	/**
	 * Add folder and _folder.xml file to zip archive.
	 * @param out - zip output stream
	 * @param folder - folder 
	 * @param zipPath - folder path under which resources will be placed to zip. 
	 * 	             Should not start with / symbol and should end with / symbol.
	 * @throws IOException
	 */
	void addFolder(final ZipOutStreamTaskAdapter out, 
			final FolderEntity folder, final String zipPath) 
			throws IOException, TaskTimeoutException;
	
	/**
	 * Add files from resource folder to zip archive.
	 * @param out - zip output stream
	 * @param folder - folder tree item
	 * @param zipPath - folder path under which resources will be placed to zip. 
	 * 	             Should not start with / symbol and should end with / symbol.
	 * @throws IOException
	 */
	void addResourcesFromFolder(final ZipOutStreamTaskAdapter out, 
			final TreeItemDecorator<FolderEntity> folder, final String zipPath) 
			throws IOException, TaskTimeoutException;

	/**
	 * Add files from resource folder to zip archive.
	 * @param out - zip output stream
	 * @param folder - folder tree item
	 * @param zipPath - folder path under which resources will be placed to zip. 
	 * 	             Should not start with / symbol and should end with / symbol.
	 * @throws IOException
	 */
	void addResourcesFromPage(final ZipOutStreamTaskAdapter out, 
			final String pageURL, final String zipPath) 
			throws IOException, TaskTimeoutException;
	
	String importResourceFile(final ZipEntry entry, byte[] data)
			throws UnsupportedEncodingException, DaoTaskException;
	
	String importResourceFile(String name, byte[] data)
			throws UnsupportedEncodingException, DaoTaskException;

	/**
	 * Read and import data from _folder.xml file.
	 * @param folderPath - folder path.
	 * @param xml - _folder.xml file.
	 * @throws DocumentException 
	 * @throws DaoTaskException 
	 */
	void readFolderFile(String folderPath, String xml) 
			throws DocumentException, DaoTaskException;
	
}



package com.heiduc.business;

import java.io.IOException;
import java.util.List;
import java.util.zip.ZipInputStream;

import org.dom4j.DocumentException;

import com.heiduc.business.imex.ExporterFactory;
import com.heiduc.business.imex.task.DaoTaskAdapter;
import com.heiduc.business.imex.task.TaskTimeoutException;
import com.heiduc.business.imex.task.ZipOutStreamTaskAdapter;
import com.heiduc.common.RequestTimeoutException;
import com.heiduc.dao.DaoTaskException;
import com.heiduc.entity.FolderEntity;
import com.heiduc.entity.StructureEntity;
import com.heiduc.entity.TemplateEntity;

public interface ImportExportBusiness {

	ExporterFactory getExporterFactory();

	/**
	 * Create export file with selected templates. Inside task execution.
	 * @param list - selected themes.
	 * @return zip file as byte array
	 * @throws IOException
	 */
	void createTemplateExportFile(final ZipOutStreamTaskAdapter zip, 
			final List<TemplateEntity> list, 
			final List<StructureEntity> structures) throws IOException, 
			TaskTimeoutException;

	/**
	 * Create export file for whole site. Excluding unconnected resources.
	 * Inside task execution.
	 * @return zip file as byte array
	 * @throws IOException
	 */
	void createSiteExportFile(final ZipOutStreamTaskAdapter zip) throws IOException,
			TaskTimeoutException;

	/**
	 * Create export file for whole site. Including unconnected resources.
	 * Inside task execution.
	 * @return zip file as byte array
	 * @throws IOException
	 */
	void createFullExportFile(final ZipOutStreamTaskAdapter zip) throws IOException,
			TaskTimeoutException;

	/**
	 * Import site data from zip file.
	 * @return list of imported resources.
	 * @throws DaoTaskException 
	 */
	void importZip(ZipInputStream in) throws IOException,
		DocumentException, DaoTaskException;

	/**
	 * Import site data from ho file. Format 2.0
	 * @return list of imported resources.
	 * @throws DaoTaskException 
	 */
	void importZip2(ZipInputStream in) throws IOException,
		DocumentException, DaoTaskException;

	/**
	 * Create export file for folder with files and subfolders.
	 * Inside task execution.
	 * @param folder - folder to export.
	 * @return zip file as byte array
	 * @throws IOException
	 */
	void createExportFile(final ZipOutStreamTaskAdapter zip, 
			final FolderEntity folder) throws IOException, TaskTimeoutException;

	/**
	 * Create resources export file for all folders except /page, /theme, /tmp.
	 * Inside task execution.
	 * @param folder - folder to export.
	 * @return zip file as byte array
	 * @throws IOException
	 */
	void createResourcesExportFile(final ZipOutStreamTaskAdapter zip) 
			throws IOException, TaskTimeoutException;
	
	DaoTaskAdapter getDaoTaskAdapter();
	void setDaoTaskAdapter(DaoTaskAdapter daoTaskAdapter);
	
	void importUnzip(final ZipInputStream in, String currentFile) 
		throws IOException, RequestTimeoutException;
}

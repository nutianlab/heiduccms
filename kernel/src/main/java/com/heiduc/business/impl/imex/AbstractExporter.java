

package com.heiduc.business.impl.imex;

import java.io.IOException;
import java.util.zip.ZipEntry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.heiduc.business.Business;
import com.heiduc.business.imex.ExporterFactory;
import com.heiduc.business.imex.task.DaoTaskAdapter;
import com.heiduc.business.imex.task.TaskTimeoutException;
import com.heiduc.business.imex.task.ZipOutStreamTaskAdapter;
import com.heiduc.dao.Dao;

public abstract class AbstractExporter {

	protected static final Log logger = LogFactory.getLog(AbstractExporter.class);

	public static void saveFile(final ZipOutStreamTaskAdapter out, String name, 
			String content) throws IOException, TaskTimeoutException {
		out.putNextEntry(new ZipEntry(name));
		out.write(content.getBytes("UTF-8"));
		out.closeEntry();
		out.nextFile();
	}

	private ExporterFactory exporterFactory;
	
	public AbstractExporter(ExporterFactory factory) {
		exporterFactory = factory;
	}

	public Dao getDao() {
		return getBusiness().getDao();
	}

	public Business getBusiness() {
		return getExporterFactory().getBusiness(); 
	}
	
	public DaoTaskAdapter getDaoTaskAdapter() {
		return getExporterFactory().getDaoTaskAdapter();
	}
	
	public ExporterFactory getExporterFactory() {
		return exporterFactory;
	}
	
}

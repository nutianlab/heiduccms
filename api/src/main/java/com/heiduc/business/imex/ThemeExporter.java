

package com.heiduc.business.imex;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.zip.ZipEntry;

import org.dom4j.DocumentException;

import com.heiduc.business.imex.task.TaskTimeoutException;
import com.heiduc.business.imex.task.ZipOutStreamTaskAdapter;
import com.heiduc.dao.DaoTaskException;
import com.heiduc.entity.TemplateEntity;

public interface ThemeExporter {

	static final String THEME_FOLDER = "theme/";
	
	String createThemeExportXML(final TemplateEntity theme);

	void exportThemes(final ZipOutStreamTaskAdapter out, 
			final List<TemplateEntity> themes) throws IOException,
			TaskTimeoutException;
	
	void exportTheme(final ZipOutStreamTaskAdapter out, 
			final TemplateEntity theme) throws IOException, 
			TaskTimeoutException;
	
	boolean isThemeDescription(final ZipEntry entry)
			throws UnsupportedEncodingException;

	void createThemeByDescription(final ZipEntry entry, String xml)
			throws UnsupportedEncodingException, DocumentException, 
			DaoTaskException;
	
	boolean isThemeContent(final ZipEntry entry)
			throws UnsupportedEncodingException;
	
	void createThemeByContent(final ZipEntry entry, final String content) 
			throws UnsupportedEncodingException, DocumentException, 
			DaoTaskException;

	/**
	 * Read and import data from _template.xml file.
	 * @param path - path to _temlate.xml file.
	 * @param xml - _template.xml content.
	 * @return true if successfully imported.
	 * @throws DocumentException
	 * @throws DaoTaskException 
	 */
	boolean readTemplateFile(String path, String xml) 
			throws DocumentException, DaoTaskException;
}



package com.heiduc.business.impl.mq.subscriber;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.exception.ExceptionUtils;

import com.heiduc.business.ImportExportBusiness;
import com.heiduc.business.imex.task.TaskTimeoutException;
import com.heiduc.business.imex.task.ZipOutStreamTaskAdapter;
import com.heiduc.business.impl.imex.task.ZipOutStreamTaskAdapterImpl;
import com.heiduc.business.impl.mq.AbstractSubscriber;
import com.heiduc.business.mq.Message;
import com.heiduc.business.mq.message.ExportMessage;
import com.heiduc.business.mq.message.ExportMessage.Builder;
import com.heiduc.common.HeiducContext;
import com.heiduc.entity.FolderEntity;
import com.heiduc.entity.StructureEntity;
import com.heiduc.entity.TemplateEntity;
import com.heiduc.entity.helper.UserHelper;

/**
 * In 9min task exports data to file located in /tmp folder with name stored in
 * request parameters. Parameters: 
 * filename - file for export located in /tmp folder. 
 * exportType - type of export. 
 * currentFile - file to export.
 * 
 * @author Alexander Oleynik
 */
public class ExportTaskSubscriber extends AbstractSubscriber {

	public final static String TYPE_PARAM_THEME = "theme";
	public final static String TYPE_PARAM_FOLDER = "folder";
	public final static String TYPE_PARAM_FULL = "full";
	public final static String TYPE_PARAM_SITE = "site";
	public final static String TYPE_PARAM_RESOURCES = "resources";

	public void onMessage(Message message) {
		ExportMessage msg = (ExportMessage)message;
		HeiducContext.getInstance().setUser(UserHelper.ADMIN);
		ZipOutStreamTaskAdapter zipOutStreamTaskAdapter = 
			new ZipOutStreamTaskAdapterImpl(getBusiness());
		if (msg.getCurrentFile() == null) {
			removeExportFile(msg.getFilename());
			getBusiness().getSystemService().getCache().remove(
					msg.getFilename());
		}
		zipOutStreamTaskAdapter.setFileCounter(Integer.valueOf(
				msg.getFileCounter()));
		try {
			openStream(zipOutStreamTaskAdapter, msg.getFilename());
			zipOutStreamTaskAdapter.setStartFile(msg.getCurrentFile());
			logger.info("Export " + msg.getExportType() + " " 
					+ msg.getCurrentFile() + " " + msg.getFileCounter());
	        if (msg.getExportType().equals(TYPE_PARAM_THEME)) {
	        	List<TemplateEntity> templates = getDao().getTemplateDao()
	        			.getById(msg.getIds());
	        	
	        	List<StructureEntity> structures = getDao().getStructureDao()
	        		.getById(msg.getStructureIds());
	        	
	        	getImportExportBusiness().createTemplateExportFile(
	        			zipOutStreamTaskAdapter, templates, structures);
	        }
	        if (msg.getExportType().equals(TYPE_PARAM_FOLDER)) {
	        	FolderEntity folder = getDao().getFolderDao().getById(
	        			msg.getFolderId());
	        	getImportExportBusiness().createExportFile(
	        			zipOutStreamTaskAdapter, folder);
	        }
	        if (msg.getExportType().equals(TYPE_PARAM_SITE)) {
	    		getImportExportBusiness().createSiteExportFile(
	    				zipOutStreamTaskAdapter);
	        }
	        if (msg.getExportType().equals(TYPE_PARAM_FULL)) {
	    		getImportExportBusiness().createFullExportFile(
	    				zipOutStreamTaskAdapter);
	        }
	        if (msg.getExportType().equals(TYPE_PARAM_RESOURCES)) {
	        	getImportExportBusiness().createResourcesExportFile(
	        			zipOutStreamTaskAdapter);
	        }
			saveZip(zipOutStreamTaskAdapter, msg.getFilename(), true);
    		getBusiness().getFileBusiness().saveFile("/tmp/" 
    				+ msg.getFilename() + ".txt", "OK".getBytes());
			logger.info("Export finished. " + zipOutStreamTaskAdapter
					.getFileCounter());
		} catch (TaskTimeoutException e) {
			try {
				saveZip(zipOutStreamTaskAdapter, msg.getFilename(), false);
			}
			catch (Exception e2) {
				e2.printStackTrace();
			}
			getMessageQueue().publish(new ExportMessage.Builder()
					.setFilename(msg.getFilename())
					.setCurrentFile(zipOutStreamTaskAdapter.getCurrentFile())
					.setFileCounter(zipOutStreamTaskAdapter.getFileCounter()) 
					.setExportType(msg.getExportType()).create());
			logger.info("Added new export task "
					+ zipOutStreamTaskAdapter.getCurrentFile());
		} catch (Exception e) {
			logger.error(e.toString() + " " + e.getMessage() + "\n"
					+ ExceptionUtils.getFullStackTrace(e));
		}
	}

	private void saveZip(ZipOutStreamTaskAdapter zipOutStreamTaskAdapter,
			String filename, boolean disk) throws IOException {
		zipOutStreamTaskAdapter.getOutStream().close();
		if (disk) {
			getBusiness().getFileBusiness().saveFile("/tmp/" + filename, 
				zipOutStreamTaskAdapter.getOutData().toByteArray());
		}
		else {
			getBusiness().getSystemService().getCache().putBlob(filename, 
				zipOutStreamTaskAdapter.getOutData().toByteArray());
		}
	}

	private void removeExportFile(String currentFile) {
		getBusiness().getFileBusiness().remove("/tmp/" + currentFile);
		getBusiness().getFileBusiness().remove("/tmp/" + currentFile + ".txt");
	}

	private ImportExportBusiness getImportExportBusiness() {
		return getBusiness().getImportExportBusiness();
	}

	private void openStream(ZipOutStreamTaskAdapter zip, String filename) {
		byte[] savedZip = getBusiness().getSystemService().getCache()
				.getBlob(filename);
		ByteArrayOutputStream outData = new ByteArrayOutputStream();
		ZipOutputStream out = new ZipOutputStream(outData);
		zip.setOutStream(out);
		zip.setOutData(outData);
		if (savedZip != null) {
			ByteArrayInputStream inputData = new ByteArrayInputStream(savedZip);
			try {
				ZipInputStream in = new ZipInputStream(inputData);
				ZipEntry entry;
				while ((entry = in.getNextEntry()) != null) {
					out.putNextEntry(entry);
					if (!entry.isDirectory()) {
						IOUtils.copy(in, out);
		            }
		            out.closeEntry();
				}
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static String getExportFilename(String exportType) {
		if (exportType.equals(TYPE_PARAM_SITE)) {
			return "exportSite.ho";
		}
		if (exportType.equals(TYPE_PARAM_THEME)) {
			return "exportTheme.ho";
		}
		if (exportType.equals(TYPE_PARAM_FOLDER)) {
			return "exportFolder.ho";
		}
		if (exportType.equals(TYPE_PARAM_FULL)) {
			return "exportFull.ho";
		}
		if (exportType.equals(TYPE_PARAM_RESOURCES)) {
			return "exportResources.ho";
		}
		return null;
	}

}

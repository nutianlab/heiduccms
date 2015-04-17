

package com.heiduc.business.impl.mq.subscriber;

import java.io.UnsupportedEncodingException;

import org.dom4j.DocumentException;

import com.heiduc.business.impl.ImportExportBusinessImpl;
import com.heiduc.business.impl.mq.AbstractSubscriber;
import com.heiduc.business.mq.Message;
import com.heiduc.business.mq.message.SimpleMessage;
import com.heiduc.common.VfsNode;
import com.heiduc.dao.DaoTaskException;

/**
 * Import file.
 * 
 * @author Alexander Oleynik
 *
 */
public class ImportFile extends AbstractSubscriber {

	public void onMessage(Message message) {
		try {
			SimpleMessage msg = (SimpleMessage)message;
			String path = msg.getMessage();
			if (ImportExportBusinessImpl.isGlobalSequenceImportFile(
					path.substring(1))) {
				return;
			}
			VfsNode node = VfsNode.find(path);
			if (node == null) {
				logger.error("VFS node not found " + path);
				return;
			}
			String xml = new String(node.getData(), "UTF-8");
			if (!getBusiness().getImportExportBusiness().getExporterFactory()
					.getSiteExporter().importSystemFile(path.substring(1), xml)) {
				getBusiness().getImportExportBusiness().getExporterFactory()
					.getResourceExporter().importResourceFile(path, node.getData());
			}
		}
		catch (DocumentException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		catch (DaoTaskException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}

}


package com.heiduc.business.impl.mq.subscriber;


import com.heiduc.business.impl.mq.AbstractSubscriber;
import com.heiduc.business.mq.Message;
import com.heiduc.business.mq.Topic;
import com.heiduc.business.mq.message.SimpleMessage;
import com.heiduc.common.VfsNode;

/**
 * Import folder.
 * 
 * @author Alexander Oleynik
 *
 */
public class ImportFolder extends AbstractSubscriber {

	public void onMessage(Message message) {
		SimpleMessage msg = (SimpleMessage)message;
		String path = msg.getMessage();
		getBusiness().getFolderBusiness().createFolder(path);
		VfsNode node = VfsNode.find(path);
		if (node == null) {
			logger.error("VFS node not found " + path);
			return;
		}
		for (VfsNode child : node.getChildren()) {
			if (child.isDirectory()) {
				getBusiness().getMessageQueue().publish(new SimpleMessage(
						Topic.IMPORT_FOLDER, child.getPath()));
			}
			else {
				getBusiness().getMessageQueue().publish(new SimpleMessage(
						Topic.IMPORT_FILE, child.getPath()));
			}
		}
	}

}
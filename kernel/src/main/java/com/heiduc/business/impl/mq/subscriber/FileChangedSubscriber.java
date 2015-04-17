

package com.heiduc.business.impl.mq.subscriber;


import com.heiduc.business.impl.mq.AbstractSubscriber;
import com.heiduc.business.mq.Message;
import com.heiduc.business.mq.message.SimpleMessage;

/**
 * 
 * @author Alexander Oleynik
 *
 */
public class FileChangedSubscriber extends AbstractSubscriber {

	@Override
	public void onMessage(Message message) {
		SimpleMessage msg = (SimpleMessage)message;
		getBusiness().getSystemService().getFileCache().remove(msg.getMessage());
		logger.debug("Clear file cache " + msg.getMessage());
	}

}

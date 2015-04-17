

package com.heiduc.business.impl.mq;

import static org.heiduc.api.taskqueue.TaskOptions.Builder.withUrl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.heiduc.api.taskqueue.Queue;

import com.heiduc.business.Business;
import com.heiduc.business.impl.mq.subscriber.EntityRemove;
import com.heiduc.business.impl.mq.subscriber.ExportTaskSubscriber;
import com.heiduc.business.impl.mq.subscriber.FileChangedSubscriber;
import com.heiduc.business.impl.mq.subscriber.ImportFile;
import com.heiduc.business.impl.mq.subscriber.ImportFolder;
import com.heiduc.business.impl.mq.subscriber.ImportTaskSubscriber;
import com.heiduc.business.impl.mq.subscriber.IndexChangedPages;
import com.heiduc.business.impl.mq.subscriber.IndexDeletedPages;
import com.heiduc.business.impl.mq.subscriber.PageCacheClear;
import com.heiduc.business.impl.mq.subscriber.PagePublishCron;
import com.heiduc.business.impl.mq.subscriber.Reindex;
import com.heiduc.business.mq.Message;
import com.heiduc.business.mq.MessageQueue;
import com.heiduc.business.mq.QueueSpeed;
import com.heiduc.business.mq.Subscriber;
import com.heiduc.business.mq.Topic;
import com.heiduc.common.HeiducContext;
import com.heiduc.global.SystemService;
import com.heiduc.servlet.MessageQueueServlet;
import com.heiduc.utils.StreamUtil;

/**
 * 
 * @author Alexander Oleynik
 *
 */
public class MessageQueueImpl implements MessageQueue {

	private static final Log logger = LogFactory.getLog(MessageQueueImpl.class);
	
	private Map<String, List<Class>> subscribers = 
		new HashMap<String, List<Class>>();
	
	public MessageQueueImpl() {
		registerSubscribers();
	}
	
	private void registerSubscribers() {
		subscribe(Topic.FILE_CHANGED, FileChangedSubscriber.class);
		subscribe(Topic.EXPORT, ExportTaskSubscriber.class);
		subscribe(Topic.IMPORT, ImportTaskSubscriber.class);

		subscribe(Topic.PAGES_DELETED, IndexDeletedPages.class);
		subscribe(Topic.PAGES_DELETED, PageCacheClear.class);
		
		subscribe(Topic.PAGES_CHANGED, IndexChangedPages.class);
		subscribe(Topic.PAGES_CHANGED, PageCacheClear.class);
		
		subscribe(Topic.PAGE_CACHE_CLEAR, PageCacheClear.class);

		subscribe(Topic.REINDEX, Reindex.class);
		subscribe(Topic.IMPORT_FILE, ImportFile.class);
		subscribe(Topic.IMPORT_FOLDER, ImportFolder.class);
		
		subscribe(Topic.ENTITY_REMOVE, EntityRemove.class);
		subscribe(Topic.PAGE_PUBLISH_CRON, PagePublishCron.class);
	}

	private SystemService getSystemService() {
		return HeiducContext.getInstance().getBusiness().getSystemService();
	}
	
	private String getQueueName(Message message) {
		if (message.getSpeed() == null) {
			return "mq-high";
		}
		else if (message.getSpeed().equals(QueueSpeed.HIGH)) {
			return "mq-high";
		}
		else if (message.getSpeed().equals(QueueSpeed.MEDIUM)) {
			return "mq-medium";
		}
		else if (message.getSpeed().equals(QueueSpeed.LOW)) {
			return "mq-low";
		}
		return "mq-high";
	}
	
	@Override
	public void publish(Message message) {
		Queue queue = getSystemService().getQueue(getQueueName(message));
		if (message.getTopic() == null) {
			logger.error("Topic is null in message " + message);
			return;
		}
		queue.add(withUrl(MessageQueueServlet.MQ_URL)
				.param("message", Base64.encodeBase64(StreamUtil.toBytes(message))));
	}

	@Override
	public void subscribe(Topic topic, Class subscriber) {
		subscribe(topic.name(), subscriber);
	}
	
	@Override
	public void subscribe(String topic, Class subscriber) {
		if (!subscribers.containsKey(topic)) {
			subscribers.put(topic, new ArrayList<Class>());
		}
		if (!subscribers.get(topic).contains(subscriber)) {
			subscribers.get(topic).add(subscriber);
		}
	}

	@Override
	public void unsubscribe(String topic, Class subscriber) {
		if (subscribers.containsKey(topic)) {
			if (subscribers.get(topic).contains(subscriber)) {
				subscribers.get(topic).remove(subscriber);
			}
		}
	}

	@Override
	public void execute(Message message) {
		if (subscribers.containsKey(message.getTopic())) {
			for (Class subscriberClass : subscribers.get(message.getTopic())) {
				try {
					Subscriber subscriber = (Subscriber)
							subscriberClass.newInstance();
					subscriber.onMessage(message);
				}
				catch (IllegalAccessException e) {
					logger.error(e.getMessage());
				}
				catch (InstantiationException e) {
					logger.error(e.getMessage());
				}
			}
		}
		if (!StringUtils.isEmpty(message.getCommandClassName())) {
			try {
				Class clazz = loadClass(message.getCommandClassName());
				if (clazz != null) {
					Subscriber subscriber = (Subscriber)clazz.newInstance();
					subscriber.onMessage(message);
				}
				else {
					logger.error("Command class not found: " 
							+ message.getCommandClassName());
				}
			}
			catch (IllegalAccessException e) {
				logger.error(e.getMessage());
			}
			catch (InstantiationException e) {
				logger.error(e.getMessage());
			}
		}
	}
	
	private Class loadClass(String name) {
		try {
			return this.getClass().getClassLoader().loadClass(name);
		}
		catch (ClassNotFoundException e) {
		}
		return getBusiness().getPluginBusiness().loadClass(name);
	}
	
	private Business getBusiness() {
		return HeiducContext.getInstance().getBusiness();
	}

}



package com.heiduc.update.verion_0_7;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.heiduc.business.impl.mq.AbstractSubscriber;
import com.heiduc.business.mq.Message;
import com.heiduc.business.mq.message.SimpleMessage;
import com.heiduc.entity.PageEntity;
import com.heiduc.utils.StrUtil;

/**
 * Update titles format from CSV to JSON.
 * 
 * @author Alexander Oleynik
 *
 */
public class PageTitleUpdate extends AbstractSubscriber {

	@Override
	public void onMessage(Message message) {
		logger.info("PageTitleUpdate 0.7 task started...");
		List<PageEntity> pages = getDao().getPageDao().select();
		for (PageEntity page : pages) {
			if (StringUtils.isEmpty(page.getTitle())) {
				Map<String, String> map = StrUtil.unpack06Title(
						page.getTitleValue());
				for (String key : map.keySet()) {
					page.setLocalTitle(map.get(key), key);
				}
				getDao().getPageDao().save(page);
			}
			if (getBusiness().getSystemService()
					.getRequestCPUTimeSeconds() > 20) {
				SimpleMessage msg = new SimpleMessage(PageTitleUpdate.class);
				getBusiness().getMessageQueue().publish(msg);
				return;
			}
		}
		logger.info("PageTitleUpdate 0.7 task completed.");
	}
}

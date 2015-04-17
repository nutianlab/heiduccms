

package com.heiduc.business.plugin;

import java.util.Date;

public interface PluginCronJob {

	/**
	 * Execute cron job.
	 */
	void run();
	
	/**
	 * Check if job should run.
	 * @param date
	 * @return
	 */
	boolean isShowTime(Date date);
	
}

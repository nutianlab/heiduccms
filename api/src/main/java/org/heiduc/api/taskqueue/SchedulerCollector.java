package org.heiduc.api.taskqueue;

import org.heiduc.api.util.Constants;
import org.heiduc.api.util.HttpProxy;

import it.sauronsoftware.cron4j.SchedulingPattern;
import it.sauronsoftware.cron4j.Task;
import it.sauronsoftware.cron4j.TaskCollector;
import it.sauronsoftware.cron4j.TaskExecutionContext;
import it.sauronsoftware.cron4j.TaskTable;

public class SchedulerCollector implements TaskCollector{

	@Override
	public TaskTable getTasks() {
		
		TaskTable taskTable = new TaskTable();
		//TODO 初始化配置文件
		
		
		//Plugin cron job scheduler
		SchedulingPattern pluginPattern = new SchedulingPattern("*/1 * * * *");
		taskTable.add(pluginPattern, new Task() {
		
			@Override
			public void execute(TaskExecutionContext context) throws RuntimeException {
				
				HttpProxy.doPost(Constants.TASKQUEUE_SERVER+"/_ah/cron/plugin", "UTF-8");
		
			}
		
		});
		
		//Page published cron job scheduler
		SchedulingPattern pagePattern = new SchedulingPattern("0 */1 * * *");
		taskTable.add(pagePattern, new Task() {
		
			@Override
			public void execute(TaskExecutionContext context) throws RuntimeException {
				
				HttpProxy.doPost(Constants.TASKQUEUE_SERVER+"/_ah/cron/page_publish", "UTF-8");
		
			}
		
		});
		return taskTable;
	}
	
	
	
	
}

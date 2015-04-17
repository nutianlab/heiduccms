package org.heiduc.api.taskqueue;

import it.sauronsoftware.cron4j.Scheduler;

public class SchedulerFactory {
	
	
	private static Scheduler instance = null;
	
	private SchedulerFactory(){
		
	}
	
	
	public synchronized static Scheduler getInstance(){
		
		if(instance == null){
			instance = new Scheduler();
			SchedulerCollector collector = new SchedulerCollector();
			
			instance.addTaskCollector(collector);
		}
		
		return instance;
	}
	
	
	public synchronized static void start(){
		if(instance.isStarted())
			instance.start();
	}
	
	
	public synchronized static void stop(){
		if(instance != null)
			instance.stop();
	}
	
	
	
	
	
	
	

}

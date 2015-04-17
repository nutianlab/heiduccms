package com.heiduc.listener;

import it.sauronsoftware.cron4j.Scheduler;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.heiduc.api.taskqueue.SchedulerFactory;


public class SchedulerContextListener implements ServletContextListener {

	private final static String SCHEDULER = "heiduc.scheduler";
	
	@Override
	public void contextInitialized(ServletContextEvent event) {
		ServletContext context = event.getServletContext();
		Scheduler scheduler = SchedulerFactory.getInstance();
		scheduler.start();
		context.setAttribute(SCHEDULER, scheduler);
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		
		ServletContext context = event.getServletContext();
		Scheduler scheduler = (Scheduler) context.getAttribute(SCHEDULER);
		context.removeAttribute(SCHEDULER);
		scheduler.stop();

	}

}

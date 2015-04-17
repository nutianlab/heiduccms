

package com.heiduc.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.heiduc.command.CommandMessage;
import com.heiduc.command.PageSearchCommand;
import com.heiduc.common.HeiducContext;

/**
 * Servlet for starting command tasks linked to channel API.
 * 
 * @author Aleksandr Oleynik 
 */
public class ChannelCommandServlet extends AbstractServlet {
	
	private static final long serialVersionUID = 1L;

	// Mapping between string command and task class
	private static final Object[] COMMANDS = {
		"pageSearch", PageSearchCommand.class
	};
	
	private static Map<String, String> getParameters(HttpServletRequest request) {
		Map<String, String> result = new HashMap<String, String>();
		for (Object key : request.getParameterMap().keySet()) {
			if (!("clientId".equals(key) || "cmd".equals(key))) {
				result.put((String)key, 
						((String[])request.getParameterMap().get(key))[0]);
				System.out.println((String)key+"   "+((String[])request.getParameterMap().get(key))[0]);
			}
		}
		return result;
	}
	
	private Map<String, Class> commands = null;
	
	public  Class getCommand(String key) {
		if (commands == null) {
			 commands = new HashMap<String, Class>();
			 for (int i=0; i < COMMANDS.length; i+=2) {
				 commands.put((String)COMMANDS[i], (Class)COMMANDS[i+1]);
			 }
		}
		return commands.get(key);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String cmd = request.getParameter("cmd");
		String clientId = request.getParameter("clientId");
		Class commandClass = getCommand(cmd);
		if (commandClass != null) {
			CommandMessage msg = new CommandMessage(clientId, 
					commandClass.getName(), getParameters(request));
			
			HeiducContext.getInstance().getMessageQueue().publish(msg);
		}
	}
	
}
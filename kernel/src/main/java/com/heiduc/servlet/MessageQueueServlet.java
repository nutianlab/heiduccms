

package com.heiduc.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;

import com.heiduc.business.mq.Message;
import com.heiduc.common.HeiducContext;
import com.heiduc.entity.helper.UserHelper;
import com.heiduc.utils.StreamUtil;

/**
 * 
 * @author Alexander Oleynik
 *
 */
public class MessageQueueServlet extends AbstractServlet {

	public static final String MQ_URL = "/_ah/queue/mq";
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		execute(request, response);
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		execute(request, response);
	}

	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String msg = request.getParameter("message");
		if (msg == null) {
			logger.error("Message is null");
			return;
		}
		try {
			Message message = (Message)StreamUtil.toObject(
					Base64.decodeBase64(request.getParameter("message")));
			HeiducContext.getInstance().setUser(UserHelper.ADMIN);
			getMessageQueue().execute(message);
		}
		catch(Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}
	
}
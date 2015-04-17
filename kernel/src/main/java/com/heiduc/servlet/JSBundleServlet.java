

package com.heiduc.servlet;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.heiduc.i18n.Messages;
import com.heiduc.utils.DateUtil;

/**
 * 
 * @author Alexander Oleynik
 *
 */
public class JSBundleServlet extends AbstractServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		byte[] content = Messages.getJSMessages().getBytes("UTF-8");
		response.setHeader("Content-type", "text/javascript; charset=\"utf-8\""); 
		response.setHeader("Expires", "Sat, 6 May 1995 12:00:00 GMT");
		response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
		response.addHeader("Cache-Control", "post-check=0, pre-check=0");
		response.setHeader("Pragma", "no-cache");
    	response.setCharacterEncoding("UTF-8");
		response.setHeader("Content-Length", String.valueOf(content.length));
		response.setHeader("Last-Modified", 
				DateUtil.toHeaderString(new Date()));
		BufferedOutputStream output = new BufferedOutputStream(
				response.getOutputStream());
		output.write(content);
		output.flush();
		output.close();
	}
	
}
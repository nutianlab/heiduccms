

package com.heiduc.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * @author Alexander Oleynik
 *
 */
public class CacheResetServlet extends AbstractServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		getBusiness().getSystemService().getCache().clear();
		response.setHeader("Content-type", "text/html"); 
		response.getWriter().write("Done.");
	}
	
}
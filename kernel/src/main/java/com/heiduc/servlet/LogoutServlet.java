

package com.heiduc.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.heiduc.common.HeiducContext;
import com.heiduc.filter.AuthenticationFilter;

/**
 * 
 * @author Alexander Oleynik
 *
 */
public class LogoutServlet extends AbstractServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HeiducContext.getInstance().getSession().set(
				AuthenticationFilter.USER_SESSION_ATTR, (String)null);

		HeiducContext.getInstance().getSession().save(response);
		response.sendRedirect("/");
	}
	
}
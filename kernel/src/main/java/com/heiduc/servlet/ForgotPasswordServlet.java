

package com.heiduc.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.heiduc.common.HeiducContext;
import com.heiduc.entity.UserEntity;
import com.heiduc.filter.AuthenticationFilter;

/**
 * 
 * @author Alexander Oleynik
 *
 */
public class ForgotPasswordServlet extends AbstractServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String key = request.getParameter("key");
		UserEntity user = getDao().getUserDao().getByKey(key);
		if (user == null || user.isDisabled()) {
			RequestDispatcher dispatcher = getServletContext()
					.getRequestDispatcher("/cms/forgotPasswordFail.html");
			
			dispatcher.forward(request,response);
		}
		else {
			user.setForgotPasswordKey(null);
			getDao().getUserDao().save(user);
			HeiducContext.getInstance().getSession().set(
					AuthenticationFilter.USER_SESSION_ATTR, user.getEmail());
			
			response.sendRedirect("/cms/#profile");
		}
	}
	
}
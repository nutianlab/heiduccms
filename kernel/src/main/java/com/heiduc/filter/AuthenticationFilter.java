

package com.heiduc.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.heiduc.common.BCrypt;
import com.heiduc.common.HeiducContext;
import com.heiduc.entity.UserEntity;

/**
 * Check authorised and redirect to login. Inject current user into Vosao 
 * context.
 * @author Aleksandr Oleynik
 */
public class AuthenticationFilter extends AbstractFilter implements Filter {

    public static final String USER_SESSION_ATTR = "userEmail";
	public static final String ORIGINAL_VIEW_KEY = "originalViewKey";
	public static final String LOGIN_VIEW = "/login.vm";
	public static final String CMS = "/cms";

	public AuthenticationFilter() {
		super();
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
        String url = httpRequest.getServletPath();
        HeiducContext ctx = HeiducContext.getInstance();
        autoLogin(httpRequest);
        String userEmail = ctx.getSession().getString(USER_SESSION_ATTR);
      //Fixed a bug, in case a user is not logged in, a query to a null e-mail
//      UserEntity user = getDao().getUserDao().getByEmail(userEmail);
        UserEntity user = null;
        if( userEmail != null )
        	user = getDao().getUserDao().getByEmail(userEmail);
        
		if (user == null) {
			ctx.getSession().remove(USER_SESSION_ATTR);
			ctx.setUser(null);
			if (url.startsWith(CMS)) {
				String originalUrl = httpRequest.getRequestURI() 
					+ (httpRequest.getQueryString() == null ? "" : 
						"?" + httpRequest.getQueryString());
				ctx.getSession().set(ORIGINAL_VIEW_KEY, originalUrl);
//				return;
			}
		}
		else {
			ctx.setUser(user);
			if (url.startsWith(CMS) && ctx.getUser().isSiteUser()) {
				httpResponse.sendRedirect("/");
				return;
			}			
		}
		chain.doFilter(request, response);
	}

	private void autoLogin(HttpServletRequest request) {
		String email = request.getParameter("login_email");
		if (StringUtils.isEmpty(email)) {
			return;
		}
		String password = request.getParameter("login_password");
		if (StringUtils.isEmpty(password)) {
			return;
		}
		UserEntity user = getDao().getUserDao().getByEmail(email);
		if (user == null || user.isDisabled()) {
			return;
		}
		if (!BCrypt.checkpw(password, user.getPassword())) {
			return;
		}
		HeiducContext.getInstance().getSession().set(USER_SESSION_ATTR, user.getEmail());
	}
}

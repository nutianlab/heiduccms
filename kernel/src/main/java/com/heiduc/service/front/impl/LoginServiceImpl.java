

package com.heiduc.service.front.impl;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.heiduc.business.SetupBean;
import com.heiduc.business.mq.Topic;
import com.heiduc.business.mq.message.SimpleMessage;
import com.heiduc.common.BCrypt;
import com.heiduc.common.HeiducContext;
import com.heiduc.entity.UserEntity;
import com.heiduc.filter.AuthenticationFilter;
import com.heiduc.filter.LanguageFilter;
import com.heiduc.i18n.Messages;
import com.heiduc.service.ServiceResponse;
import com.heiduc.service.front.LoginService;
import com.heiduc.service.impl.AbstractServiceImpl;

/**
 * 
 * @author Alexander Oleynik
 *
 */
public class LoginServiceImpl extends AbstractServiceImpl 
		implements LoginService {

	@Override
	public ServiceResponse login(String email, String password) {
		UserEntity user = getDao().getUserDao().getByEmail(email);
		if (user == null || user.isDisabled()) {
			return ServiceResponse.createErrorResponse(Messages.get(
					"user_not_found"));
		}
		ServiceResponse passwordIncorrect = ServiceResponse.createErrorResponse(
				Messages.get("password_incorrect"));
		if (user.getPassword() == null) {
			if (!StringUtils.isEmpty(password)) {
				return passwordIncorrect;
			}
		}
		else {		
			try {
				if (!BCrypt.checkpw(password, user.getPassword())) {
					return passwordIncorrect;
				}
			}
			catch (Exception e) {
				e.printStackTrace();
				return passwordIncorrect;
			}
		}
		HeiducContext ctx = HeiducContext.getInstance();
		ctx.getSession().set(AuthenticationFilter.USER_SESSION_ATTR, user.getEmail());
		String originalView = ctx.getSession().getString(AuthenticationFilter.ORIGINAL_VIEW_KEY);
		logger.info(originalView);
		if (originalView != null) {
			ctx.getSession().remove(AuthenticationFilter.ORIGINAL_VIEW_KEY);
		}
		else {
			originalView = "/";
		}
		getMessageQueue().publish(new SimpleMessage(Topic.LOGIN.name(), 
				user.getEmail()));
		
		return ServiceResponse.createSuccessResponse(originalView);
	}

	@Override
	public ServiceResponse logout() {
		HeiducContext.getInstance().getSession().set(
				AuthenticationFilter.USER_SESSION_ATTR, (String)null);
		
		return ServiceResponse.createSuccessResponse(Messages.get(
				"success_logout"));
	}

	@Override
	public ServiceResponse forgotPassword(String email) {
		try {
			getBusiness().getUserBusiness().forgotPassword(email);
			return ServiceResponse.createSuccessResponse(Messages.get("success"));
		}
		catch (Exception e) {
			return ServiceResponse.createErrorResponse(e.getMessage());
		}
	}
	
	@Override
	public Map<String, String> getSystemProperties() {
		Map<String, String> result = new HashMap<String, String>();
		result.put("version", SetupBean.VERSION);
		result.put("fullVersion", SetupBean.FULLVERSION);
		result.put("loggedIn", new Boolean(HeiducContext.getInstance().getUser() != null).toString());
		return result;
	}

	@Override
	public ServiceResponse setLanguage(String language) {
		Locale locale = LanguageFilter.getLocale(language);
		logger.info("Locale " + locale.getDisplayName());
		HeiducContext.getInstance().setLocale(locale);
		HeiducContext.getInstance().getSession().setLocale(locale);
		return ServiceResponse.createSuccessResponse(Messages.get("success"));
	}

}

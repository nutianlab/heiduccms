

package com.heiduc.business.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.velocity.VelocityContext;

import com.heiduc.business.UserBusiness;
import com.heiduc.common.HeiducContext;
import com.heiduc.entity.ConfigEntity;
import com.heiduc.entity.UserEntity;
import com.heiduc.i18n.Messages;
import com.heiduc.utils.EmailUtil;
import com.heiduc.utils.HashUtil;
import com.heiduc.utils.StreamUtil;

/**
 * 
 * @author Alexander Oleynik
 *
 */
public class UserBusinessImpl extends AbstractBusinessImpl 
	implements UserBusiness {
	
	@Override
	public List<String> validateBeforeUpdate(final UserEntity user) {
		List<String> errors = new ArrayList<String>();
		UserEntity foundUser = getDao().getUserDao().getByEmail(user.getEmail());
		if (user.getId() == null) {
			if (foundUser != null) {
				errors.add(Messages.get("user_already_exists"));
			}
		}
		else {
			if (foundUser != null && !foundUser.getId().equals(user.getId())) {
				errors.add(Messages.get("user_already_exists"));
			}
		}
		if (StringUtils.isEmpty(user.getEmail())) {
			errors.add(Messages.get("email_is_empty"));
		}
		return errors;
	}

	@Override
	public void remove(List<Long> ids) {
		if (HeiducContext.getInstance().getUser().isAdmin()) {
			getDao().getUserGroupDao().removeByUser(ids);
			getDao().getUserDao().remove(ids);
		}
	}

	@Override
	public void forgotPassword(String email) {
		UserEntity user = getDao().getUserDao().getByEmail(email);
		if (user == null) {
			return;
		}
		String key = HashUtil.getMD5(email 
				+ String.valueOf((new Date()).getTime()));
		user.setForgotPasswordKey(key);
		getDao().getUserDao().save(user);
		String template = "";
		try {
			template = StreamUtil.getTextResource(
					"com/heiduc/resources/html/forgot-letter.html");
		}
		catch (IOException e) {
			LOGGER.error(e.getMessage());
			return;
		}
		ConfigEntity config = HeiducContext.getInstance().getConfig();
		VelocityContext context = new VelocityContext();
		context.put("user", user);
		context.put("config", config);
		context.put("key", key);
		String letter = getSystemService().render(template, context);
		String error = EmailUtil.sendEmail(letter, "Forgot password", 
				config.getSiteEmail(), "Site admin", email);
		if (error != null) {
			LOGGER.error(error);
		}
	}
	
}

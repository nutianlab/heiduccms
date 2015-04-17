

package com.heiduc.service.front.impl;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.tanesha.recaptcha.ReCaptchaResponse;


import com.heiduc.entity.ConfigEntity;
import com.heiduc.entity.FormEntity;
import com.heiduc.i18n.Messages;
import com.heiduc.service.ServiceResponse;
import com.heiduc.service.front.FormService;
import com.heiduc.service.impl.AbstractServiceImpl;
import com.heiduc.utils.EmailUtil;
import com.heiduc.utils.RecaptchaUtil;

/**
 * 
 * @author Alexander Oleynik
 *
 */
public class FormServiceImpl extends AbstractServiceImpl 
		implements FormService {

	@Override
	public ServiceResponse send(final String name, Map<String, String> params) {
		FormEntity form = getDao().getFormDao().getByName(name);
		if (form == null) {
			return new ServiceResponse("error", Messages.get("form_not_found",
					name));
		}
		String msgBody = createLetter(params);
		String subject = form.getLetterSubject();
		ConfigEntity config = getBusiness().getConfigBusiness().getConfig();
		String fromAddress = config.getSiteEmail();
		String fromText = config.getSiteDomain() + " admin";
		String toAddress = form.getEmail();
		String error = EmailUtil.sendEmail(msgBody, subject, fromAddress, fromText, 
					toAddress);
		if (error == null) {
			return new ServiceResponse("success", Messages.get(
					"form.success_send", form.getEmail()));
		}
		else {
			return new ServiceResponse("error", error);
		}
	}
	
	private String createLetter(final Map<String, String> params) {
		StringBuilder result = new StringBuilder();
		result.append("<table>");
		for (String key : params.keySet()) {
			result.append("<tr><td>").append(key).append("</td><td>")
				.append(params.get(key)).append("</td></tr>");
		}
		return result.toString();
	}

	@Override
	public ServiceResponse send(String name, Map<String, String> params,
			String challenge, String response, HttpServletRequest request) {

		ConfigEntity config = getBusiness().getConfigBusiness().getConfig();
		ReCaptchaResponse recaptchaResponse = RecaptchaUtil.check(
				config.getRecaptchaPublicKey(), 
				config.getRecaptchaPrivateKey(), 
				challenge, response, request); 
		ServiceResponse result = new ServiceResponse(); 
        if (recaptchaResponse.isValid()) {
            return send(name, params);
        }
        else {
                result.setResult("error");
                result.setMessage(recaptchaResponse.getErrorMessage());
        }
		return result;
	}
	

}

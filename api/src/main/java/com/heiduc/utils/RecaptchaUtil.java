

package com.heiduc.utils;

import javax.servlet.http.HttpServletRequest;

import net.tanesha.recaptcha.ReCaptcha;
import net.tanesha.recaptcha.ReCaptchaFactory;
import net.tanesha.recaptcha.ReCaptchaResponse;

public class RecaptchaUtil {

	public static ReCaptchaResponse check(
			final String publicKey,
			final String privateKey, 
			final String challenge, 
			final String response, HttpServletRequest request) {

		ReCaptcha captcha = ReCaptchaFactory.newReCaptcha(publicKey, 
				privateKey, false);
		String address = request.getRemoteAddr();
		return captcha.checkAnswer(address,	challenge, response);
	}
	
}

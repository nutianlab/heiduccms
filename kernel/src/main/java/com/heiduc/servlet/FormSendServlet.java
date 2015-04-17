

package com.heiduc.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.tanesha.recaptcha.ReCaptchaResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;

import com.heiduc.common.HeiducContext;
import com.heiduc.common.UploadException;
import com.heiduc.entity.ConfigEntity;
import com.heiduc.entity.FormEntity;
import com.heiduc.i18n.Messages;
import com.heiduc.utils.FileItem;
import com.heiduc.utils.RecaptchaUtil;
import com.heiduc.utils.StreamUtil;

/**
 * Servlet for form submition in forms plugin.
 * 
 * @author Aleksandr Oleynik 
 */
public class FormSendServlet extends AbstractServlet {
	
	private static final long serialVersionUID = 1L;

	private static final long MAX_SIZE = 10000000;
	private static final String TEXT_MESSAGE = "{\"result\":\"%s\", \"message\":\"%s\"}";
	private static final String FORM_NAME_PARAM = "form-name";

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String message = null;
		Map<String, String> parameters = new HashMap<String, String>();
		List<FileItem> files = new ArrayList<FileItem>();
		try {
			if (request.getContentType().startsWith("multipart/form-data")) {
				ServletFileUpload upload = new ServletFileUpload();
				upload.setFileSizeMax(MAX_SIZE);
				upload.setHeaderEncoding("UTF-8");
				FileItemIterator iter;
				try {
					iter = upload.getItemIterator(request);
					InputStream stream = null;
					while (iter.hasNext()) {
						FileItemStream item = iter.next();
						stream = item.openStream();
						if (item.isFormField()) {
							parameters.put(item.getFieldName(), 
								Streams.asString(stream, "UTF-8"));
						} else {
							files.add(new FileItem(item, 
								StreamUtil.readFileStream(stream)));
						}
					}
				} catch (FileUploadException e) {
					logger.error(e.getMessage());
					throw new UploadException(Messages.get(
							"request_parsing_error"));
				}
			}
			else {
				for (Object key : request.getParameterMap().keySet()) {
					String paramName = (String)key;
					parameters.put(paramName, request.getParameter(paramName));
				}
			}
			message = processForm(parameters, files, request);
		} catch (UploadException e) {
			message = createMessage("error", e.getMessage()); 
			logger.error(message);
		}
		catch (Exception e) {
			message = createMessage("error", e.getMessage()); 
			logger.error(message);
			e.printStackTrace();
		}
		response.setContentType("text/html");
    	response.setCharacterEncoding("UTF-8");
		response.setStatus(200);
		response.getWriter().write(message);
	}

	private String createMessage(final String result, final String message) {
		return String.format(TEXT_MESSAGE, result, message);
	}
	
	private String processForm(Map<String, String> parameters,
			List<FileItem> files, HttpServletRequest request) 
			throws UploadException {
		String formName = parameters.get(FORM_NAME_PARAM);
		if (formName == null) {
			throw new UploadException(Messages.get(
					"form.name_parameter_not_found"));
		}
		FormEntity form = getDao().getFormDao().getByName(formName);
		if (form == null) {
			throw new UploadException(Messages.get("form.not_found", formName));
		}
		ConfigEntity config = HeiducContext.getInstance().getConfig();
		String challenge = parameters.get("recaptcha_challenge_field");
		String response = parameters.get("recaptcha_response_field");
		if (form.isEnableCaptcha() && config.isEnableRecaptcha()) {
			ReCaptchaResponse recaptchaResponse = RecaptchaUtil.check(
					config.getRecaptchaPublicKey(), 
					config.getRecaptchaPrivateKey(), 
					challenge, response, request);
			if (!recaptchaResponse.isValid()) {
				return createMessage("error", Messages.get("incorrect_captcha"));
			}
		}
		getBusiness().getFormBusiness().submit(form, parameters, files, 
				request.getRemoteAddr());
		return createMessage("success", Messages.get("form.success_submit"));
	}
	
}
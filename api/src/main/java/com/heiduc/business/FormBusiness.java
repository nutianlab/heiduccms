

package com.heiduc.business;

import java.util.List;
import java.util.Map;

import com.heiduc.common.UploadException;
import com.heiduc.entity.FormDataEntity;
import com.heiduc.entity.FormEntity;
import com.heiduc.utils.FileItem;

public interface FormBusiness {

	List<String> validateBeforeUpdate(final FormEntity entity);
	
	/**
	 * Submit form.
	 * @param form - form to submit.
	 * @param parameters - form parameters.
	 * @param files - form parameters of FILE type.
	 */
	void submit(final FormEntity form, final Map<String, String> parameters, 
			final List<FileItem> files, String ipAddress) throws UploadException;

	String sendEmail(FormDataEntity formData);
	
}

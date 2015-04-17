

package com.heiduc.velocity.impl;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.VelocityContext;

import com.heiduc.business.Business;
import com.heiduc.business.PageBusiness;
import com.heiduc.dao.Dao;
import com.heiduc.entity.FieldEntity;
import com.heiduc.entity.FormConfigEntity;
import com.heiduc.entity.FormDataEntity;
import com.heiduc.entity.FormEntity;
import com.heiduc.entity.helper.EntityHelper;
import com.heiduc.enums.FieldType;
import com.heiduc.global.SystemService;
import com.heiduc.i18n.Messages;
import com.heiduc.velocity.FormVelocityService;

/**
 * 
 * @author Alexander Oleynik
 *
 */
public class FormVelocityServiceImpl implements FormVelocityService {

	private static final Log logger = LogFactory.getLog(FormVelocityServiceImpl.class);
	
	private Business business;
	
	public FormVelocityServiceImpl(Business aBusiness) {
		business = aBusiness;
	}
	
	@Override
	public String render(String formName) {
		try {
			FormEntity form = getDao().getFormDao().getByName(formName);
			if (form == null) {
				return Messages.get("form.not_found", formName);
			}
			List<FieldEntity> fields = getDao().getFieldDao().getByForm(form);
			FormConfigEntity formConfig = getDao().getFormConfigDao().getConfig();
			VelocityContext context = getPageBusiness().createContext(
				getBusiness().getLanguage(), null);
			
			context.put("formConfig", formConfig);
			context.put("form", form);
			context.put("fields", fields);
			boolean fileupload = false;
			for (FieldEntity field : fields) {
				if (field.getFieldType().equals(FieldType.FILE)) {
					fileupload = true;
				}
			}
			context.put("fileUpload", fileupload);
			if (StringUtils.isEmpty(formConfig.getFormTemplate())) {
				return Messages.get("form.template_is_empty");
			}
			return getSystemService().render(formConfig.getFormTemplate(), 
					context);
		}
		catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}

	private Business getBusiness() {
		return business;
	}
	
	private PageBusiness getPageBusiness() {
		return getBusiness().getPageBusiness();
	}

	private Dao getDao() {
		return getBusiness().getDao();
	}
	
	private SystemService getSystemService() {
		return getBusiness().getSystemService();
	}

	@Override
	public List<FormDataEntity> findData(String formName) {
		FormEntity form = getDao().getFormDao().getByName(formName);
		if (formName != null) {
			List<FormDataEntity> result = getDao().getFormDataDao().getByForm(
					form);
			Collections.sort(result, EntityHelper.MOD_DATE_DESC);
			return result;
		}
		return Collections.EMPTY_LIST;
	}
	
}

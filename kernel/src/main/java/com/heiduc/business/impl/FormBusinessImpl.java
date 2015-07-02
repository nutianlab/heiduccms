

package com.heiduc.business.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.velocity.VelocityContext;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.heiduc.business.FileBusiness;
import com.heiduc.business.FolderBusiness;
import com.heiduc.business.FormBusiness;
import com.heiduc.common.HeiducContext;
import com.heiduc.common.UploadException;
import com.heiduc.dao.FormDao;
import com.heiduc.entity.ConfigEntity;
import com.heiduc.entity.FieldEntity;
import com.heiduc.entity.FormConfigEntity;
import com.heiduc.entity.FormDataEntity;
import com.heiduc.entity.FormEntity;
import com.heiduc.enums.FieldType;
import com.heiduc.i18n.Messages;
import com.heiduc.utils.EmailUtil;
import com.heiduc.utils.FileItem;
import com.heiduc.utils.FolderUtil;
import com.heiduc.utils.ParamUtil;
import com.heiduc.utils.StrUtil;

/**
 * 
 * @author Alexander Oleynik
 *
 */
public class FormBusinessImpl extends AbstractBusinessImpl 
	implements FormBusiness {

	private FormDao getFormDao() {
		return getDao().getFormDao();
	}
	
	@Override
	public List<String> validateBeforeUpdate(final FormEntity entity) {
		List<String> errors = new ArrayList<String>();
		if (entity.getId() == null) {
			FormEntity myForm = getFormDao().getByName(entity.getName());
			if (myForm != null) {
				errors.add(Messages.get("form.already_exists"));
			}
		}
		if (StringUtils.isEmpty(entity.getName())) {
			errors.add(Messages.get("name_is_empty"));
		}
		if (StringUtils.isEmpty(entity.getTitle())) {
			errors.add(Messages.get("title_is_empty"));
		}
		if (StringUtils.isEmpty(entity.getEmail())) {
			errors.add(Messages.get("email_is_empty"));
		}
		return errors;
	}

	@Override
	public void submit(FormEntity form, Map<String, String> parameters,
			List<FileItem> files, String ipAddress) throws UploadException {
		filterXSS(parameters);
		FormDataEntity formData = saveFormData(form, parameters, files, 
				ipAddress);
		String error = sendEmail(formData);
		if (error != null) {
			throw new UploadException(error);
		}
	}
	
	private FormDataEntity saveFormData(FormEntity form, Map<String, String> parameters,
			List<FileItem> files, String ipAddress) {
		FormDataEntity formData = new FormDataEntity(form.getId(), "");
		formData.setIpAddress(ipAddress);
		getDao().getFormDataDao().save(formData);
		formData.setUuid(formData.getId().toString());
		Map<String, String> filesMap = saveFormDataFiles(formData, files);
		List<FieldEntity> fields = getDao().getFieldDao().getByForm(form);
		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("formData");
		for (FieldEntity field: fields) {
			String value = parameters.containsKey(field.getName()) ? 
					parameters.get(field.getName()) : "";
			if (field.getFieldType().equals(FieldType.FILE) 
				&& filesMap.containsKey(field.getName())) {
				value = filesMap.get(field.getName());
			}
			root.addElement(field.getName()).setText(value);
		}
		formData.setData(doc.asXML());
		return getDao().getFormDataDao().save(formData);
	}
	
	private void filterXSS(Map<String, String> params) {
		for (String key : params.keySet()) {
			String value = params.get(key);
			params.put(key, ParamUtil.filterXSS(value));
		}
	}
	
	private Map<String, String> saveFormDataFiles(FormDataEntity formData,
			List<FileItem> files) {
		Map<String, String> result = new HashMap<String, String>();
		getFolderBusiness().createFolder(getFilePath(formData));
		for (FileItem file: files) {
			String filepath = getFilePath(formData) + "/" + file.getFilename();
			getFileBusiness().saveFile(filepath, file.getData());			
			result.put(file.getFieldName(), "/file" + filepath);
		}
		return result;
	}
	
	public String getFilePath(FormDataEntity formData) {
		FormEntity form = getFormDao().getById(formData.getFormId());
		return "/form/" + form.getName() + "/" + formData.getUuid();
	}

	private FolderBusiness getFolderBusiness() {
		return getBusiness().getFolderBusiness();
	}

	private FileBusiness getFileBusiness() {
		return getBusiness().getFileBusiness();
	}

	@Override
	public String sendEmail(FormDataEntity formData) {
		FormEntity form = getFormDao().getById(formData.getFormId());
		ConfigEntity config = HeiducContext.getInstance().getConfig();
		FormConfigEntity formConfig = getDao().getFormConfigDao().getConfig();
		VelocityContext context = new VelocityContext();
		List<FieldEntity> fields = getDao().getFieldDao().getByForm(form);
		context.put("form", form);
		context.put("fields", fields);
		context.put("values", formData.getValues());
		context.put("config", config);
		String letter = getSystemService().render(
				formConfig.getLetterTemplate(), context);
		List<String> emails = StrUtil.fromCSV(form.getEmail());
		for (String email : emails) {
			String error = EmailUtil.sendEmail(
					letter, 
					form.getLetterSubject(), 
					config.getSiteEmail(), 
					"Site admin", 
					StringUtils.strip(email), 
					getFileItems(formData));
			if (error != null) {
				return error;
			}
			LOGGER.info("Form successfully submitted and emailed.");
		}
		return null;
	}
	
	private List<FileItem> getFileItems(FormDataEntity formData) {
		List<FileItem> result = new ArrayList<FileItem>();
		FormEntity form = getFormDao().getById(formData.getFormId());
		List<FieldEntity> fields = getDao().getFieldDao().getByForm(form);
		Map<String, String> values = formData.getValues();
		for (FieldEntity field : fields) {
			if (field.getFieldType().equals(FieldType.FILE)
				&& values.containsKey(field.getName()) 
				&& !StringUtils.isEmpty(values.get(field.getName()))) {
					String filepath = values.get(field.getName()).replace("/file", "");
					result.add(new FileItem(
							field.getName(), 
							FolderUtil.getFileName(filepath),
							getFileBusiness().readFile(filepath)));
			}
		}
		return result;
	}
}

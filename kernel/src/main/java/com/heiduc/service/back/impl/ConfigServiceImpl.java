

package com.heiduc.service.back.impl;

import java.io.IOException;
import java.util.List;
import java.util.Map;


import com.heiduc.business.SetupBean;
import com.heiduc.business.impl.SetupBeanImpl;
import com.heiduc.business.impl.mq.subscriber.ExportTaskSubscriber;
import com.heiduc.business.mq.Topic;
import com.heiduc.business.mq.message.ExportMessage;
import com.heiduc.business.mq.message.SimpleMessage;
import com.heiduc.entity.ConfigEntity;
import com.heiduc.entity.FileEntity;
import com.heiduc.i18n.Messages;
import com.heiduc.service.ServiceResponse;
import com.heiduc.service.back.ConfigService;
import com.heiduc.service.impl.AbstractServiceImpl;
import com.heiduc.service.vo.SiteStatVO;
import com.heiduc.utils.StrUtil;
import com.heiduc.utils.StreamUtil;

/**
 * 
 * @author Alexander Oleynik
 *
 */
public class ConfigServiceImpl extends AbstractServiceImpl 
		implements ConfigService {

	@Override
	public ConfigEntity getConfig() {
		return getBusiness().getConfigBusiness().getConfig();
	}

	@Override
	public ServiceResponse saveConfig(Map<String, String> vo) {
		ConfigEntity config = getBusiness().getConfigBusiness().getConfig();
		if (vo.get("commentsEmail") != null) {
			config.setCommentsEmail(vo.get("commentsEmail"));
		}
		if (vo.get("commentsTemplate") != null) {
			config.setCommentsTemplate(vo.get("commentsTemplate"));
		}
		if (vo.get("editExt") != null) {
			config.setEditExt(vo.get("editExt"));
		}
		if (vo.get("googleAnalyticsId") != null) {
			config.setGoogleAnalyticsId(vo.get("googleAnalyticsId"));
		}
		if (vo.get("enableRecaptcha") != null) {
			config.setEnableRecaptcha(Boolean.valueOf(vo.get("enableRecaptcha")));
		}
		if (vo.get("recaptchaPrivateKey") != null) {
			config.setRecaptchaPrivateKey(vo.get("recaptchaPrivateKey"));
		}
		if (vo.get("recaptchaPublicKey") != null) {
			config.setRecaptchaPublicKey(vo.get("recaptchaPublicKey"));
		}
		if (vo.get("siteDomain") != null) {
			config.setSiteDomain(vo.get("siteDomain"));
		}
		if (vo.get("siteEmail") != null) {
			config.setSiteEmail(vo.get("siteEmail"));
		}
		if (vo.get("siteUserLoginUrl") != null) {
			config.setSiteUserLoginUrl(vo.get("siteUserLoginUrl"));
		}
		if (vo.get("site404Url") != null) {
			config.setSite404Url(vo.get("site404Url"));
		}
		if (vo.get("enablePicasa") != null) {
			config.setEnablePicasa(Boolean.valueOf(vo.get("enablePicasa")));
		}
		if (vo.get("picasaUser") != null) {
			config.setPicasaUser(vo.get("picasaUser"));
		}
		if (vo.get("picasaPassword") != null) {
			config.setPicasaPassword(vo.get("picasaPassword"));
		}
		if (vo.get("enableCkeditor") != null) {
			config.setEnableCkeditor(Boolean.valueOf(vo.get("enableCkeditor")));
		}
		if (vo.get("defaultTimezone") != null) {
			config.setDefaultTimezone(vo.get("defaultTimezone"));
		}
		if (vo.get("defaultLanguage") != null) {
			config.setDefaultLanguage(vo.get("defaultLanguage"));
		}
		List<String> errors = getBusiness().getConfigBusiness()
				.validateBeforeUpdate(config);
		if (errors.isEmpty()) {
			getDao().getConfigDao().save(config);
			return ServiceResponse.createSuccessResponse(
					Messages.get("successfull_save", "Configuration"));
		}
		else {
			return ServiceResponse.createErrorResponse(
					Messages.get("error_during_save", "config"), errors);
		}
	}

	@Override
	public ServiceResponse restoreCommentsTemplate() throws IOException {
		ConfigEntity config = getDao().getConfigDao().getConfig();
		config.setCommentsTemplate(StreamUtil.getTextResource(
			SetupBeanImpl.COMMENTS_TEMPLATE_FILE));
		getDao().getConfigDao().save(config);			
		return ServiceResponse.createSuccessResponse(
				Messages.get("successfull_save", "Comments template"));
	}

	@Override
	public ServiceResponse reset() {
		getSetupBean().clear();
		getSetupBean().clearFileCache();
		getSetupBean().setup();
		return ServiceResponse.createSuccessResponse(
				Messages.get("successfull_reset", "Site"));
	}

	private SetupBean getSetupBean() {
		return getBusiness().getSetupBean();
	}

	@Override
	public ServiceResponse reindex() {
		getBusiness().getSearchEngine().reindex();
		return ServiceResponse.createSuccessResponse(
				Messages.get("index_creation_started"));
	}

	@Override
	public ServiceResponse cacheReset() {
		getBusiness().getSystemService().getCache().clear();
		getSetupBean().clearFileCache();
		return ServiceResponse.createSuccessResponse(
				Messages.get("successfull_reset", "Cache"));
	}

	@Override
	public ServiceResponse startExportTask(String exportType) {
		try {
			
		String filename = ExportTaskSubscriber.getExportFilename(exportType);
		if (filename != null) {
			getMessageQueue().publish(new ExportMessage.Builder()
					.setFilename(filename)
					.setExportType(exportType).create());
			return ServiceResponse.createSuccessResponse(filename);
		}
		return ServiceResponse.createErrorResponse(
				Messages.get("unknown_export_type", exportType));
		
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean isExportTaskFinished(String exportType) {
		String finishFilename = "/tmp/" + ExportTaskSubscriber
				.getExportFilename(exportType) + ".txt";
		FileEntity file = getBusiness().getFileBusiness().findFile(
				finishFilename);
		if (file != null) {
			String content = new String(getDao().getFileDao().getFileContent(
					file));
			if ("OK".equals(content)) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public ServiceResponse startExportThemeTask(List<String> ids,
			List<String> structureIds) {
		String filename = ExportTaskSubscriber.getExportFilename(
				ExportTaskSubscriber.TYPE_PARAM_THEME);
		getMessageQueue().publish(new ExportMessage.Builder()
				.setFilename(filename)
				.setExportType(ExportTaskSubscriber.TYPE_PARAM_THEME)
				.setIds(StrUtil.toLong(ids))
				.setStructureIds(StrUtil.toLong(ids)).create());
		return ServiceResponse.createSuccessResponse(filename);
	}

	@Override
	public ServiceResponse startExportFolderTask(Long folderId) {
		String filename = ExportTaskSubscriber.getExportFilename(
				ExportTaskSubscriber.TYPE_PARAM_FOLDER);
		getMessageQueue().publish(new ExportMessage.Builder()
				.setFilename(filename)
				.setExportType(ExportTaskSubscriber.TYPE_PARAM_FOLDER)
				.setFolderId(folderId).create());
		return ServiceResponse.createSuccessResponse(filename);
	}

	@Override
	public ServiceResponse loadDefaultSite() {
		getSetupBean().clear();
		getSetupBean().clearFileCache();
		getSetupBean().setup();  
		getSetupBean().loadDefaultSite();
		return ServiceResponse.createSuccessResponse(
				Messages.get("success"));
	}

	@Override
	public SiteStatVO getSiteStat() {
		return new SiteStatVO();
	}

	@Override
	public ServiceResponse saveAttribute(String name, String value) {
		ConfigEntity config = getDao().getConfigDao().getConfig();
		config.setAttribute(name, value);
		getDao().getConfigDao().save(config);
		return ServiceResponse.createSuccessResponse(
				Messages.get("success"));
	}

	@Override
	public ServiceResponse removeAttributes(List<String> names) {
		ConfigEntity config = getDao().getConfigDao().getConfig();
		config.removeAttributes(names);
		getDao().getConfigDao().save(config);
		return ServiceResponse.createSuccessResponse(
				Messages.get("success"));
	}
	
}

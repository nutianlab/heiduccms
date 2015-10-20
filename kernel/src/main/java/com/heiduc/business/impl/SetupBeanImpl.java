

package com.heiduc.business.impl;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.heiduc.business.Business;
import com.heiduc.business.SetupBean;
import com.heiduc.business.mq.MessageQueue;
import com.heiduc.business.mq.Topic;
import com.heiduc.business.mq.message.ImportMessage;
import com.heiduc.business.mq.message.SimpleMessage;
import com.heiduc.common.BCrypt;
import com.heiduc.common.HeiducContext;
import com.heiduc.common.Session;
import com.heiduc.dao.Dao;
import com.heiduc.entity.ConfigEntity;
import com.heiduc.entity.FileEntity;
import com.heiduc.entity.FolderEntity;
import com.heiduc.entity.FormConfigEntity;
import com.heiduc.entity.GroupEntity;
import com.heiduc.entity.LanguageEntity;
import com.heiduc.entity.PageEntity;
import com.heiduc.entity.TemplateEntity;
import com.heiduc.entity.UserEntity;
import com.heiduc.enums.ContentPermissionType;
import com.heiduc.enums.FolderPermissionType;
import com.heiduc.enums.PageState;
import com.heiduc.enums.UserRole;
import com.heiduc.utils.CipherUtils;
import com.heiduc.utils.StreamUtil;

public class SetupBeanImpl implements SetupBean {

	private static Log log = LogFactory.getLog(SetupBeanImpl.class);

	private static final String DEFAULT_SITE = "default.ho";
	
	private GroupEntity guests;
	
	public void setup() {
		log.info("setup...");
		clearCache();
		initGroups();
		initUsers();
		initTemplates();
		initFolders();
		initPages();
		initConfigs();
		initForms();
		initLanguages();
	}
	
	private void initLanguages() {
		LanguageEntity lang = getDao().getLanguageDao().getByCode(
				LanguageEntity.ENGLISH_CODE); 
		if (lang == null) {
			lang = new LanguageEntity(
				LanguageEntity.ENGLISH_CODE, LanguageEntity.ENGLISH_TITLE);
			getDao().getLanguageDao().save(lang);
		}
	}

	private void clearCache() {
		getBusiness().getSystemService().getCache().clear();
	} 

	private void initUsers() {
		List<UserEntity> admins = getDao().getUserDao().getByRole(UserRole.ADMIN);
		if (admins.size() == 0) {
			UserEntity admin = new UserEntity("admin", 
					BCrypt.hashpw("admin", BCrypt.gensalt()), 
					"dev@heiduc.com", 
					UserRole.ADMIN);
			getDao().getUserDao().save(admin);
	        log.info("Adding admin user dev@heiduc.com");
		}
	}

	private void initGroups() {
		guests = getDao().getGroupDao().getByName("guests");
		if (guests == null) {
			guests = new GroupEntity("guests");
			getDao().getGroupDao().save(guests);
		}
	}

	public static final String HOME_PAGE_FILE = 
		"com/heiduc/resources/html/root.html";

	public static final String LOGIN_PAGE_FILE = 
		"com/heiduc/resources/html/login.html";
	
	public static final String SEARCH_PAGE_FILE = 
		"com/heiduc/resources/html/search.html";
	
	private void initPages() {
		List<PageEntity> roots = getDao().getPageDao().getByParent("");
		if (roots.size() == 0) {
			TemplateEntity template = getDao().getTemplateDao().getByUrl("simple");
			addPage("Home page", "/", HOME_PAGE_FILE, template.getId(), 0, true, 
					true);
			getBusiness().getContentPermissionBusiness().setPermission(
					"/", guests, ContentPermissionType.READ);
	        addPage("Site user Login", "/login", LOGIN_PAGE_FILE, 
	        		template.getId(), 0, true, false);
	        addPage("Search", "/search", SEARCH_PAGE_FILE, template.getId(), 1,
	        		false, false);
		}
	}

	private void addPage(String title, String url, String resource, 
				Long templateId, Integer sortIndex, boolean cached, 
				boolean searchable) {
        String content = loadResource(resource);
		PageEntity page = new PageEntity(title, url,	templateId);
		page.setCreateUserEmail("dev@heiduc.com");
		page.setModUserEmail("dev@heiduc.com");
		page.setState(PageState.APPROVED);
		page.setSortIndex(sortIndex);
		page.setCached(cached);
		page.setSearchable(searchable);
		getDao().getPageDao().save(page);
		getDao().getPageDao().setContent(page.getId(), 
				LanguageEntity.ENGLISH_CODE, content);
        log.info("Added " + title);
	}
	
	public static final String SIMPLE_TEMPLATE_FILE = 
			"com/heiduc/resources/html/simple.html";
	
	private void initTemplates() {
		List<TemplateEntity> list = getDao().getTemplateDao().select();
		if (list.size() == 0) {
			String content = loadResource(SIMPLE_TEMPLATE_FILE);
			TemplateEntity template = new TemplateEntity("Simple", content, 
					"simple");
			getDao().getTemplateDao().save(template);
	        log.info("Adding default template.");
		}
	}

	private void initFolders() {
		List<FolderEntity> roots = getDao().getFolderDao().getByParent(null);
		if (roots.size() == 0) {
	        log.info("Adding default folders.");
			FolderEntity root = new FolderEntity("file", "/", null);
			getDao().getFolderDao().save(root);
			FolderEntity theme = new FolderEntity("Themes resources", "theme", root.getId());
			getDao().getFolderDao().save(theme);
			FolderEntity simple = new FolderEntity("Simple", "simple", theme.getId());
			getDao().getFolderDao().save(simple);
			getBusiness().getFolderPermissionBusiness().setPermission(
					root, guests, FolderPermissionType.READ);
			FolderEntity tmp = new FolderEntity("tmp", "tmp", root.getId());
			getDao().getFolderDao().save(tmp);
			getBusiness().getFolderPermissionBusiness().setPermission(
					tmp, guests, FolderPermissionType.WRITE);
			FolderEntity page = new FolderEntity("page", "page", root.getId());
			getDao().getFolderDao().save(page);
			
			FolderEntity form = new FolderEntity("form", "form", root.getId());
			getDao().getFolderDao().save(form);
			getBusiness().getFolderPermissionBusiness().setPermission(
					form, guests, FolderPermissionType.WRITE);

		}
	}
	
	private Dao getDao() {
		return getBusiness().getDao();
	}

	private Business getBusiness() {
		return HeiducContext.getInstance().getBusiness();
	}
	
	private MessageQueue getMessageQueue() {
		return HeiducContext.getInstance().getMessageQueue();
	}

	public static final String COMMENTS_TEMPLATE_FILE = 
			"com/heiduc/resources/html/comments.html";
	
	private void initConfigs() {
		ConfigEntity config = getBusiness().getConfigBusiness().getConfig();
		if (config.getId() == null || config.getId() == 0) {
	        config.setVersion(VERSION);
			config.setGoogleAnalyticsId("");
	        config.setSiteEmail("");
	        config.setSiteDomain("");
	        config.setEditExt("css,js,xml");
	        config.setSiteUserLoginUrl("/login");
	        config.setCommentsTemplate(loadResource(
	        		COMMENTS_TEMPLATE_FILE));
	        config.setSessionKey(CipherUtils.generateKey());
	        getDao().getConfigDao().save(config);
		}
	}
	
	private String loadResource(final String url) {
		try {
			return StreamUtil.getTextResource(url);
		}
		catch(IOException e) {
			log.error("Can't read comments template." + e);
			return "Error during load resources " + url;
		}
	}
	
	public static final String FORM_TEMPLATE_FILE =
		"com/heiduc/resources/html/form-template.html";
	public static final String FORM_LETTER_FILE =
		"com/heiduc/resources/html/form-letter.html";
	
	private void initForms() {
		FormConfigEntity config = getDao().getFormConfigDao().getConfig();
		if (config.getId() == null) {
			config.setFormTemplate(loadResource(
					FORM_TEMPLATE_FILE));
			config.setLetterTemplate(loadResource(
					FORM_LETTER_FILE));
			getDao().getFormConfigDao().save(config);			
		}
	}

	@Override
	public void clear() {
		getDao().getCommentDao().removeAll();
		getDao().getConfigDao().removeAll();
		getDao().getContentDao().removeAll();
		getDao().getContentPermissionDao().removeAll();
		getDao().getFieldDao().removeAll();
		getDao().getFileDao().removeAll();
		getDao().getFolderDao().removeAll();
		getDao().getFolderPermissionDao().removeAll();
		getDao().getFormDao().removeAll();
		getDao().getGroupDao().removeAll();
		getDao().getLanguageDao().removeAll();
		getDao().getMessageDao().removeAll();
		getDao().getPageDao().removeAll();
		getDao().getSeoUrlDao().removeAll();
		getDao().getStructureDao().removeAll();
		getDao().getStructureTemplateDao().removeAll();
		getDao().getTemplateDao().removeAll();
		getDao().getUserDao().removeAll();
		getDao().getUserGroupDao().removeAll();
		getDao().getPluginDao().removeAll();
		getDao().getPluginResourceDao().removeAll();
		getDao().getTagDao().removeAll();
		getDao().getPageTagDao().removeAll();
		clearCache();
	}

	@Override
	public void clearFileCache() {
		clearFileCache(getDao().getFolderDao().getByPath("/"));
	}
	
	private void clearFileCache(FolderEntity folder) {
		if (folder == null) {
			return;
		}
		String folderPath = getDao().getFolderDao().getFolderPath(
				folder.getId());
		for (FileEntity file : getDao().getFileDao().getByFolder(
				folder.getId())) {
			getDao().getSystemService().getFileCache().remove(folderPath + "/" 
					+ file.getFilename());
		}
		for (FolderEntity child : getDao().getFolderDao().getByParent(
				folder.getId())) {
			clearFileCache(child);
		}
	}

	@Override
	public void loadDefaultSite() {
		try {
			byte[] file = StreamUtil.getBytesResource(DEFAULT_SITE);
			getBusiness().getSystemService().getCache().putBlob(
					DEFAULT_SITE, file);
			getMessageQueue().publish(new ImportMessage.Builder()
					.setStart(1)
					.setFilename(DEFAULT_SITE)
					.create());
		}
		catch (IOException e) {
			log.error("Can't load default site: " + e.getMessage());
		}
	}
	
}

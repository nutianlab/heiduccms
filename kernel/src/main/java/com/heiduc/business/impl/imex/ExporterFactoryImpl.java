

package com.heiduc.business.impl.imex;

import com.heiduc.business.Business;
import com.heiduc.business.imex.ConfigExporter;
import com.heiduc.business.imex.ExporterFactory;
import com.heiduc.business.imex.FolderExporter;
import com.heiduc.business.imex.FormExporter;
import com.heiduc.business.imex.GroupExporter;
import com.heiduc.business.imex.MessagesExporter;
import com.heiduc.business.imex.PageDependencyExporter;
import com.heiduc.business.imex.PageExporter;
import com.heiduc.business.imex.PagePermissionExporter;
import com.heiduc.business.imex.PluginExporter;
import com.heiduc.business.imex.ResourceExporter;
import com.heiduc.business.imex.SeoUrlExporter;
import com.heiduc.business.imex.SiteExporter;
import com.heiduc.business.imex.StructureExporter;
import com.heiduc.business.imex.TagExporter;
import com.heiduc.business.imex.ThemeExporter;
import com.heiduc.business.imex.UserExporter;
import com.heiduc.business.imex.task.DaoTaskAdapter;
import com.heiduc.common.AbstractServiceBeanImpl;

public class ExporterFactoryImpl extends AbstractServiceBeanImpl 
		implements ExporterFactory {

	private DaoTaskAdapter daoTaskAdapter;
	
	private ConfigExporter configExporter;
	private FolderExporter folderExporter;
	private FormExporter formExporter;
	private GroupExporter groupExporter;
	private MessagesExporter messagesExporter;
	private PageExporter pageExporter;
	private PagePermissionExporter pagePermissionExporter;
	private PluginExporter pluginExporter;
	private ResourceExporter resourceExporter;
	private SiteExporter siteExporter;
	private StructureExporter structureExporter;
	private ThemeExporter themeExporter;
	private UserExporter userExporter;
	private SeoUrlExporter seoUrlExporter;
	private TagExporter tagExporter;
	private PageDependencyExporter pageDependencyExporter;
	
	public ExporterFactoryImpl(Business aBusiness, 
			DaoTaskAdapter aDaoTaskAdapter) {
		super(aBusiness);
		daoTaskAdapter = aDaoTaskAdapter;
	}

	public ConfigExporter getConfigExporter() {
		if (configExporter == null) {
			configExporter = new ConfigExporterImpl(this);
		}
		return configExporter;
	}

	public DaoTaskAdapter getDaoTaskAdapter() {
		return daoTaskAdapter;
	}

	public FolderExporter getFolderExporter() {
		if (folderExporter == null) {
			folderExporter = new FolderExporterImpl(this);
		}
		return folderExporter;
	}

	public FormExporter getFormExporter() {
		if (formExporter == null) {
			formExporter = new FormExporterImpl(this);
		}
		return formExporter;
	}

	public GroupExporter getGroupExporter() {
		if (groupExporter == null) {
			groupExporter = new GroupExporterImpl(this);
		}
		return groupExporter;
	}

	public MessagesExporter getMessagesExporter() {
		if (messagesExporter == null) {
			messagesExporter = new MessagesExporterImpl(this);
		}
		return messagesExporter;
	}

	public PageExporter getPageExporter() {
		if (pageExporter == null) {
			pageExporter = new PageExporterImpl(this);
		}
		return pageExporter;
	}

	public PagePermissionExporter getPagePermissionExporter() {
		if (pagePermissionExporter == null) {
			pagePermissionExporter = new PagePermissionExporterImpl(this);
		}
		return pagePermissionExporter;
	}

	public PluginExporter getPluginExporter() {
		if (pluginExporter == null) {
			pluginExporter = new PluginExporterImpl(this);
		}
		return pluginExporter;
	}

	public ResourceExporter getResourceExporter() {
		if (resourceExporter == null) {
			resourceExporter = new ResourceExporterImpl(this);
		}
		return resourceExporter;
	}

	public SiteExporter getSiteExporter() {
		if (siteExporter == null) {
			siteExporter = new SiteExporterImpl(this);
		}
		return siteExporter;
	}

	public StructureExporter getStructureExporter() {
		if (structureExporter == null) {
			structureExporter = new StructureExporterImpl(this);
		}
		return structureExporter;
	}

	public ThemeExporter getThemeExporter() {
		if (themeExporter == null) {
			themeExporter = new ThemeExporterImpl(this);
		}
		return themeExporter;
	}

	public UserExporter getUserExporter() {
		if (userExporter == null) {
			userExporter = new UserExporterImpl(this);
		}
		return userExporter;
	}

	public SeoUrlExporter getSeoUrlExporter() {
		if (seoUrlExporter == null) {
			seoUrlExporter = new SeoUrlsExporterImpl(this);
		}
		return seoUrlExporter;
	}

	@Override
	public TagExporter getTagExporter() {
		if (tagExporter == null) {
			tagExporter = new TagExporterImpl(this);
		}
		return tagExporter;
	}

	@Override
	public PageDependencyExporter getPageDependencyExporter() {
		if (pageDependencyExporter == null) {
			pageDependencyExporter = new PageDependencyExporterImpl(this);
		}
		return pageDependencyExporter;
	}

}

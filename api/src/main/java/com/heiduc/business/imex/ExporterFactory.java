

package com.heiduc.business.imex;

import com.heiduc.business.imex.task.DaoTaskAdapter;
import com.heiduc.common.AbstractServiceBean;

public interface ExporterFactory extends AbstractServiceBean {

	ConfigExporter getConfigExporter();

	DaoTaskAdapter getDaoTaskAdapter();

	FolderExporter getFolderExporter();

	FormExporter getFormExporter();

	GroupExporter getGroupExporter();

	MessagesExporter getMessagesExporter();

	PageExporter getPageExporter();

	PagePermissionExporter getPagePermissionExporter();

	PluginExporter getPluginExporter();

	ResourceExporter getResourceExporter();

	SiteExporter getSiteExporter();

	StructureExporter getStructureExporter();

	ThemeExporter getThemeExporter();

	UserExporter getUserExporter();
	
	SeoUrlExporter getSeoUrlExporter();

	TagExporter getTagExporter();
	
	PageDependencyExporter getPageDependencyExporter();

}

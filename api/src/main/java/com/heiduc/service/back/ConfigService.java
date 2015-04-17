

package com.heiduc.service.back;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.heiduc.entity.ConfigEntity;
import com.heiduc.service.AbstractService;
import com.heiduc.service.ServiceResponse;
import com.heiduc.service.vo.SiteStatVO;

/**
 * @author Alexander Oleynik
 */
public interface ConfigService extends AbstractService {
	
	ConfigEntity getConfig();
	
	ServiceResponse saveConfig(final Map<String, String> vo);
	
	ServiceResponse restoreCommentsTemplate() throws IOException;
	
	ServiceResponse reset();
	
	/**
	 * Recreate search index.
	 * @return service response.
	 */
	ServiceResponse reindex();
	
	ServiceResponse cacheReset();
	
	/**
	 * Start export task chain.
	 * @return service response.
	 */
	ServiceResponse startExportTask(String exportType);
	
	boolean isExportTaskFinished(String exportType);
	
	/**
	 * Start export themes task chain.
	 * @return service response.
	 */
	ServiceResponse startExportThemeTask(List<String> ids, 
			List<String> structureIds);

	/**
	 * Start export folder task chain.
	 * @return service response.
	 */
	ServiceResponse startExportFolderTask(Long folderId);
	
	ServiceResponse loadDefaultSite();
	
	SiteStatVO getSiteStat();
	
	ServiceResponse saveAttribute(String name, String value);
	
	ServiceResponse removeAttributes(List<String> names);
	
}

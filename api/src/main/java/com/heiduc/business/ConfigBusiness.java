

package com.heiduc.business;

import java.util.List;

import com.heiduc.entity.ConfigEntity;


public interface ConfigBusiness {

	ConfigEntity getConfig();
	
	boolean isTextFileExt(final String ext);
	
	boolean isImageFileExt(final String ext);
	
	List<String> validateBeforeUpdate(ConfigEntity entity);
}

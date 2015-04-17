

package com.heiduc.service.back;

import java.util.List;
import java.util.Map;

import com.heiduc.business.decorators.TreeItemDecorator;
import com.heiduc.entity.TagEntity;
import com.heiduc.service.AbstractService;
import com.heiduc.service.ServiceResponse;
import com.heiduc.service.vo.PageVO;

/**
 * @author Alexander Oleynik
 */
public interface TagService extends AbstractService {
	
	List<TreeItemDecorator<TagEntity>> getTree();

	TagEntity getById(Long id);
	
	ServiceResponse save(Map<String, String> vo);
	
	ServiceResponse remove(Long id);

	ServiceResponse addTag(String pageURL, Long tagId);
	
	ServiceResponse removeTag(String pageURL, Long tagId);

	List<PageVO> getPages(Long tagId);
	
}

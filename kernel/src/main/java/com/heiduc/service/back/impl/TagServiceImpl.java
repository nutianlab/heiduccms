

package com.heiduc.service.back.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.heiduc.business.decorators.TreeItemDecorator;
import com.heiduc.entity.PageEntity;
import com.heiduc.entity.TagEntity;
import com.heiduc.i18n.Messages;
import com.heiduc.service.ServiceResponse;
import com.heiduc.service.back.TagService;
import com.heiduc.service.impl.AbstractServiceImpl;
import com.heiduc.service.vo.PageVO;

/**
 * 
 * @author Alexander Oleynik
 *
 */
public class TagServiceImpl extends AbstractServiceImpl 
		implements TagService {

	@Override
	public List<TreeItemDecorator<TagEntity>> getTree() {
		return getBusiness().getTagBusiness().getTree();
	}

	@Override
	public TagEntity getById(Long id) {
		return getDao().getTagDao().getById(id);
	}

	@Override
	public ServiceResponse save(Map<String, String> vo) {
		TagEntity tag = null;
		if (!StringUtils.isEmpty(vo.get("id"))) {
			tag = getDao().getTagDao().getById(Long.valueOf(vo.get("id")));
		}
		if (tag == null) {
			tag = new TagEntity();
		}
		if (StringUtils.isEmpty(vo.get("parent"))) {
			tag.setParent(null);
		}
		else {
			tag.setParent(Long.valueOf(vo.get("parent")));
		}
		tag.setName(vo.get("name"));
		tag.setTitle(vo.get("title"));
		String error = getBusiness().getTagBusiness().validateBeforeSave(tag);
		if (error == null) {
			getDao().getTagDao().save(tag);
			return ServiceResponse.createSuccessResponse(
					Messages.get("tag.success_save"));
		}
		else {
			return ServiceResponse.createErrorResponse(error);
		}
	}

	@Override
	public ServiceResponse remove(Long id) {
		getBusiness().getTagBusiness().remove(id);
		return ServiceResponse.createSuccessResponse(
				Messages.get("tag.success_remove"));
	}

	@Override
	public ServiceResponse addTag(String pageURL, Long tagId) {
		TagEntity tag = getDao().getTagDao().getById(tagId);
		if (tag == null) {
			logger.error("Tag not found " + tagId);
		}
		else {
			getBusiness().getTagBusiness().addTag(pageURL, tag);
		}
		return ServiceResponse.createSuccessResponse(
				Messages.get("tag.success_add"));
	}

	@Override
	public ServiceResponse removeTag(String pageURL, Long tagId) {
		TagEntity tag = getDao().getTagDao().getById(tagId);
		if (tag != null) {
			getBusiness().getTagBusiness().removeTag(pageURL, tag);
		}
		return ServiceResponse.createSuccessResponse(
				Messages.get("tag.success_remove"));
	}

	@Override
	public List<PageVO> getPages(Long tagId) {
		List<PageVO> result = new ArrayList<PageVO>();
		TagEntity tag = getById(tagId);
		if (tag != null) {
			for (String url : tag.getPages()) {
				PageEntity page = getBusiness().getPageBusiness().getByUrl(url);
				if (page != null) {
					result.add(new PageVO(page));
				}
			}
		}
		return result;
	}
	
}



package com.heiduc.velocity.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.heiduc.business.Business;
import com.heiduc.business.decorators.TreeItemDecorator;
import com.heiduc.common.AbstractServiceBeanImpl;
import com.heiduc.entity.PageEntity;
import com.heiduc.entity.PageTagEntity;
import com.heiduc.entity.TagEntity;
import com.heiduc.entity.helper.PageHelper;
import com.heiduc.utils.ListUtil;
import com.heiduc.velocity.TagVelocityService;

/**
 * 
 * @author Alexander Oleynik
 *
 */
public class TagVelocityServiceImpl extends AbstractServiceBeanImpl implements
		TagVelocityService {

	public TagVelocityServiceImpl(Business business) {
		super(business);
	}
	
	@Override
	public List<TreeItemDecorator<TagEntity>> getTrees() {
		return getBusiness().getTagBusiness().getTree();
	}

	@Override
	public TreeItemDecorator<TagEntity> getTree(String name) {
		return getBusiness().getTagBusiness().getTree(name);
	}

	@Override
	public List<PageEntity> getPagesById(Long tagId) {
		TagEntity tag = getDao().getTagDao().getById(tagId);
		List<PageEntity> result = new ArrayList<PageEntity>();
		if (tag != null) {
			for (String url : tag.getPages()) {
				PageEntity page = getBusiness().getPageBusiness().getByUrl(url);
				if (page != null) {
					result.add(page);
				}
			}
		}
		Collections.sort(result, PageHelper.PUBLISH_DATE);
		return result;
	}

	@Override
	public List<TagEntity> getTags(String pageURL) {
		PageTagEntity pageTag = getDao().getPageTagDao().getByURL(pageURL);
		if (pageTag != null) {
			return getDao().getTagDao().getById(pageTag.getTags());
		}
		return Collections.EMPTY_LIST;
	}

	@Override
	public List<PageEntity> getPagesByPath(String tagPaths) {
		String[] paths = tagPaths.replace(" ", "").split(",");
		if (paths.length == 0) {
			return Collections.EMPTY_LIST;
		}
		Set<PageEntity> pages = null;
		for (String tagPath : paths) {
			TagEntity tag = getBusiness().getTagBusiness().getByPath(tagPath);
			if (tag != null) {
				if (pages == null) {
					pages = getPagesByTag(tag.getId());
				}
				else {
					pages.retainAll(getPagesByTag(tag.getId()));
				}
			}
		}
		List<PageEntity> result = new ArrayList<PageEntity>(pages);
		Collections.sort(result, PageHelper.PUBLISH_DATE);
		return result;
	}
	
	private Set<PageEntity> getPagesByTag(long tagId) {
		Set<PageEntity> result = new HashSet<PageEntity>(getPagesById(tagId));
		for (TagEntity tag : getDao().getTagDao().selectByParent(tagId)) {
			result.addAll(getPagesByTag(tag.getId()));
		}
		return result;
	}

	@Override
	public List<PageEntity> getPagesById(Long tagId, int index, int count) {
		return ListUtil.slice(getPagesById(tagId), index, count);
	}

	@Override
	public List<PageEntity> getPagesByPath(String tagPath, int index, int count) {
		return ListUtil.slice(getPagesByPath(tagPath), index, count);
	}

}

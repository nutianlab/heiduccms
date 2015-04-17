

package com.heiduc.business;

import java.util.List;

import com.heiduc.business.decorators.TreeItemDecorator;
import com.heiduc.entity.TagEntity;

public interface TagBusiness {

	List<TreeItemDecorator<TagEntity>> getTree();
	
	TreeItemDecorator<TagEntity> getTree(String name);

	String validateBeforeSave(TagEntity tag);
	
	void remove(Long id);
	
	TagEntity getByPath(String tagPath);
	
	String getPath(TagEntity tag);

	void addTag(String pageURL, TagEntity tag);

	void removeTag(String pageURL, TagEntity tag);

}

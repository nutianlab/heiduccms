

package com.heiduc.velocity;

import java.util.List;

import com.heiduc.business.decorators.TreeItemDecorator;
import com.heiduc.entity.PageEntity;
import com.heiduc.entity.TagEntity;

/**
 * @author Alexander Oleynik
 */
public interface TagVelocityService {

	List<TreeItemDecorator<TagEntity>> getTrees();
	
	TreeItemDecorator<TagEntity> getTree(String name);
	
	List<TagEntity> getTags(String pageURL);
	
	/**
	 * Get all pages by tag id.
	 * @param tagId - tag id
	 * @return found pages.
	 */
	List<PageEntity> getPagesById(Long tagId);
	
	/**
	 * Get all pages by tag id.
	 * @param tagId - tag id
	 * @param index - starting index.
	 * @param count - batch size.
	 * @return found pages.
	 */
	List<PageEntity> getPagesById(Long tagId, int index, int count);

	/**
	 * Get pages of tag selected by tag path in tags tree. Pages of all 
	 * children tags are also included.
	 * @param tagPaths - comma delimited list of tag paths. Path starts with /.
	 * @return pages by tags.
	 */
	List<PageEntity> getPagesByPath(String tagPaths);

	/**
	 * Get pages of tag selected by tag path in tags tree. Pages of all 
	 * children tags are also included.
	 * @param tagPath - path to tag like URL. Path starts with /.
	 * @param index - starting index.
	 * @param count - batch size.
	 * @return pages by tag.
	 */
	List<PageEntity> getPagesByPath(String tagPath, int index, int count);

}

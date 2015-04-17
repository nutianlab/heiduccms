

package com.heiduc.service.vo;

import java.util.ArrayList;
import java.util.List;

import com.google.gdata.data.Category;
import com.google.gdata.data.photos.PhotoEntry;

/**
 * Value object to be returned from services.
 * @author Alexander Oleynik
 */
public class PhotoVO {

	public static List<PhotoVO> create(List<PhotoEntry> photos) {
		List<PhotoVO> result = new ArrayList<PhotoVO>();
		for (PhotoEntry photo : photos) {
			result.add(new PhotoVO(photo));
		}
		return result;
	}

	private PhotoEntry photo;

	public PhotoVO(final PhotoEntry entry) {
		photo = entry;
	}

	public String getId() {
		return photo.getGphotoId();
	}
	
	public String getTitle() {
		return photo.getTitle().getPlainText();
	}

	public String getThumbnailURL() {
		return photo.getMediaThumbnails().get(1).getUrl();
	}

	public String getURL() {
		return photo.getMediaContents().get(0).getUrl();
	}
	
	public PhotoEntry photo() {
		return photo;
	}
	
	public List<String> getCategories() {
		return photo.getMediaKeywords().getKeywords();
	}

}

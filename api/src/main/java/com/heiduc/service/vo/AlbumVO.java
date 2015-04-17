

package com.heiduc.service.vo;

import java.util.ArrayList;
import java.util.List;


import com.google.gdata.data.photos.AlbumEntry;
import com.heiduc.i18n.Messages;

/**
 * Value object to be returned from services.
 * @author Alexander Oleynik
 */
public class AlbumVO {

	public static List<AlbumVO> create(List<AlbumEntry> list) {
		List<AlbumVO> result = new ArrayList<AlbumVO>();
		for (AlbumEntry album : list) {
			result.add(new AlbumVO(album));
		}
		return result;
	}

	private AlbumEntry album;

	public AlbumVO(final AlbumEntry entry) {
		album = entry;
	}

	public String getId() {
		return album != null ? album.getGphotoId(): Messages.get("not_found");
	}

	public String getTitle() {
		return album != null ? album.getTitle().getPlainText(): 
			Messages.get("not_found");
	}

	public String getName() {
		return album != null ? album.getName(): Messages.get("not_found");
	}
	
	public AlbumEntry album() {
		return album;
	}
	
	public List<String> getCategories() {
		return album.getMediaKeywords().getKeywords();
	}
}

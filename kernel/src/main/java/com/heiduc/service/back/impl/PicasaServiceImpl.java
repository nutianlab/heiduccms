

package com.heiduc.service.back.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.data.photos.AlbumEntry;
import com.google.gdata.data.photos.PhotoEntry;
import com.heiduc.i18n.Messages;
import com.heiduc.service.ServiceResponse;
import com.heiduc.service.back.PicasaService;
import com.heiduc.service.impl.AbstractServiceImpl;
import com.heiduc.service.vo.AlbumVO;
import com.heiduc.service.vo.PhotoVO;

/**
 * 
 * @author Alexander Oleynik
 *
 */
public class PicasaServiceImpl extends AbstractServiceImpl 
		implements PicasaService {

	@Override
	public List<AlbumVO> selectAlbums() {
		try {
			List<AlbumVO> result = new ArrayList<AlbumVO>();
			for (AlbumEntry album : getBusiness().getPicasaBusiness()
					.selectAlbums()) {
				result.add(new AlbumVO(album));
			}
			return result;
		}
		catch (Exception e) {
			logger.error(e.getMessage());
			return Collections.EMPTY_LIST;
		}
	}

	@Override
	public List<PhotoVO> selectPhotos(String albumId) {
		try {
			List<PhotoVO> result = new ArrayList<PhotoVO>();
			for (PhotoEntry photo : getBusiness().getPicasaBusiness()
					.selectPhotos(albumId)) {
				result.add(new PhotoVO(photo));
			}
			return result;
		}
		catch (Exception e) {
			logger.error(e.getMessage());
			return Collections.EMPTY_LIST;
		}
	}
	
	@Override
	public ServiceResponse addAlbum(String title) {
		try {
			AlbumEntry myAlbum = new AlbumEntry();
			myAlbum.setTitle(new PlainTextConstruct(title));
			myAlbum.setDescription(new PlainTextConstruct(title));
			getBusiness().getPicasaBusiness().addAlbum(myAlbum);
			return ServiceResponse.createSuccessResponse(
					Messages.get("success"));
		}
		catch (Exception e) {
			return ServiceResponse.createErrorResponse(e.getMessage());
		}
	}

	@Override
	public ServiceResponse removeAlbum(String albumId) {
		try {
			getBusiness().getPicasaBusiness().removeAlbum(albumId);
			return ServiceResponse.createSuccessResponse(Messages.get("success"));
		}
		catch (Exception e) {
			return ServiceResponse.createErrorResponse(e.getMessage());
		}
	}

	@Override
	public ServiceResponse removePhoto(String albumId, String photoId) {
		try {
			getBusiness().getPicasaBusiness().removePhoto(albumId, photoId);
			return ServiceResponse.createSuccessResponse(Messages.get("success"));
		}
		catch (Exception e) {
			return ServiceResponse.createErrorResponse(e.getMessage());
		}
	}
	
}

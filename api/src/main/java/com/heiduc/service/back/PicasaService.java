

package com.heiduc.service.back;

import java.util.List;

import com.heiduc.service.AbstractService;
import com.heiduc.service.ServiceResponse;
import com.heiduc.service.vo.AlbumVO;
import com.heiduc.service.vo.PhotoVO;

/**
 * @author Alexander Oleynik
 */
public interface PicasaService extends AbstractService {
	
	List<AlbumVO> selectAlbums();

	List<PhotoVO> selectPhotos(String albumId);
	
	ServiceResponse addAlbum(String title);

	ServiceResponse removeAlbum(String albumId);

	ServiceResponse removePhoto(String albumId, String photoId);
}

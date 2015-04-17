

package com.heiduc.velocity;

import java.util.List;

import com.heiduc.service.vo.AlbumVO;
import com.heiduc.service.vo.PhotoVO;

/**
 * @author Alexander Oleynik
 */
public interface PicasaVelocityService {

	List<AlbumVO> findAlbums();
	
	AlbumVO findAlbumById(String albumId);
	
	AlbumVO findAlbumByTitle(String title);
	
	List<PhotoVO> findPhotos(String title, int count);
	
	List<PhotoVO> findPhotosInAlbum(String albumId);
}

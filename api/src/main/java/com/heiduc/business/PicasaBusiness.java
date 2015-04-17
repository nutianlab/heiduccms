

package com.heiduc.business;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import com.google.gdata.client.photos.PicasawebService;
import com.google.gdata.data.photos.AlbumEntry;
import com.google.gdata.data.photos.PhotoEntry;
import com.google.gdata.util.ServiceException;

/**
 * 
 * @author Alexander Oleynik
 *
 */
public interface PicasaBusiness {

	PicasawebService getPicasawebService();

	List<AlbumEntry> selectAlbums() 
			throws MalformedURLException, IOException, ServiceException;

	List<PhotoEntry> selectPhotos(String albumId) 
			throws MalformedURLException, IOException, ServiceException;

	AlbumEntry addAlbum(AlbumEntry album) 
			throws MalformedURLException, IOException, ServiceException;

	void removeAlbum(String albumId) 
			throws IOException, ServiceException;

	void removePhoto(String albumId, String photoId) 
			throws MalformedURLException, IOException, ServiceException;

	AlbumEntry findAlbum(String albumId) 
			throws MalformedURLException, IOException, ServiceException;
		
	PhotoEntry upload(String albumId, byte[] data, String name) 
			throws MalformedURLException, IOException, ServiceException;
	
	AlbumEntry findAlbumByTitle(String title)
		throws MalformedURLException, IOException, ServiceException;
	
	List<PhotoEntry> findPhotos(String title, int count)
		throws MalformedURLException, IOException, ServiceException;
	
}

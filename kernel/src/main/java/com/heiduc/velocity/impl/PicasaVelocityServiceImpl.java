

package com.heiduc.velocity.impl;

import java.util.Collections;
import java.util.List;

import com.heiduc.business.Business;
import com.heiduc.common.AbstractServiceBeanImpl;
import com.heiduc.service.vo.AlbumVO;
import com.heiduc.service.vo.PhotoVO;
import com.heiduc.velocity.PicasaVelocityService;

/**
 * 
 * @author Alexander Oleynik
 *
 */
public class PicasaVelocityServiceImpl extends AbstractServiceBeanImpl 
		implements PicasaVelocityService {

	public PicasaVelocityServiceImpl(Business business) {
		super(business);
	}

	@Override
	public AlbumVO findAlbumById(String albumId) {
		try {
			return new AlbumVO(getBusiness().getPicasaBusiness().findAlbum(
					albumId));
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	@Override
	public AlbumVO findAlbumByTitle(String title) {
		try {
			return new AlbumVO(getBusiness().getPicasaBusiness()
					.findAlbumByTitle(title));
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	@Override
	public List<AlbumVO> findAlbums() {
		try {
			return AlbumVO.create(getBusiness().getPicasaBusiness()
					.selectAlbums());
		}
		catch (Exception e) {
			logger.error(e.getMessage());
			return Collections.EMPTY_LIST;
		}
	}

	@Override
	public List<PhotoVO> findPhotosInAlbum(String albumId) {
		try {
			return PhotoVO.create(getBusiness().getPicasaBusiness().selectPhotos(
					albumId));
		}
		catch (Exception e) {
			logger.error(e.getMessage());
			return Collections.EMPTY_LIST;
		}
	}

	@Override
	public List<PhotoVO> findPhotos(String title, int count) {
		try {
			return PhotoVO.create(getBusiness().getPicasaBusiness().findPhotos(
					title, count));
		}
		catch (Exception e) {
			logger.error(e.getMessage());
			return Collections.EMPTY_LIST;
		}
	}
}

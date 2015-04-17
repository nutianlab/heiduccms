

package com.heiduc.servlet;

import java.io.BufferedOutputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.heiduc.business.decorators.TreeItemDecorator;
import com.heiduc.common.HeiducContext;
import com.heiduc.entity.ConfigEntity;
import com.heiduc.entity.FileEntity;
import com.heiduc.entity.FolderEntity;
import com.heiduc.global.CacheService;
import com.heiduc.global.FileCacheItem;
import com.heiduc.i18n.Messages;
import com.heiduc.utils.DateUtil;
import com.heiduc.utils.FolderUtil;

/**
 * Servlet for download files from database.
 * 
 * @author Aleksandr Oleynik
 */
public class FileDownloadServlet extends AbstractServlet {
	
	private static final long serialVersionUID = 6098745782027999297L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String[] chain = FolderUtil.getPathChain(request.getPathInfo());
		if (chain.length == 0) {
			response.sendError(response.SC_NOT_FOUND, Messages.get(
					"file.not_specified"));
			return;
		}
		if (servedFromPublicCache(request.getPathInfo(), request, response)) {
			logger.info("from public cache " + request.getPathInfo());
			return;
		}	
		String filename = chain[chain.length-1];		
		/*TreeItemDecorator<FolderEntity> tree = getBusiness().getFolderBusiness()
				.getTree();
		TreeItemDecorator<FolderEntity> folder = getBusiness().getFolderBusiness()
				.findFolderByPath(tree, FolderUtil.getFilePath(
						request.getPathInfo()));*/
		
		FolderEntity folder = getBusiness().getFolderBusiness()
		.getByPath( FolderUtil.getFilePath(request.getPathInfo() ) );	
		
		if (folder == null) {
			response.sendError(response.SC_NOT_FOUND, Messages.get(
					"folder.not_found", request.getPathInfo()));
			return;
		}
//		if (isAccessDenied(folder.getEntity())) {
		if (isAccessDenied(folder)) {
			response.sendError(response.SC_FORBIDDEN, Messages.get(
					"access_denied"));
			return;
		}
		if (servedFromCache(request.getPathInfo(), request, response)) {
			logger.info("from cache " + request.getPathInfo());
			return;
		}
//		FileEntity file = getDao().getFileDao().getByName(				folder.getEntity().getId(), filename);
		FileEntity file = getDao().getFileDao().getByName(
				folder.getId(), filename);
		if (file != null) {
			logger.info("not in cache " + request.getPathInfo());
			byte[] content = getDao().getFileDao().getFileContent(file);
			if (file.getSize() < CacheService.MEMCACHE_LIMIT) {
				getSystemService().getFileCache().put(request.getPathInfo(), 
						new FileCacheItem(file, content, 
								HeiducContext.getInstance().getUser() == null));
			}
			sendFile(file, content, request, response);
		}
		else {
			response.sendError(response.SC_NOT_FOUND, Messages.get(
					"file.not_found"));
	        logger.debug("not found file " + request.getPathInfo());
		}
	}
	
	private boolean servedFromPublicCache(final String path,
			HttpServletRequest request,	HttpServletResponse response) 
			throws IOException {
		FileCacheItem item = getSystemService().getFileCache().get(path);
		if (item != null && item.isPublicCache()) {
			if (getCache().getResetDate() == null
					|| item.getTimestamp().after(getCache().getResetDate())) {
				sendFile(item.getFile(), item.getContent(), request, response);
				return true;
			}
		}
		return false;
	}

	private CacheService getCache() {
		return getSystemService().getCache();
	}
	
	private boolean servedFromCache(final String path,
			HttpServletRequest request,	HttpServletResponse response) 
			throws IOException {
		FileCacheItem item = getSystemService().getFileCache().get(path);
		if (item != null) {
			if (getCache().getResetDate() == null
					|| item.getTimestamp().after(getCache().getResetDate())) {
				sendFile(item.getFile(), item.getContent(), request, response);
				return true;
			}
		}
		return false;
	}

	private void sendFile(final FileEntity file, byte[] content, 
			HttpServletRequest request,	HttpServletResponse response) 
			throws IOException {
		if(DateUtil.toHeaderString(file.getLastModifiedTime()).equals(
				request.getHeader("If-Modified-Since"))){
			response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
			return;
		}
		else {
			response.setHeader("Content-type", file.getMimeType());
			
			response.setHeader("Content-Length", String.valueOf(file.getSize()));
			response.setHeader("Last-Modified", 
					DateUtil.toHeaderString(file.getLastModifiedTime()));
			BufferedOutputStream output = new BufferedOutputStream(
					response.getOutputStream());
			output.write(content);
			output.flush();
			output.close();
		}
	}

	private boolean isAccessDenied(FolderEntity folder) {
		if (HeiducContext.getInstance().getUser() == null) {
			return getBusiness().getFolderPermissionBusiness()
					.getGuestPermission(folder).isDenied();
		}
		return getBusiness().getFolderPermissionBusiness()
				.getPermission(folder, HeiducContext.getInstance().getUser())
				.isDenied();
	}
	
}
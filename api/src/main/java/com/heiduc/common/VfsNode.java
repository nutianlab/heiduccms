

package com.heiduc.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.cache.CacheException;

//import net.sf.jsr107cache.CacheException;

import com.heiduc.global.CacheService;
import com.heiduc.global.SystemService;
import com.heiduc.utils.FolderUtil;

public class VfsNode implements Serializable {

	private List<String> children;
	private boolean directory;
	private String path;
	private int size;
	private byte[] data;
	private long timestamp;
	
	public VfsNode() {
		children = new ArrayList<String>();
		timestamp = System.currentTimeMillis();
	}
	
	public VfsNode(String path) {
		this();
		this.path = path;
		this.directory = true;
	}
	
	public VfsNode(String path, byte[] data) {
		this();
		this.path = path;
		this.directory = false;
		this.data = data;
		size = data.length;
	}

	public String getKey() {
		return createKey(path);
	}
	
	private String getBlobKey() {
		return createBlobKey(path);
	}

	public List<VfsNode> getChildren() {
		try {
			return new ArrayList<VfsNode>(getSystemService().getCache().getAll(
					createKeys(children)).values());
		} catch (CacheException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public VfsNode addFile(String path, byte[] data) {
		children.add(path);
		save();
		VfsNode node = new VfsNode(path, data);
		return node.save();
	}
	
	public VfsNode addDirectory(String path) {
		children.add(path);
		save();
		VfsNode node = new VfsNode(path);
		return node.save();
	}

	public boolean isDirectory() {
		return directory;
	}
	
	public void setDirectory(boolean directory) {
		this.directory = directory;
	}
	
	public String getPath() {
		return path;
	}
	
	public void setPath(String path) {
		this.path = path;
	}
	
	public byte[] getData() {
		if (size > CacheService.MEMCACHE_LIMIT && data == null) {
			data = getSystemService().getCache().getBlob(getBlobKey());
		}
		return data;
	}
	
	public void setData(byte[] data) {
		this.data = data;
		size = data.length;
	}
	
	public VfsNode save() {
		if (size > CacheService.MEMCACHE_LIMIT) {
			getSystemService().getCache().putBlob(getBlobKey(), data);
			data = null;
		}
		getSystemService().getCache().put(getKey(), this);
		return this;
	}
	
	
	
	
	private static SystemService getSystemService() {
		return HeiducContext.getInstance().getBusiness().getSystemService();
	}
	
	private static String createKey(String path) {
		return "vfs:" + path;
	}
	
	private static String createBlobKey(String path) {
		return "vfs_data:" + path;
	}

	private static Set<String> createKeys(List<String> paths) {
//		List<String> result = new ArrayList<String>();
		Set<String> result = new HashSet<String>();
		for (String path : paths) {
			result.add(createKey(path));
		}
		return result;
	}
	
	public static VfsNode find(String path) {
		VfsNode node = (VfsNode)getSystemService().getCache().get(
				createKey(path));
		if (node != null && node.timestamp < getVfsTimestamp()) {
			node = null;
		}
		return node;
	}
	
	private static VfsNode getParent(String path) {
		String parentPath = FolderUtil.getParentPath(path);
		if (parentPath == null) {
			parentPath = "/";
		}
		VfsNode parent = find(parentPath);
		if (parent == null || !parent.isDirectory()) {
			parent = createDirectory(parentPath);
		}
		return parent;
	}
	
	public static VfsNode createDirectory(String path) {
		if (path.equals("/")) {
			return (new VfsNode(path)).save();
		}
		return getParent(path).addDirectory(path);		
	}

	public static VfsNode createFile(String path, byte[] data) {
		return getParent(path).addFile(path, data);
	}
	
	private static final String VFS_TIMESTAMP = "vfsTimestamp";
	
	public static void reset() {
		getSystemService().getCache().put(VFS_TIMESTAMP, 
				System.currentTimeMillis());
	}
	
	private static long getVfsTimestamp() {
		Long t = (Long)getSystemService().getCache().get(VFS_TIMESTAMP);
		if (t == null) {
			return 0;
		}
		return t;
	}
	
}

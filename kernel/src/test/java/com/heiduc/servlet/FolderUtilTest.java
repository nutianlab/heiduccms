

package com.heiduc.servlet;

import java.io.UnsupportedEncodingException;

import com.heiduc.utils.FolderUtil;

import junit.framework.TestCase;

public class FolderUtilTest extends TestCase {

	public void testGetPathChain() throws UnsupportedEncodingException {
		String path = "/one/two/free/image.jpg";
		String[] chain = FolderUtil.getPathChain(path);
		assertNotNull(chain);
		assertEquals(4, chain.length);
		assertEquals("one", chain[0]);
		assertEquals("two", chain[1]);
		assertEquals("free", chain[2]);
		assertEquals("image.jpg", chain[3]);
	}

	public void testGetFolderChain() throws UnsupportedEncodingException {
		String path = "/one/two/free/image.jpg";
		String[] chain = FolderUtil.getPathChain(path);
		assertNotNull(chain);
		String[] folderChain = FolderUtil.getFolderChain(chain);
		assertNotNull(folderChain);
		assertEquals(3, folderChain.length);
		assertEquals("one", folderChain[0]);
		assertEquals("two", folderChain[1]);
		assertEquals("free", folderChain[2]);
	}
	
	public void testGetFileExt() {
		assertEquals("zip", FolderUtil.getFileExt("/home/x/file.zip"));
	}

	public void testGetFilePath() {
		assertEquals("/home/x", FolderUtil.getFilePath("/home/x/file.zip"));
	}

	public void testGetFileName() {
		assertEquals("file.zip", FolderUtil.getFileName("/home/x/file.zip"));
	}

	public void testGetFolderName() {
		assertEquals("file", FolderUtil.getFolderName("/home/x/file"));
	}
}

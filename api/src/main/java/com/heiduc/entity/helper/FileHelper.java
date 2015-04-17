

package com.heiduc.entity.helper;

import java.util.Comparator;

import com.heiduc.entity.FileEntity;

public class FileHelper {

	public static class NameAsc implements Comparator<FileEntity> {
		@Override
		public int compare(FileEntity o1, FileEntity o2) {
			return o1.getFilename().compareToIgnoreCase(o2.getFilename());
		}
	}
	
}



package com.heiduc.search.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.heiduc.business.Business;
import com.heiduc.common.HeiducContext;
import com.heiduc.dao.Dao;
import com.heiduc.entity.ContentEntity;
import com.heiduc.entity.FileEntity;
import com.heiduc.entity.PageEntity;
import com.heiduc.search.Hit;
import com.heiduc.search.SearchIndex;
import com.heiduc.search.SearchResultFilter;
import com.heiduc.utils.StrUtil;
import com.heiduc.utils.StreamUtil;

public class SearchIndexImpl implements SearchIndex {

	private static final Log logger = LogFactory.getLog(
			SearchIndexImpl.class);

	private static final String INDEX_MOD_DATE = "IndexModDate";

	private String language;
	private Map<String, Set<Long>> index;
	private Date indexModDate;

	public SearchIndexImpl(String aLanguage) {
		try {			
			language = aLanguage;
			loadIndex();
		} catch (IOException e) {
			logger.error(StreamUtil.getStackTrace(e));
		}
	}
	
	@Override
	public void updateIndex(Long pageId) throws IOException{
		PageEntity page = getDao().getPageDao().getById(pageId);
		if (page == null) {
			return;
		}
		refreshIndex();
		List<PageEntity> versions = getDao().getPageDao().selectByUrl(
				page.getFriendlyURL());
		for (PageEntity version : versions) {
			removeFromIndex(version.getId());
		}
		page = getDao().getPageDao().getByUrl(page.getFriendlyURL());
		if (page == null) {
			return;
		}
		if (!page.isSearchable()) {
			return;
		}
		String content = getDao().getPageDao().getContent(page.getId(), 
				getLanguage());
		if (content == null) {
			return;
		}
		String data = StrUtil.extractSearchTextFromHTML(content.toLowerCase());
		String[] words = StrUtil.splitByWord(data);
		//logger.info(Arrays.asList(words));
		for (String word : words) {
			if (word.length() < 3) {
				continue;
			}
			
			word = StrUtil.removeAccents(word);
			
			if (!getIndex().containsKey(word)) {
				getIndex().put(word, new HashSet<Long>());
			}
			if (!getIndex().get(word).contains(page.getId())) {
				getIndex().get(word).add(page.getId());
			}
		}
	}
	
	@Override
	public void removeFromIndex(Long pageId) {
		for (Set<Long> pages : getIndex().values()) {
			pages.remove(pageId);
		}
	}
	
	@Override
	public void saveIndex() throws IOException {
			byte[] indexContent = StrUtil.zipStringToBytes(indexToString());
			FileEntity file = getBusiness().getFileBusiness()
					.saveFile(getIndexFilename(), indexContent);
			indexModDate = file.getLastModifiedTime();
			getBusiness().getSystemService().getCache().getMemcache().put(
					getIndexKey(), indexModDate);
	}

	private String indexToString() {
		StringBuffer buf = new StringBuffer();
		int i = 0;
		for (String word : getIndex().keySet()) {
			if (getIndex().get(word).isEmpty()) {
				continue;
			}
			buf.append(i++ == 0 ? "" : ":").append(word).append("=");
			int j = 0;
			for (Long id : getIndex().get(word)) {
				buf.append(j++ == 0 ? "" : ",").append(id);
			}
		}
		return buf.toString();
	}

	@Override
	public List<Hit> search(SearchResultFilter filter, String query, 
			int textSize) {
		logger.info("into index.search in " + language);
		List<Hit> result = new ArrayList<Hit>();
				
		try {
			refreshIndex();
			
			List<Long> pages = new ArrayList<Long>(getPageIds(query));
			
			logger.info("Number of pages found = " + pages.size());
			
			for (Long pageId : pages) {
				PageEntity page = getDao().getPageDao().getById(pageId);
				if (page != null) {
					if (filter != null && !filter.check(page)) {
						logger.info("page skipped by filter");
						continue;
					}
					ContentEntity content = getBusiness().getPageBusiness()
							.getPageContent(page, language);
					if (content != null) {
						logger.info("Content found for pageId = " + pageId + " in " + language);
						String text = StrUtil.extractSearchTextFromHTML(
								content.getContent());
						if (text.length() > textSize) {
							text = text.substring(0, textSize);
						}
						result.add(new Hit(page, text, language));
					}
					else {
						logger.error("Content not found for pageId = " + pageId);
					}
				}
				else {
					logger.error("Page not found " + pageId + ". Rebuild index.");
				}
			}	
			
		} catch (IOException e) {
			
			logger.error(StreamUtil.getStackTrace(e));
		}
		logger.info("out of index.search");
		return result;
	}
	
	@Override
	public List<PageEntity> search(SearchResultFilter filter, String query) {
		
		logger.info("into index.search in " + language);
		List<PageEntity> result = new ArrayList<PageEntity>();
				
		try {
			refreshIndex();
			
			List<Long> pages = new ArrayList<Long>(getPageIds(query));
			
			logger.info("Number of pages found = " + pages.size());
			
			for (Long pageId : pages) {
				PageEntity page = getDao().getPageDao().getById(pageId);
				if (page != null && page.isStructured()) {
					if (filter != null && !filter.check(page)) {
						logger.info("page skipped by filter");
						continue;
					}
					ContentEntity content = getBusiness().getPageBusiness()
							.getPageContent(page, language);
					if (content != null) {
						
						result.add(page);
					}
					else {
						logger.error("Content not found for pageId = " + pageId);
					}
				}
				else {
					logger.error("Page not found " + pageId + ". Rebuild index.");
				}
			}	
			
		} catch (IOException e) {
			
			logger.error(StreamUtil.getStackTrace(e));
		}
		logger.info("out of index.search");
		return result;
		
	}

	private Set<Long> getPageIds(String query) {
		String[] tmpWords = StrUtil.splitByWord(query);
		if (tmpWords.length == 0) {
			return Collections.emptySet();
		}
		
		int nbWords = 0;
		
		/* 
		 * query doesn't take account 
		 * words with length < 3 
		 */
		for (String tmpWord : tmpWords) {
			if (tmpWord.length() >= 3) 
				nbWords++;
		}
		
		String[] words = new String[nbWords];
		
		int i = 0;
		for (String tmpWord : tmpWords) {
			if (tmpWord.length() >= 3) { 
				words[i] = tmpWord;
				i++;
			}
		}			
		
		Set<Long> keys = null;
		i = 0;
		
		for (String word : words) {	
			if (i == 0) {
				keys = getPageKeys(StrUtil.removeAccents(word));
				i++;
			}
			
			else {
				keys = keysLogicalAnd(keys, getPageKeys(StrUtil.removeAccents(word)));
			}			
		}
		return keys;		
	}	
		
	private Set<Long> keysLogicalAnd(Set<Long> l1, Set<Long> l2) {
		Set<Long> result = new HashSet<Long>();
		for (Long i : l1) {
			if (l2.contains(i) && !result.contains(i)) {
				result.add(i);
			}
		}
		return result;
	}	
	
	private Set<Long> getPageKeys(String word) {
		if (getIndex().containsKey(word)) {
			return getIndex().get(word);
		}
		return Collections.emptySet();
	}

	private String getIndexKey() {
		return INDEX_MOD_DATE + getLanguage();
	}
	
	private void refreshIndex() throws IOException {
		Date date = (Date) getBusiness().getSystemService().getCache()
				.getMemcache().get(getIndexKey());
		if (index == null || date == null || !date.equals(indexModDate)) {
			loadIndex();
		}
	}

	private String getIndexFilename() {
		return "/tmp/index_" + getLanguage() + ".bin";
	}
	
	private void loadIndex() throws IOException {
		logger.info("into loadIndex");
		
		index = new HashMap<String, Set<Long>>();
		indexModDate = null;
		FileEntity file = getBusiness().getFileBusiness()
				.findFile(getIndexFilename());
		if (file == null) {
			logger.error("Index File not found. " + getIndexFilename());
			logger.info("out of loadIndex");
			return;
		}
		byte[] data = getDao().getFileDao().getFileContent(file);
		if (data != null) {
			String strIndex = StrUtil.unzipStringFromBytes(data);
			indexFromString(strIndex);
			indexModDate = file.getLastModifiedTime();
			Date dt = (Date)getBusiness().getSystemService().getCache()
					.getMemcache().get(getIndexKey());
			if (dt == null || dt.before(indexModDate)) {
				getBusiness().getSystemService().getCache().getMemcache()
						.put(getIndexKey(), indexModDate);
			}
		}
		else {
			logger.error("Search index is empty. " + getIndexFilename());
		}
		logger.info("out of loadIndex");
	}

	private void indexFromString(String data) {
		if (StringUtils.isEmpty(data)) {
			return;
		}
		for (String wordBuf : data.split("\\:")) {
			//logger.info(wordBuf);
			String[] wordStruc = wordBuf.split("\\=");
			if (wordStruc.length != 2 ) {
				logger.error("Problem with index " + wordBuf);
				continue;
			}
			index.put(wordStruc[0], new HashSet<Long>());
			for (String key : wordStruc[1].split(",")) {
				index.get(wordStruc[0]).add(Long.valueOf(key));
			}
		}
	}

	private Business getBusiness() {
		return HeiducContext.getInstance().getBusiness();
	}
	
	private Dao getDao() {
		return getBusiness().getDao();
	}
	
	@Override
	public String getLanguage() {
		return language;
	}

	private Map<String, Set<Long>> getIndex() {
		if (index == null) {
			index = new HashMap<String, Set<Long>>();
		}
		return index;
	}

	@Override
	public void clear() {
		getIndex().clear();
	}
	
}

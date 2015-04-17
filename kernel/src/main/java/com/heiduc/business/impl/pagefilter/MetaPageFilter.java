

package com.heiduc.business.impl.pagefilter;

import org.apache.commons.lang.StringUtils;

import com.heiduc.entity.PageEntity;
import com.heiduc.utils.StrUtil;

/**
 * 
 * @author Alexander Oleynik
 *
 */
public class MetaPageFilter implements PageFilter {
	
	@Override
	public String apply(String content, PageEntity page) {
		String result = content;
		if (!StringUtils.isEmpty(page.getDescription())) {
			String description = "<meta name=\"description\" content=\"" 
					+ page.getDescription() + "\" />"; 
			if (StrUtil.DESCRIPTION_PATTERN.matcher(result).find()) {
				result = result.replaceAll(StrUtil.DESCRIPTION_REGEX,
						description);
			}
			else {
				result = result.replaceAll(StrUtil.HEAD_CLOSE_REGEX, description
						+ "\n</head>");
			}
		}
		if (!StringUtils.isEmpty(page.getKeywords())) {
			String keywords = "<meta name=\"keywords\" content=\"" 
					+ page.getKeywords() + "\" />";
			if (StrUtil.KEYWORDS_PATTERN.matcher(result).find()) {
				result = result.replaceAll(StrUtil.KEYWORDS_REGEX,
						keywords);
			}
			else {
				result = result.replaceAll(StrUtil.HEAD_CLOSE_REGEX, keywords
						+ "\n</head>");
			}
		}
		return result;
	}

}

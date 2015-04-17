

package com.heiduc.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import junit.framework.TestCase;

public class StrUtilTest extends TestCase {

	private static final Log logger = LogFactory.getLog(StrUtilTest.class);
	
	public void testExtrtactSearchTextFromHTML() {
		String html = "<b>\nb\n</b> re <script type=\"text/javascript\">test \n\nvar x = 0; \nfunction f() {};\n</script>d";
		String text = StrUtil.extractSearchTextFromHTML(html);
		//logger.info("$" + text + "-");
		assertEquals(text, " b  re d", text);
		assertEquals("test", 
				StrUtil.extractSearchTextFromHTML("test${plugin.form.render()}"));
		assertEquals("test", 
				StrUtil.extractSearchTextFromHTML("test## my velocity comment"));
		assertEquals("test", 
				StrUtil.extractSearchTextFromHTML("test#for ($i in $pages)"));
		assertEquals("test", 
				StrUtil.extractSearchTextFromHTML("test#set($i = 0)"));
		assertEquals("test", 
				StrUtil.extractSearchTextFromHTML("test$page.friendlyURL"));
		assertEquals("test", 
				StrUtil.extractSearchTextFromHTML("test$service.findContent(1, 2)"));
		assertEquals("test", 
				StrUtil.extractSearchTextFromHTML("test#if ($d == 5)"));
		assertEquals("test", 
				StrUtil.extractSearchTextFromHTML("test#end"));
		assertEquals("test", 
				StrUtil.extractSearchTextFromHTML("test<![CDATA["));
		assertEquals("test", 
				StrUtil.extractSearchTextFromHTML("test]]>"));
		assertEquals("test ", 
				StrUtil.extractSearchTextFromHTML("test&nbsp;</p>"));
		assertEquals("test            ", 
				StrUtil.extractSearchTextFromHTML("test&nbsp;&nbsp;&gt;&lt;-*=/&|()"));
		assertEquals("привет ", 
				StrUtil.extractSearchTextFromHTML("привет&nbsp;"));
	}
	
	public void testSplitByWord() {
		String data = "Hello my friend. \n I am! May \nbe I? Привет лунатикам!";
		String[] words = StrUtil.splitByWord(data);
		assertEquals(10, words.length);
	}
	
	public void testReplaceDescriptionMeta() {
		assertEquals("<html><head>hello</head></html>", 
				"<html><head><meta  name=\"description\"  content=\"abc\" /></head></html>".replaceAll(
						StrUtil.DESCRIPTION_REGEX, "hello"));
		assertEquals("<html><head>hello</head></html>", 
				"<html><head><meta content=\"abc\"  name=\"description\" /></head></html>".replaceAll(
						StrUtil.DESCRIPTION_REGEX, "hello"));
		assertEquals("<html><head>hello</head></html>", 
				"<html><head><META  name=\"description\" content=\"abc\" /></head></html>".replaceAll(
				StrUtil.DESCRIPTION_REGEX, "hello"));
		assertEquals("<html><head>hello</head></html>", 
				"<html><head><META NAME=\"description\" content=\"abc\" /></head></html>".replaceAll(
						StrUtil.DESCRIPTION_REGEX, "hello"));
		assertEquals("<html><head>hello</head></html>", 
				"<html><head><META NAME=\"DESCRIPTION\" content=\"abc\" /></head></html>".replaceAll(
						StrUtil.DESCRIPTION_REGEX, "hello"));
	}
	
	public void testHeadCloseRegex() {
		assertTrue(StrUtil.HEAD_CLOSE_PATTERN.matcher("<html><head>  </head></html>").find());
		assertTrue(StrUtil.HEAD_CLOSE_PATTERN.matcher("<html><head>  </HEAD></html>").find());
	}
	
}

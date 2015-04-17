package com.heiduc.bliki;

import info.bliki.htmlcleaner.ContentToken;
import info.bliki.htmlcleaner.TagNode;
import info.bliki.wiki.filter.Encoder;
import info.bliki.wiki.filter.WikipediaParser;
import info.bliki.wiki.model.Configuration;
import info.bliki.wiki.model.ImageFormat;
import info.bliki.wiki.model.WikiModel;
import info.bliki.wiki.tags.WPATag;
import info.bliki.wiki.tags.extension.ChartTag;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.heiduc.common.HeiducContext;
import com.heiduc.dao.Dao;
import com.heiduc.entity.ContentEntity;
import com.heiduc.entity.LanguageEntity;
import com.heiduc.entity.PageEntity;
import com.heiduc.entity.TemplateEntity;

/**
 * A wiki model for the <a href="http://code.google.com/p/gwtwiki/">Java
 * Wikipedia API (Bliki engine)</a>
 * 
 */
public class HeiducWikiModel extends WikiModel {
	
	private static final Log logger = LogFactory.getLog(HeiducWikiModel.class);
	
	final PageEntity page;

	static {
		// allow style attributes in tags like div and span. 
		// May be a security risk cause of Cross Site Scripting XSS.
		TagNode.addAllowedAttribute("style");
		// Add &lt;chart&gt; tag for Google Chart API
		// See http://code.google.com/p/gwtwiki/wiki/TagExtensions
		Configuration.DEFAULT_CONFIGURATION.addTokenTag("chart", new ChartTag());
	}

	public HeiducWikiModel(PageEntity page) {
		super("", "");
		this.page = page;
		fExternalImageBaseURL = "/file/page/${image}";
		fExternalWikiBaseURL = "${title}";
		if (!page.isRoot()) {
			fExternalImageBaseURL = "/file/page" + page.getFriendlyURL() 
					+ "/${image}";
			if (!page.getParentUrl().equals("/")) {
				fExternalWikiBaseURL = page.getParentUrl() + "/${title}";
			}else{
				fExternalWikiBaseURL = page.getFriendlyURL() + "/${title}";
			}
		}
	}

	/**
	 * Append an internal wikilink as described in <a
	 * href="http://en.wikipedia.org/wiki/Help:Link#Wikilinks">Help Links</a>
	 * with the exception, that the link is converted to lower case letters.
	 * 
	 * @param topic
	 * @param hashSection
	 * @param topicDescription
	 * @param cssClass
	 *            the links CSS class style
	 * @param parseRecursive
	 *            parse the topic description recurseily
	 */
	@Override
	public void appendInternalLink(String topic, String hashSection,
			String topicDescription, String cssClass, boolean parseRecursive) {
		String hrefLink;
		if (topic.length() > 0) {
			String encodedtopic = encodeTitleToUrl(topic, true).toLowerCase();
			if (encodedtopic.startsWith("/")) {
				hrefLink = encodedtopic;
			}
			else {
				hrefLink = fExternalWikiBaseURL.replace("${title}", encodedtopic);
			}
		} else {
			if (hashSection != null) {
				hrefLink = "";
			} else {
				hrefLink = fExternalWikiBaseURL.replace("${title}", "");
			}
		}

		WPATag aTagNode = new WPATag();
		aTagNode.addAttribute("title", topic, true);
		String href = hrefLink;
		if (hashSection != null) {
			href = href + '#' + encodeTitleDotUrl(hashSection, true);
		}
		aTagNode.addAttribute("href", href, true);
		if (cssClass != null) {
			aTagNode.addAttribute("class", cssClass, true);
		}
		aTagNode.addObjectAttribute("wikilink", topic);

		pushNode(aTagNode);
		if (parseRecursive) {
			WikipediaParser.parseRecursive(topicDescription.trim(), this,
					false, true);
		} else {
			aTagNode.addChild(new ContentToken(topicDescription));
		}
		popNode();
	}

	private Dao getDao() {
		return HeiducContext.getInstance().getBusiness().getDao();
	}

	/**
	 * Get the raw text of the MediaWiki template. 
	 * 
	 * See: http://en.wikipedia.org/wiki/Help:Template
	 */
	@Override
	public String getRawWikiContent(String namespace, String articleName, 
			Map<String, String> templateParameters) {
		String result = super.getRawWikiContent(namespace, articleName, 
				templateParameters);
		if (result != null) {
			// found magic word template
			return result;
		}
		String encodedtopic = encodeTitleToUrl(articleName, true).toLowerCase();
		if (isTemplateNamespace(namespace)) {
			String name;
			if (encodedtopic.startsWith("/")) {
				name = encodedtopic;
			} else {
				name = fExternalWikiBaseURL.replace("${title}", encodedtopic);
			}

			PageEntity page = getDao().getPageDao().getByUrl(name);
			if (page != null) {
				String languageCode = LanguageEntity.ENGLISH_CODE;
				ContentEntity content = getDao().getContentDao().getByLanguage(
						PageEntity.class.getName(), page.getId(), languageCode);
				if (content != null) {
					return content.getContent();
				}
			}
		}
		return null;
	}

	/**
	 * Check if the given namespace is an image namespace.
	 * 
	 * @param namespace
	 * @return <code>true</code> if the namespace is a image namespace.
	 */
	@Override
	public boolean isImageNamespace(String namespace) {
		if (super.isImageNamespace(namespace)) {
			return true;
		}
		// allow links to the themes images with their name as a namespace:
		return isHeiducTemplateNamespace(namespace);
	}

	private boolean isHeiducTemplateNamespace(String namespace) {
		for (TemplateEntity template : getDao().getTemplateDao().select()) {
			if (template.getUrl().equalsIgnoreCase(namespace)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Append the internal wiki image link to this model.<br/>
	 * <br/>
	 * See <a href="http://en.wikipedia.org/wiki/Image_markup">Image markup</a>
	 * and see <a
	 * href="http://www.mediawiki.org/wiki/Help:Images">Help:Images</a>.
	 * 
	 * @param imageNamespace
	 *            the image namespace
	 * @param rawImageLink
	 *            the raw image link text without the surrounding
	 *            <code>[[...]]</code>
	 */
	@Override
	public void parseInternalImageLink(String imageNamespace,
			String rawImageLink) {
		if (fExternalImageBaseURL != null) {
			String imageSrc = null;
			if (isHeiducTemplateNamespace(imageNamespace)) {
				// link to the theme images:
				imageSrc = "/file/theme/" + imageNamespace.toLowerCase() 
						+ "/images/${image}";
			} else {
				// link to the resources of the page entity:
				imageSrc = fExternalImageBaseURL;
			}
			ImageFormat imageFormat = ImageFormat.getImageFormat(rawImageLink,
					imageNamespace);

			String imageName = imageFormat.getFilename();
			imageName = Encoder.encodeUrl(imageName);
			imageSrc = imageSrc.replace("${image}", imageName);

			appendInternalImageLink(imageSrc, imageSrc, imageFormat);
		}
	}

	/**
	 * Activate the rendering of &lt;math&gt; tags 
	 * through the <a href="http://www.mathtran.org">MathTran.org</a> service
	 */
	@Override
	public boolean isMathtranRenderer() {
		return true;
	}

}
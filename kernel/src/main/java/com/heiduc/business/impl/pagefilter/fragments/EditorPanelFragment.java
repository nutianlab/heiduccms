

package com.heiduc.business.impl.pagefilter.fragments;


import com.heiduc.business.Business;
import com.heiduc.business.impl.pagefilter.ContentFragment;
import com.heiduc.common.HeiducContext;
import com.heiduc.entity.PageEntity;

public class EditorPanelFragment implements ContentFragment {

	@Override
	public String get(Business business, PageEntity page) {
		if (HeiducContext.getInstance().getUser() != null 
			&& HeiducContext.getInstance().getUser().isEditor()) {
			StringBuffer code = new StringBuffer( 
				"<div id=\"editor-panel\"" 
				+	"style=\"border-bottom: 1px solid #a5c4d5;"
				+ "padding: 4px; position: fixed; left: 0; top: 0; width: 100%;"
				+ "background-color: white; opacity:0.2; z-index: 1;\">"
				+ "<div style=\"float:left\">"
				+ "<a style=\"padding: 2px 4px; margin-left: 2px;\" href=\"/cms\">Heiduc</a> CMS"
				+ "<a style=\"padding: 2px 4px; margin-left: 2px;\" href=\"/cms#page/content/");
			code.append(page.getId()).append("\">Edit page</a>"
				+ "<a style=\"padding: 2px 4px; margin-left: 2px;\" href=\"/cms#pages\">Content</a>"
				+ "<a style=\"padding: 2px 4px; margin-left: 2px;\" href=\"/cms#folders\">Resources</a>"
				+ "</div>"
				+ "<div style=\"float:right;margin-right:10px;\">"
				+ HeiducContext.getInstance().getUser().getEmail()
				+ " | <a style=\"padding: 2px 4px; margin-left: 2px;\" href=\"/cms#profile\">Profile</a>" 
				+ " | <a style=\"padding: 2px 4px; margin-left: 2px;\" href=\"http://issues.heiduc.com/cms/list\">Support</a>"
				+ " | <a style=\"padding: 2px 4px; margin-left: 2px;\" href=\"/_ah/logout\">Logout</a>"
				+ " | <a href=\"#\" onclick=\"hidePanel()\">Hide</a> "
				+ "</div>"
				+ "<span style=\"clear:both\">&#160;</span>"
				+ "</div>"
				+ "<div id=\"editor-pin\""
				+ "    style=\"border: 1px solid silver;display:none;"
				+ "    padding: 4px; position: fixed; left: 0; bottom: 0; width: 76px;"
				+ "    background-color: white; z-index: 1;\">"
				+ "  <a href=\"#\" onclick=\"showPanel()\">Show panel</a>"
				+ "</div>"
				+ "<script type=\"text/javascript\">"
				+ " $(function() {"
				+ "   if ($.cookie('hideEditorPanel')) hidePanel();"
				+ "   $('#editor-panel').hover(function(){ $('#editor-panel').css('opacity','1.0'); }, "
				+ "      function(){ $('#editor-panel').css('opacity','0.1'); });"
				+ " });"
				+ " function hidePanel() {"
				+ "   $('#editor-panel').hide();"
				+ "   $('#editor-pin').show();"
				+ "   $.cookie('hideEditorPanel', 'yes');"
				+ " }"
				+ " function showPanel() {"
				+ "   $('#editor-panel').show();"
				+ "   $('#editor-pin').hide();"
				+ "   $.cookie('hideEditorPanel', null);"
				+ " }"
				+ "</script>");
			return code.toString();
		}
		return "";
	}

}

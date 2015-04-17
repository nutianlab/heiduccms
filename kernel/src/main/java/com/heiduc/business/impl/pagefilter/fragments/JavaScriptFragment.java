

package com.heiduc.business.impl.pagefilter.fragments;


import com.heiduc.business.Business;
import com.heiduc.business.impl.pagefilter.ContentFragment;
import com.heiduc.common.HeiducContext;
import com.heiduc.entity.ConfigEntity;
import com.heiduc.entity.PageEntity;

public class JavaScriptFragment	implements ContentFragment {

	@Override
	public String get(Business business, PageEntity page) {
		ConfigEntity config = HeiducContext.getInstance().getConfig();
		StringBuffer code = new StringBuffer( 
		    "<script src=\"/static/js/jquery.js\" type=\"text/javascript\"></script>\n"
		    //"<script src=\"http://ajax.googleapis.com/ajax/libs/jquery/1.4.2/jquery.min.js\" type=\"text/javascript\"></script>\n"
		    +  "<script src=\"/static/js/jquery.form.js\" type=\"text/javascript\"></script>\n"
            +  "<script src=\"/static/js/jsonrpc.js\" type=\"text/javascript\"></script>\n"
            +  "<script src=\"/static/js/heiduc.js\" type=\"text/javascript\"></script>\n");
		if (config.isEnableRecaptcha()) {
            code.append("<script src=\"http://api.recaptcha.net/js/recaptcha_ajax.js\" type=\"text/javascript\" ></script>\n");
		}
		if (HeiducContext.getInstance().getUser() != null) {
			code.append("<script src=\"/static/js/jquery.cookie.js\" type=\"text/javascript\"></script>\n");
		}
		return code.toString();
	}

}

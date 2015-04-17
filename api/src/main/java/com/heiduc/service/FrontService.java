

package com.heiduc.service;

import org.jabsorb.JSONRPCBridge;

import com.heiduc.service.front.ChannelApiService;
import com.heiduc.service.front.CommentService;
import com.heiduc.service.front.FormService;
import com.heiduc.service.front.LoginService;
import com.heiduc.service.front.SearchService;

public interface FrontService {
	
	void register(JSONRPCBridge bridge);
	void unregister(JSONRPCBridge bridge);

	FormService getFormService();
	void setFormService(FormService bean);

	LoginService getLoginService();
	void setLoginService(LoginService bean);

	CommentService getCommentService();
	void setCommentService(CommentService bean);
	
	SearchService getSearchService();
	void setSearchService(SearchService bean);
	
	ChannelApiService getChannelApiService();
	void setChannelApiService(ChannelApiService bean);

}

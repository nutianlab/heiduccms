

package com.heiduc.service.front.impl;


import org.heiduc.api.channel.ChannelServiceFactory;

import com.heiduc.service.front.ChannelApiService;
import com.heiduc.service.impl.AbstractServiceImpl;

/**
 * @author Alexander Oleynik
 */
public class ChannelApiServiceImpl extends AbstractServiceImpl 
		implements ChannelApiService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String createToken(String clientId) {
		return ChannelServiceFactory.getChannelService().createChannel(clientId);
	}

}

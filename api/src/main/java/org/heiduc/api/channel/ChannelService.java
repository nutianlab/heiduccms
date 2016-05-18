package org.heiduc.api.channel;

import org.atmosphere.cpr.BroadcasterFactory;

public interface ChannelService {

	void sendMessage(ChannelMessage channelMessage);

	String createChannel(String clientId);
	
	void setBroadcasterFactory(BroadcasterFactory broadcasterFactory);

}

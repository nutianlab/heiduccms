package org.heiduc.api.channel;

import org.atmosphere.cpr.Broadcaster;
import org.atmosphere.cpr.BroadcasterFactory;

public class ChannelServiceImpl implements ChannelService {

	private BroadcasterFactory broadcasterFactory;
	
	public void setBroadcasterFactory(BroadcasterFactory broadcasterFactory) {
		this.broadcasterFactory = broadcasterFactory;
	}

	ChannelServiceImpl(){
	}
	
	@Override
	public String createChannel(String clientId) {
		return clientId;
	}

	@Override
	public void sendMessage(ChannelMessage channelMessage) {
		Broadcaster broadcaster  = broadcasterFactory.lookup("/_ah/channel/"+channelMessage.getClientId());
		broadcaster.broadcast(channelMessage.getMessage());
	}

}

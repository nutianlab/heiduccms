package org.heiduc.api.channel;

import org.atmosphere.cpr.Broadcaster;
import org.atmosphere.cpr.BroadcasterFactory;
import org.atmosphere.cpr.DefaultBroadcaster;
import org.atmosphere.cpr.DefaultBroadcasterFactory;


public class ChannelServiceImpl implements ChannelService {

	private BroadcasterFactory broadcasterFactory  = null;
	
	ChannelServiceImpl(){
		broadcasterFactory = new DefaultBroadcasterFactory();
	}
	
	@Override
	public String createChannel(String clientId) {
		return clientId;
	}

	@Override
	public void sendMessage(ChannelMessage channelMessage) {
		
		Broadcaster broadcaster  = broadcasterFactory.lookup(DefaultBroadcaster.class,"/_ah/channel/"+channelMessage.getClientId());
		broadcaster.broadcast(channelMessage.getMessage());
	}

}

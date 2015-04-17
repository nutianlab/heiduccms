package org.heiduc.api.channel;

import org.atmosphere.cpr.Broadcaster;
import org.atmosphere.cpr.BroadcasterFactory;
import org.atmosphere.cpr.DefaultBroadcaster;


public class ChannelServiceImpl implements ChannelService {

	ChannelServiceImpl(){
	}
	
	@Override
	public String createChannel(String clientId) {
		return clientId;
	}

	@Override
	public void sendMessage(ChannelMessage channelMessage) {
		Broadcaster broadcaster  = BroadcasterFactory.getDefault().lookup(DefaultBroadcaster.class,"/_ah/channel/"+channelMessage.getClientId());
		broadcaster.broadcast(channelMessage.getMessage());
	}

}

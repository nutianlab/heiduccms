package org.heiduc.api.channel;

public interface ChannelService {

	void sendMessage(ChannelMessage channelMessage);

	String createChannel(String clientId);

}

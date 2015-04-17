package org.heiduc.api.channel;

public class ChannelServiceFactory {

	
	private static ChannelService instance = null;
	
	private ChannelServiceFactory(){
		
	}
	
	public synchronized static ChannelService getChannelService() {
		if(instance == null){
			instance = new ChannelServiceImpl();
		}
		return instance;
	}

}

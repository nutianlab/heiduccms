package org.heiduc.api.channel;

import java.io.IOException;

import javax.inject.Inject;

import org.atmosphere.client.TrackMessageSizeFilter;
import org.atmosphere.config.service.Disconnect;
import org.atmosphere.config.service.Heartbeat;
import org.atmosphere.config.service.ManagedService;
import org.atmosphere.config.service.Message;
import org.atmosphere.config.service.Ready;
import org.atmosphere.config.service.Singleton;
import org.atmosphere.cpr.AtmosphereResource;
import org.atmosphere.cpr.AtmosphereResourceEvent;
import org.atmosphere.cpr.BroadcasterFactory;
import org.atmosphere.interceptor.AtmosphereResourceLifecycleInterceptor;
import org.atmosphere.interceptor.BroadcastOnPostAtmosphereInterceptor;
import org.atmosphere.interceptor.CacheHeadersInterceptor;
import org.atmosphere.interceptor.HeartbeatInterceptor;
import org.atmosphere.interceptor.JavaScriptProtocol;
import org.atmosphere.interceptor.OnDisconnectInterceptor;
import org.atmosphere.interceptor.PaddingAtmosphereInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Singleton

@ManagedService(
		atmosphereConfig = 
		{
//			"org.atmosphere.websocket.WebSocketProtocol=org.atmosphere.websocket.protocol.StreamingHttpProtocol",
			"org.atmosphere.websocket.DefaultWebSocketProcessor=org.atmosphere.config.managed.ManagedAtmosphereHandler",
//			"org.atmosphere.cpr.AtmosphereInterceptor.disableDefaults=true",
			"org.atmosphere.interceptor.HeartbeatInterceptor.heartbeatFrequencyInSeconds=60",
//			"org.atmosphere.cpr.AtmosphereInterceptor=org.atmosphere.interceptor.JavaScriptProtocol,org.atmosphere.interceptor.OnDisconnectInterceptor",
//			"org.atmosphere.interceptor.HeartbeatInterceptor.paddingChar=X",
			"org.atmosphere.cpr.broadcaster.maxProcessingThreads=10",
			"org.atmosphere.cpr.broadcaster.maxAsyncWriteThreads=10"
		},
		broadcastFilters={TrackMessageSizeFilter.class},
		interceptors = 
			{
				
		 		// OnDisconnect
	            OnDisconnectInterceptor.class,
	            // WebSocket and suspend
//	            WebSocketMessageSuspendInterceptor.class,
	            // ADD Tracking ID Handshake
	            JavaScriptProtocol.class,
	            // ADD JSONP support
	            //JSONPAtmosphereInterceptor.class,
	            // Add SSE support
	            //SSEAtmosphereInterceptor.class,
	            // Heartbeat
	            HeartbeatInterceptor.class,
	            // Android 2.3.x streaming support
	            //AndroidAtmosphereInterceptor.class,
	            // WebKit & IE Padding
	            PaddingAtmosphereInterceptor.class,
	            // Default Interceptor
	            CacheHeadersInterceptor.class,
	            // Add CORS support
	            //CorsInterceptor.class,
	            
//				TrackMessageSizeInterceptor.class,
				AtmosphereResourceLifecycleInterceptor.class,
				
				BroadcastOnPostAtmosphereInterceptor.class
				
//				SuspendTrackerInterceptor.class
		},
		/*listeners={
				
		},*/
//		broadcasterCache=UUIDBroadcasterCache.class,
//		broadcaster = DefaultBroadcaster.class,
		path="/_ah/channel/{clientId}"
		)
public class Channel {

	private final Logger logger = LoggerFactory.getLogger(Channel.class);
	
	@Inject
	private BroadcasterFactory broadcasterFactory;
	
	@Heartbeat
    public void onHeartbeat(final AtmosphereResourceEvent event) {
        logger.info("Heartbeat send by {}", event.getResource());
    }
	
	@Ready
    public void onReady(final AtmosphereResource r) {
		ChannelServiceFactory.getChannelService().setBroadcasterFactory(broadcasterFactory);
        logger.info("Browser {} connected.", r.uuid());
        
        
/*        Broadcaster broadcaster = BroadcasterFactory.getDefault().lookup(DefaultBroadcaster.class, r.uuid());
//        broadcaster.setID(arg0)
        broadcaster.addAtmosphereResource(r);
        
        
        System.out.println(r.getBroadcaster().getID());
        System.out.println(r.getBroadcaster().getScope());*/
//        BroadcasterFactory.getDefault().get().addAtmosphereResource(r);
//        Broadcaster 
//        BroadcasterFactory factory = r.getAtmosphereConfig().getBroadcasterFactory();
    }
	
	@Disconnect
    public void onDisconnect(AtmosphereResourceEvent event) {
        if (event.isCancelled()) {
            logger.info("Browser {} unexpectedly disconnected", event.getResource().uuid());
        } else if (event.isClosedByClient()) {
            logger.info("Browser {} closed the connection", event.getResource().uuid());
        }
    }
	
    /*public String onMessage(String message) throws IOException {
        logger.info("just send {}", message);
        return message;
    }*/
	
	@Message
	public String onMessage(String message) throws IOException {
        logger.info("just send {}", message);
        return message;
    }
	
}

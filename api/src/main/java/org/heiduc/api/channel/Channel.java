package org.heiduc.api.channel;

import java.io.IOException;

import org.atmosphere.cache.DefaultBroadcasterCache;
import org.atmosphere.config.service.Disconnect;
import org.atmosphere.config.service.ManagedService;
import org.atmosphere.config.service.Message;
import org.atmosphere.config.service.Ready;
import org.atmosphere.config.service.Singleton;
import org.atmosphere.cpr.AtmosphereResource;
import org.atmosphere.cpr.AtmosphereResourceEvent;
import org.atmosphere.interceptor.AtmosphereResourceLifecycleInterceptor;
import org.atmosphere.interceptor.BroadcastOnPostAtmosphereInterceptor;
import org.atmosphere.interceptor.CacheHeadersInterceptor;
import org.atmosphere.interceptor.HeartbeatInterceptor;
import org.atmosphere.interceptor.SuspendTrackerInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
@ManagedService(
		
//		broadcastFilters={TrackMessageSizeFilter.class},
		broadcasterCache=DefaultBroadcasterCache.class,
		//broadcaster = DefaultBroadcaster.class,
		path="/_ah/channel/{clientId}",
		interceptors = 
		{
			/**
	 		// OnDisconnect
            OnDisconnectInterceptor.class,
            // WebSocket and suspend
            WebSocketMessageSuspendInterceptor.class,
            // ADD Tracking ID Handshake
            JavaScriptProtocol.class,
            // ADD JSONP support
            JSONPAtmosphereInterceptor.class,
            // Add SSE support
            SSEAtmosphereInterceptor.class,
            // Heartbeat
            HeartbeatInterceptor.class,
            // Android 2.3.x streaming support
            AndroidAtmosphereInterceptor.class,
            // WebKit & IE Padding
            PaddingAtmosphereInterceptor.class,
            // Default Interceptor
            CacheHeadersInterceptor.class,
            // Add CORS support
            CorsInterceptor.class
			 * */
			CacheHeadersInterceptor.class,
//			OnDisconnectInterceptor.class,
			//TrackMessageSizeInterceptor.class,
			AtmosphereResourceLifecycleInterceptor.class,
			BroadcastOnPostAtmosphereInterceptor.class,
			HeartbeatInterceptor.class,
			SuspendTrackerInterceptor.class
		}
		)
public class Channel {

	private final Logger logger = LoggerFactory.getLogger(Channel.class);
	
	@Ready
    public void onReady(final AtmosphereResource r) {
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

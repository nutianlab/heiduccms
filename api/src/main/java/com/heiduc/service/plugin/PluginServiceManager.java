

package com.heiduc.service.plugin;

import org.jabsorb.JSONRPCBridge;

public interface PluginServiceManager extends ServicePlugin {

	void register(JSONRPCBridge bridge);
	void unregister(JSONRPCBridge bridge);

}

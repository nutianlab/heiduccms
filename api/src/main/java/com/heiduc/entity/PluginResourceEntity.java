

package com.heiduc.entity;

import static com.heiduc.utils.EntityUtil.getBlobProperty;
import static com.heiduc.utils.EntityUtil.getStringProperty;
import static com.heiduc.utils.EntityUtil.setProperty;

import org.heiduc.api.datastore.Entity;


public class PluginResourceEntity extends BaseEntityImpl {
	
	private static final long serialVersionUID = 2L;

	private String pluginName;
	private byte[] data;
	private String url;
	
    public PluginResourceEntity() {
    }

    @Override
    public void load(Entity entity) {
    	super.load(entity);
    	data = getBlobProperty(entity, "data");
    	url = getStringProperty(entity, "url");
    	pluginName = getStringProperty(entity, "pluginName");
    }
    
    @Override
    public void save(Entity entity) {
    	super.save(entity);
    	setProperty(entity, "data", data);
    	setProperty(entity, "url", url, true);
    	setProperty(entity, "pluginName", pluginName, true);
    }

    public PluginResourceEntity(String plugin, String anUrl, byte[] content) {
		this();
		pluginName = plugin;
		this.url = anUrl;
		this.data = content;
	}

	public byte[] getContent() {
		return data;
	}
	
	public void setContent(byte[] content) {
		this.data = content;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String fileId) {
		this.url = fileId;
	}

	public String getPluginName() {
		return pluginName;
	}

	public void setPluginName(String pluginName) {
		this.pluginName = pluginName;
	}
	
}

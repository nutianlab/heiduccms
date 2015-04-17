

package com.heiduc.business.vo;

public class PluginPropertyVO {

	private String name;
	private String title;
	private String type;
	private String defaultValue;
	
	public PluginPropertyVO() {
	}

	public PluginPropertyVO(String name, String title, String type, 
			String defaultValue) {
		super();
		this.name = name;
		this.title = title;
		this.type = type;
		this.defaultValue = defaultValue;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
}



package com.heiduc.entity.helper;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Element;

import com.heiduc.enums.PluginConfigParameterType;
import com.heiduc.utils.ParamUtil;

/**
 * 
 * @author Alexander Oleynik
 *
 */
public class PluginParameter {

	private String name;
	private String title;
	private PluginConfigParameterType type;
	private String value;
	private String defaultValue;

	public PluginParameter(Element element) {
		name = element.attributeValue("name");
		title = element.attributeValue("title");
		type = PluginConfigParameterType.valueOf(element.attributeValue("type")
				.toUpperCase());
		defaultValue = element.attributeValue("title");
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public String getTitle() {
		return title;
	}

	public PluginConfigParameterType getType() {
		return type;
	}

	public String getDefaultValue() {
		return defaultValue;
	}
	
	public Integer getDefaultValueInteger() {
		return ParamUtil.getInteger(defaultValue, 0);
	}

	public Boolean getDefaultValueBoolean() {
		return ParamUtil.getBoolean(defaultValue, false);
	}

	public Date getDefaultValueDate() {
		return ParamUtil.getDate(defaultValue, new Date());
	}

	public Integer getValueInteger() {
		return ParamUtil.getInteger(value, getDefaultValueInteger());
	}

	public Boolean getValueBoolean() {
		return ParamUtil.getBoolean(value, getDefaultValueBoolean());
	}

	public Date getValueDate() {
		return ParamUtil.getDate(value, getDefaultValueDate());
	}

	public String getValueString() {
		return StringUtils.isEmpty(value) ? defaultValue : value;
	}
	
	
}

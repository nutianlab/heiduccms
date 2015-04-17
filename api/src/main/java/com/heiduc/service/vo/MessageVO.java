

package com.heiduc.service.vo;

import java.util.Map;

/**
 * Value object to be returned from services.
 * @author Alexander Oleynik
 */
public class MessageVO {

    private String code;
    private Map<String, String> values;

	public MessageVO(final String code, final Map<String, String> values) {
		this.code = code;
		this.values = values;
	}

	public String getCode() {
		return code;
	}

	public Map<String, String> getValues() {
		return values;
	}

}

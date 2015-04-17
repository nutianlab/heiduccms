

package com.heiduc.service.vo;

/**
 * Value object to be returned from services.
 * @author Alexander Oleynik
 */
public class CodeVO {

    private String code;
    private String value;

	public CodeVO(final String code, final String value) {
		this.code = code;
		this.value = value;
	}

	public String getCode() {
		return code;
	}

	public String getValue() {
		return value;
	}

}

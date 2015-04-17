

package com.heiduc.service.vo;

import java.util.ArrayList;
import java.util.List;

import com.heiduc.entity.FieldEntity;

/**
 * Value object to be returned from services.
 * @author Alexander Oleynik
 */
public class FieldVO {

	private FieldEntity field;
	
	public static List<FieldVO> create(List<FieldEntity> list) {
		List<FieldVO> result = new ArrayList<FieldVO>();
		for (FieldEntity entity : list) {
			result.add(new FieldVO(entity));
		}
		return result;
	}

	public FieldVO(final FieldEntity aField) {
		field = aField;
	}
	
	public String getFieldType() {
		return field.getFieldType().name();
	}

	public boolean isOptional() {
		return field.isMandatory();
	}

	public String getValues() {
		return field.getValues();
	}

	public String getDefaultValue() {
		return field.getDefaultValue();
	}

	public String getTitle() {
		return field.getTitle();
	}

	public Long getId() {
		return field.getId();
	}
	
	public String getName() {
		return field.getName();
	}

	public Long getFormId() {
		return field.getFormId();
	}

	public int getHeight() {
		return field.getHeight();
	}

	public int getWidth() {
		return field.getWidth();
	}
	
	public int getIndex() {
		return field.getIndex();
	}
	
	public String getRegex() {
		return field.getRegex();
	}

	public String getRegexMessage() {
		return field.getRegexMessage();
	}
	
}

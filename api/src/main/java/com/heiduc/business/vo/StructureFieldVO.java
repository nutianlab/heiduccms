

package com.heiduc.business.vo;

/**
 * Value object to be returned from services.
 * @author Alexander Oleynik
 */
public class StructureFieldVO {

    private String title;
    private String name;
    private String type;
	
    public StructureFieldVO(String title, String name, String type) {
		super();
		this.title = title;
		this.name = name;
		this.type = type;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
    
}

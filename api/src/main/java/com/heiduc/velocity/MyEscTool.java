

package com.heiduc.velocity;

import org.apache.velocity.tools.generic.EscapeTool;

/**
 * @author Alexander Oleynik
 */
public class MyEscTool extends EscapeTool {

	public MyEscTool() {
		super();
	}

	public String getR() {
		return "\r";
	}
}

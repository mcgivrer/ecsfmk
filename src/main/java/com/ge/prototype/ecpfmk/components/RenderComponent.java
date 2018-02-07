/**
 * GE DoseWatch
 * ecpfmk
 *
 * @copyright 2018
 */
package com.ge.prototype.ecpfmk.components;

import java.awt.Color;

/**
 * Rendering component with some specific attributes.
 * 
 * @author Frédéric Delorme<frederic.delorme@ge.com>
 *
 */
public class RenderComponent implements Component {

	
	// Rendering Attributes
	public Color color = Color.GRAY;

	/**
	 * 
	 */
	public RenderComponent() {
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ge.prototype.ecpfmk.components.Component#getName()
	 */
	@Override
	public String getName() {
		return "render";
	}

}

/**
 * McGivrer's Blog 
 *
 * Entity Component System framework 
 *
 * @copyright 2018
 */
package fr.mcgivrer.prototype.ecsfmk.components;

import java.awt.Color;

/**
 * Rendering component with some specific attributes.
 * 
 * @author Frédéric Delorme<frederic.delorme@snapgames.fr>
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
	 * @see fr.mcgivrer.prototype.ecsfmk.components.Component#getName()
	 */
	@Override
	public String getName() {
		return "render";
	}

}

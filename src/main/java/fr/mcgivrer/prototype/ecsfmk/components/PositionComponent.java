/**
 * McGivrer's Blog 
 *
 * Entity Component System framework 
 *
 * @copyright 2018
 */
package fr.mcgivrer.prototype.ecsfmk.components;

import java.awt.Rectangle;

import fr.mcgivrer.prototype.ecsfmk.math.Vector2D;

/**
 * This component intends to contain position information.
 * 
 * @author Frédéric Delorme<frederic.delorme@snapgames.fr>
 *
 */
public class PositionComponent implements Component {

	public Vector2D position = new Vector2D(0.0f, 0.0f);
	public Rectangle size = new Rectangle();

	/**
	 * 
	 */
	public PositionComponent() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getName() {

		return "position";
	}

	/**
	 * @param position
	 *            the position to set
	 */
	public PositionComponent setPosition(Vector2D position) {
		this.position = position;
		return this;
	}

	/**
	 * @param size
	 *            the size to set
	 */
	public PositionComponent setSize(Rectangle size) {
		this.size = size;
		return this;
	}
	
}

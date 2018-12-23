/**
 * McGivrer's Blog 
 *
 * Entity Component System framework 
 *
 * @copyright 2018
 */
package fr.mcgivrer.prototype.ecsfmk.math.physic;

import java.util.ArrayList;
import java.util.List;

import fr.mcgivrer.prototype.ecsfmk.math.Vector2D;

/**
 * This class is intend to be the Physic world context to compute everything
 * about physic.
 * 
 * @author Frédéric Delorme<frederic.delorme@snapgames.fr>
 *
 */
public class World {

	public List<Vector2D> forces = new ArrayList<Vector2D>();
	public Vector2D gravity = new Vector2D(0, 98.1f);

	/**
	 * Initialize the default World attributes.
	 */
	public World() {
		clear();
	}

	public World(Vector2D vGravity) {
		gravity = vGravity;
		clear();
	}

	/**
	 * create the World values but gravity.
	 */
	public void clear() {
		forces.clear();
		forces.add(gravity);
	}

}

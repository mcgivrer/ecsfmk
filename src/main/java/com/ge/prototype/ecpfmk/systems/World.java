/**
 * GE DoseWatch
 * ecpfmk
 *
 * @copyright 2018
 */
package com.ge.prototype.ecpfmk.systems;

import java.util.ArrayList;
import java.util.List;

import com.ge.prototype.ecpfmk.math.Vector2D;

/**
 * This class is intend to be the Physic world context to compute everything
 * about physic.
 * 
 * @author Frédéric Delorme<frederic.delorme@ge.com>
 *
 */
public class World {

	// list of forces (like wind, river flow, etc...) for this world.
	public List<Vector2D> forces = new ArrayList<Vector2D>();

	// default Earth gravity can be changed accordingly to your needs.
	public Vector2D gravity = new Vector2D(0, 98.1f);

	/**
	 * Initialize the default World attributes.
	 */
	public World() {
		clear();
	}

	/**
	 * Initialize the World object with the gravity value.
	 * 
	 * @param vGravity
	 */
	public World(Vector2D vGravity) {
		gravity = vGravity;
		clear();
	}

	/**
	 * clear all the World values but gravity.
	 */
	public void clear() {
		forces.clear();
		forces.add(gravity);
	}

}

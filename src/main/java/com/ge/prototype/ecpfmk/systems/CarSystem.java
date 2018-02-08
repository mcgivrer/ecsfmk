/**
 * GE DoseWatch
 * ecpfmk
 *
 * @copyright 2018
 */
package com.ge.prototype.ecpfmk.systems;

import java.awt.Dimension;

import com.ge.prototype.ecpfmk.Application;
import com.ge.prototype.ecpfmk.entities.Car;
import com.ge.prototype.ecpfmk.math.Vector2D;

/**
 * This system is dedicated to Car animation and computation.
 * 
 * @author Frédéric Delorme<frederic.delorme@ge.com>
 *
 */
public class CarSystem implements System {
	// parent application.
	public Application app;
	// Viewport dimension useful to constrain position of the car.
	public Dimension dimension;

	/**
	 * Default constructor for the Car System
	 * 
	 * @param app
	 *            the parent application
	 * @param dim
	 *            the dimension of the window viewport.
	 */
	public CarSystem(Application app, Dimension dim) {
		this.app = app;
		this.dimension = dim;
	}

	/**
	 * Compute physic behavior and position of the car.
	 */
	public void update(float dt) {
		Car c = (Car) app.entities.get("car");
		float t = dt * 0.005f;

		// -- Update Physics (System)
		c.physic.forces.addAll(c.physic.world.forces);
		// logger.debug("acceleration = {}", c.physic.acceleration);
		for (Vector2D v : c.physic.forces) {
			c.physic.acceleration = c.physic.acceleration.add(v);
		}
		c.physic.acceleration = c.physic.acceleration.multiply(c.physic.resistance).multiply(1.0f / c.physic.mass)
				.multiply(50.0f * dt);
		// compute velocity
		c.physic.velocity.x += (c.physic.acceleration.x * t * t);
		c.physic.velocity.y += (c.physic.acceleration.y * t * t);
		// logger.debug("velocity = {}", c.physic.velocity);

		// -- update Position (System)
		c.pos.position.x += 0.5f * (c.physic.velocity.x * t);
		c.pos.position.y += 0.5f * (c.physic.velocity.y * t);
		// logger.debug("position = {}", this.position);

		keepConstrainedTo(c, dimension);
	}

	/**
	 * keep Car c in the field of view limited by dim.
	 * 
	 * @param c
	 * @param dim
	 */
	public void keepConstrainedTo(Car c, Dimension dim) {
		if (c.pos.position.x > dim.getWidth() - c.pos.size.width) {
			c.pos.position.x = (float) dim.getWidth() - c.pos.size.width;
			c.physic.velocity.x *= -1 * c.physic.elasticity;
		}

		if (c.pos.position.x <= 0) {
			c.pos.position.x = 0;
			c.physic.velocity.x *= -1 * c.physic.elasticity;

		}
		if (c.pos.position.y > dim.getHeight() - c.pos.size.height) {
			c.pos.position.y = (float) dim.getHeight() - c.pos.size.height;
			c.physic.velocity.y *= -1 * c.physic.elasticity;
		}
		if (c.pos.position.y <= 0) {
			c.pos.position.y = 0.0f;
			c.physic.velocity.y *= -1 * c.physic.elasticity;
		}
	}

}

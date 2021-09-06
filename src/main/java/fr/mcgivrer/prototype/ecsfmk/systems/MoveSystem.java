/**
 * McGivrer's Blog 
 *
 * Entity Component System framework 
 *
 * @copyright 2018
 */
package fr.mcgivrer.prototype.ecsfmk.systems;

import java.awt.Dimension;
import java.util.Optional;

import fr.mcgivrer.prototype.ecsfmk.Application;
import fr.mcgivrer.prototype.ecsfmk.components.Component;
import fr.mcgivrer.prototype.ecsfmk.components.PhysicComponent;
import fr.mcgivrer.prototype.ecsfmk.components.PositionComponent;
import fr.mcgivrer.prototype.ecsfmk.entities.Entity;
import fr.mcgivrer.prototype.ecsfmk.math.Vector2D;

/**
 * This system is dedicated to Car animation and computation.
 * 
 * @author Frédéric Delorme<frederic.delorme@snapgames.fr>
 *
 */
public class MoveSystem implements System {
	// parent application.
	public Application app;
	// Viewport dimension useful to constrain position of the car.
	public Dimension dimension;

	/**
	 * Default constructor for the Car System
	 * 
	 * @param app the parent application
	 * @param dim the dimension of the window viewport.
	 */
	public MoveSystem(Application app, Dimension dim) {
		this.app = app;
		this.dimension = dim;
	}

	/**
	 * Compute physic behavior and position of the car.
	 */
	public void update(float dt) {
		if (!app.pause) {
			for (Entity c : app.entities.values()) {
				// retrieve components
				Optional<Component> phyC = c.getComponent("physic");
				Optional<Component> posC = c.getComponent("position");

				if (phyC.isPresent() && posC.isPresent()) {
					PhysicComponent physic = (PhysicComponent) phyC.get();
					PositionComponent pos = (PositionComponent) posC.get();

					float t = dt * 0.005f;

					// -- Update Physics (System)
					physic.forces.addAll(physic.world.forces);

					for (Vector2D v : physic.forces) {
						physic.acceleration = physic.acceleration.add(v);
					}
					physic.acceleration = physic.acceleration.multiply(physic.resistance).multiply(1.0f / physic.mass)
							.multiply(50.0f * dt);
					// compute velocity
					physic.velocity.x += (physic.acceleration.x * t * t);
					physic.velocity.y += (physic.acceleration.y * t * t);

					// -- update Position (System)
					pos.position.x += 0.5f * (physic.velocity.x * t);
					pos.position.y += 0.5f * (physic.velocity.y * t);

					keepConstrainedTo(physic, pos, dimension);
				}
			}
		}
	}

	/**
	 * keep Car c in the field of view limited by dim.
	 * 
	 * @param c
	 * @param dim
	 */
	public void keepConstrainedTo(PhysicComponent physic, PositionComponent pos, Dimension dim) {
		if (pos.position.x > dim.getWidth() - pos.size.width) {
			pos.position.x = (float) dim.getWidth() - pos.size.width;
			physic.velocity.x *= -1 * physic.elasticity;
		}

		if (pos.position.x <= 0) {
			pos.position.x = 0;
			physic.velocity.x *= -1 * physic.elasticity;

		}
		if (pos.position.y > dim.getHeight() - pos.size.height) {
			pos.position.y = (float) dim.getHeight() - pos.size.height;
			physic.velocity.y *= -1 * physic.elasticity;
		}
		if (pos.position.y <= 0) {
			pos.position.y = 0.0f;
			physic.velocity.y *= -1 * physic.elasticity;
		}
	}

	@Override
	public void postOperation() {
		if (!app.pause) {
			for (Entity e : app.entities.values()) {
				Optional<PhysicComponent> physic = (Optional<PhysicComponent>) e.getComponent("physic");

				if (physic.isPresent()) {
					physic.get().forces.clear();
				}

			}
		}
	}

}

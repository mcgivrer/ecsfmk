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
import fr.mcgivrer.prototype.ecsfmk.math.physic.World;

/**
 * This system is dedicated to Car animation and computation.
 * 
 * @author Frédéric Delorme<frederic.delorme@snapgames.fr>
 *
 */
public class PhysicSystem implements System {
	// parent application.
	public Application app;
	// Viewport dimension useful to constrain position of the car.
	public Dimension dimension;

	private World world;

	/**
	 * Default constructor for the Car System
	 * 
	 * @param app the parent application
	 * @param dim the dimension of the window viewport.
	 */
	public PhysicSystem(Application app, Dimension dim) {
		this.app = app;
		this.dimension = dim;
	}

	/**
	 * Compute physic behavior and position of the car.
	 */
	public void update(float dt) {
		if (!app.pause) {
			app.entities.values().stream()
					.filter(v -> v.getComponent("physic").isPresent() && v.getComponent("position").isPresent())
					.forEach(e -> {

						// retrieve components
						Optional<Component> phyC = e.getComponent("physic");
						Optional<Component> posC = e.getComponent("position");

						if (phyC.isPresent() && posC.isPresent()) {
							PhysicComponent physic = (PhysicComponent) phyC.get();
							PositionComponent pos = (PositionComponent) posC.get();

							float t = dt * 0.005f;

							// -- Update Physics (System)
							// apply world global physic forces from the engine.
							if (world != null) {
								physic.forces.addAll(world.forces);
							}
							// apply specific World's forces from entity's physic component
							if (physic.world != null) {
								physic.forces.addAll(physic.world.forces);
							}

							for (Vector2D v : physic.forces) {
								physic.acceleration = physic.acceleration.add(v);
							}
							physic.acceleration = physic.acceleration.multiply(physic.resistance)
									.multiply(1.0f / physic.mass).multiply(50.0f * dt);
							// compute velocity
							physic.velocity.x += (physic.acceleration.x * t * t);
							physic.velocity.y += (physic.acceleration.y * t * t);

							// -- update Position (System)
							pos.position.x += 0.5f * (physic.velocity.x * t);
							pos.position.y += 0.5f * (physic.velocity.y * t);

							keepConstrainedTo(physic, pos, dimension);

						}
					});
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

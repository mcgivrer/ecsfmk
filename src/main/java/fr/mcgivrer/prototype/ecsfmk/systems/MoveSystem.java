/**
 * McGivrer's Blog 
 *
 * Entity Component System framework 
 *
 * @copyright 2018
 */
package fr.mcgivrer.prototype.ecsfmk.systems;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import fr.mcgivrer.prototype.ecsfmk.Application;
import fr.mcgivrer.prototype.ecsfmk.components.PhysicComponent;
import fr.mcgivrer.prototype.ecsfmk.components.PositionComponent;
import fr.mcgivrer.prototype.ecsfmk.entities.Entity;
import fr.mcgivrer.prototype.ecsfmk.math.Vector2D;
import fr.mcgivrer.prototype.ecsfmk.math.physic.World;

/**
 * This MoveSystem is dedicated to compute physic and position for attached
 * entities. Each attached entity to this system need to have a
 * {@link PositionComponent} and a {@link PhysicComponent}.
 * 
 * @author Frédéric Delorme<frederic.delorme@snapgames.fr>
 *
 */
public class MoveSystem implements System {

	/**
	 * List of attached entities to the MoveSystem.
	 */
	List<Entity> entities = new ArrayList<>();

	// parent application.
	public Application app;
	// Viewport dimension useful to constrain position of the car.
	public Dimension dimension;
	// the world object where all world constraints are declared !
	public World world;

	/**
	 * Default constructor for the Car System
	 * 
	 * @param app
	 *            the parent application
	 * @param dim
	 *            the dimension of the window viewport.
	 */
	public MoveSystem(Application app, Dimension dim) {
		this.app = app;
		this.dimension = dim;
	}

	/**
	 * Compute physic behavior and position of the car.
	 */
	public void update(float dt) {
		if (entities != null && !entities.isEmpty()) {
			for (Entity entity : entities) {
				PositionComponent pos = (PositionComponent) app.entities.get(entity.getName()).getComponent("position");
				PhysicComponent phy = (PhysicComponent) app.entities.get(entity.getName()).getComponent("physic");
				if (pos != null && phy != null) {
					float t = dt * 0.005f;

					// -- Update Physics (System)
					phy.forces.addAll(world.forces);
					for (Vector2D v : phy.forces) {
						phy.acceleration = phy.acceleration.add(v);
					}
					phy.acceleration = phy.acceleration.multiply(1.0f / phy.mass).multiply(50.0f * dt);
					// compute velocity
					phy.velocity.x = (phy.velocity.x + (phy.acceleration.x * t * t)) * phy.friction;
					phy.velocity.y = (phy.velocity.y + (phy.acceleration.y * t * t)) * phy.friction;

					// -- update Position (System)
					pos.position.x += 0.5f * (phy.velocity.x * t);
					pos.position.y += 0.5f * (phy.velocity.y * t);

					keepConstrainedTo(pos, phy, dimension);
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
	public void keepConstrainedTo(PositionComponent pos, PhysicComponent phy, Dimension dim) {
		if (pos.position.x > dim.getWidth() - pos.size.width) {
			pos.position.x = (float) dim.getWidth() - pos.size.width;
			phy.velocity.x *= -1 * phy.elasticity;
		}

		if (pos.position.x <= 0) {
			pos.position.x = 0;
			phy.velocity.x *= -1 * phy.elasticity;

		}
		if (pos.position.y > dim.getHeight() - pos.size.height) {
			pos.position.y = (float) dim.getHeight() - pos.size.height;
			phy.velocity.y *= -1 * phy.elasticity;
		}
		if (pos.position.y <= 0) {
			pos.position.y = 0.0f;
			phy.velocity.y *= -1 * phy.elasticity;
		}
	}

	/**
	 * Set the world constraints.
	 * 
	 * @param world
	 */
	public void setWorld(World world) {
		this.world = world;

	}

	@Override
	public void add(Entity e) {
		entities.add(e);
	}

	public void postProcess(float dt) {
		for (Entity entity : entities) {
			((PhysicComponent) entity.getComponent("physic")).clearForces();
		}
	}

}

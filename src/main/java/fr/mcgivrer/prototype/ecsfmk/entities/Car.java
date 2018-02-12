/**
 * McGivrer's Blog 
 *
 * Entity Component System framework 
 *
 * @copyright 2018
 */
package fr.mcgivrer.prototype.ecsfmk.entities;

import fr.mcgivrer.prototype.ecsfmk.components.PhysicComponent;
import fr.mcgivrer.prototype.ecsfmk.components.PositionComponent;
import fr.mcgivrer.prototype.ecsfmk.components.RenderComponent;
import fr.mcgivrer.prototype.ecsfmk.math.physic.World;

/**
 * A Car with the Entity concept.
 * 
 * @author Frédéric Delorme<frederic.delorme@ge.com>
 *
 */
public class Car extends Entity {
	// Position component to manage car position :)
	private  PositionComponent pos = new PositionComponent();
	// Physic component to compute physic about that car.
	private PhysicComponent physic = new PhysicComponent();
	// an dfinally a rendering component to render tings.
	private  RenderComponent render = new RenderComponent();

	/**
	 * A default Constructor.
	 */
	public Car(World world) {
		super();
		initialize(world);
	}

	/**
	 * A parameterized constructor with the name of that car.
	 * 
	 * @param name
	 */
	public Car(World world, String name) {
		super(world, name);
		initialize(world);
	}

	/**
	 * @param world
	 */
	private void initialize(World world) {
		physic.setWorld(world);
		addComponent(pos);
		addComponent(physic);
		addComponent(render);
	}

}

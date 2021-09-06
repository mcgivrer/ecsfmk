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

/**
 * A Car with the Entity concept.
 * 
 * @author Frédéric Delorme<frederic.delorme@snapgames.fr>
 *
 */
public class Car extends Entity<Car> {

	/**
	 * A default Constructor.
	 */
	public Car() {
		this("mycar");
	}

	/**
	 * A parameterized constructor with the name of that car.
	 * 
	 * @param name
	 */
	public Car(String name) {
		super(name);
		// Position component to manage car position :)
		add(new PositionComponent());
		// Physic component to compute physic about that car.
		add(new PhysicComponent());
		// an dfinally a rendering component to render tings.
		add(new RenderComponent());
	}

}

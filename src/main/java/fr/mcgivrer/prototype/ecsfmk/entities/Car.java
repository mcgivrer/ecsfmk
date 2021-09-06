/**
 * McGivrer's Blog 
 *
 * Entity Component System framework 
 *
 * @copyright 2018
 */
package fr.mcgivrer.prototype.ecsfmk.entities;

import java.awt.Rectangle;

import fr.mcgivrer.prototype.ecsfmk.components.PhysicComponent;
import fr.mcgivrer.prototype.ecsfmk.components.PositionComponent;
import fr.mcgivrer.prototype.ecsfmk.components.RenderComponent;
import fr.mcgivrer.prototype.ecsfmk.math.Vector2D;
import fr.mcgivrer.prototype.ecsfmk.math.physic.World;

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

	public Car setPosition(Vector2D pos) {
		((PositionComponent) (getComponent("position").get())).setPosition(pos);
		return this;
	}

	public Car setSize(Rectangle size) {
		((PositionComponent) (getComponent("position").get())).setSize(size);
		return this;
	}

	public Car setMass(float mass) {
		((PhysicComponent) (getComponent("physic").get())).setMass(mass);
		return this;
	}

	public Car setResistance(float resistance) {
		((PhysicComponent) (getComponent("physic").get())).setResistance(resistance);
		return this;
	}

	public Car setVelocity(Vector2D vel) {
		((PhysicComponent) (getComponent("physic").get())).setVelocity(vel);
		return this;
	}

	public Car setWorld(World world) {
		((PhysicComponent) (getComponent("physic").get())).setWorld(world);
		return this;
	}

}

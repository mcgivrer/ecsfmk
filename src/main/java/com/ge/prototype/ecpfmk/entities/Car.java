/**
 * GE DoseWatch
 * ecpfmk
 *
 * @copyright 2018
 */
package com.ge.prototype.ecpfmk.entities;

import com.ge.prototype.ecpfmk.components.PhysicComponent;
import com.ge.prototype.ecpfmk.components.PositionComponent;
import com.ge.prototype.ecpfmk.components.RenderComponent;

/**
 * A Car with the Entity concept.
 * 
 * @author Frédéric Delorme<frederic.delorme@ge.com>
 *
 */
public class Car extends Entity {
	// Position component to manage car position :)
	public PositionComponent pos = new PositionComponent();
	// Physic component to compute physic about that car.
	public PhysicComponent physic = new PhysicComponent();
	// an dfinally a rendering component to render tings.
	public RenderComponent render = new RenderComponent();

	/**
	 * A default Constructor.
	 */
	public Car() {
		super();
	}

	/**
	 * A parameterized constructor with the name of that car.
	 * 
	 * @param name
	 */
	public Car(String name) {
		super(name);
	}

}

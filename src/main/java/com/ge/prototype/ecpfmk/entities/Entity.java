/**
 * GE DoseWatch
 * 
 * ecpfmk
 *
 * @copyright 2018
 */
package com.ge.prototype.ecpfmk.entities;

/**
 * A Car class to manage and display a Car :)
 * 
 * @author Frédéric Delorme<frederic.delorme@ge.com>
 *
 */
public class Entity {
	// Entity Name
	public String name;

	/**
	 * A new constructor to build car with all its attributes values.
	 */
	public Entity(String name) {
		this();
		this.name = name;
	}

	/**
	 * Default constructor to create a new Car.
	 */
	public Entity() {
		super();
	}

}

/**
 * GE DoseWatch
 * 
 * ecpfmk
 *
 * @copyright 2018
 */
package com.ge.prototype.ecpfmk.entities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.ge.prototype.ecpfmk.components.Component;
import com.ge.prototype.ecpfmk.math.Vector2D;
import com.ge.prototype.ecpfmk.systems.World;

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

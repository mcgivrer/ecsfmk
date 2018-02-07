/**
 * GE DoseWatch
 * ecpfmk
 *
 * @copyright 2018
 */
package com.ge.prototype.ecpfmk.components;

import java.util.ArrayList;
import java.util.List;

import com.ge.prototype.ecpfmk.math.Vector2D;
import com.ge.prototype.ecpfmk.systems.World;

/**
 * Physic Component to compute all physic stuff on entities.
 * 
 * @author Frédéric Delorme<frederic.delorme@ge.com>
 *
 */
public class PhysicComponent implements Component {

	public static final float CAR_ACCEL_X = 100.0f;
	public static final float CAR_ACCEL_Y = 300.0f;
	public static final float CAR_MAX_SPEED = 1000.0f;
	// Physic Attributes
	public World world;
	public Vector2D acceleration = new Vector2D(0.0f, 0.0f);
	public Vector2D velocity = new Vector2D(0.0f, 0.0f);
	public List<Vector2D> forces = new ArrayList<Vector2D>();
	public float mass = 1300.0f;
	public float resistance = 0.89f;
	public float elasticity = 0.22f;
	public float stopTreshold = 0.20f;

	/**
	 * Build this fantastic component.
	 */
	public PhysicComponent() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ge.prototype.ecpfmk.components.Component#getName()
	 */
	@Override
	public String getName() {
		return "physic";
	}

	/**
	 * @param velocity
	 *            the velocity to set
	 */
	public PhysicComponent setVelocity(Vector2D velocity) {
		this.velocity = velocity;
		return this;
	}

	/**
	 * @param resistance
	 *            the resistance to set
	 */
	public PhysicComponent setResistance(float resistance) {
		this.resistance = resistance;
		return this;
	}

	/**
	 * Set the world value.
	 * 
	 * @param world
	 * @return
	 */
	public PhysicComponent setWorld(World world) {
		this.world = world;
		return this;
	}

	/**
	 * @param acceleration
	 *            the acceleration to set
	 */
	public PhysicComponent setAcceleration(Vector2D acceleration) {
		this.acceleration = acceleration;
		return this;
	}

	/**
	 * @param mass
	 *            the mass to set
	 */
	public PhysicComponent setMass(float mass) {
		this.mass = mass;
		return this;
	}

}

/**
 * McGivrer's Blog 
 *
 * Entity Component System framework 
 *
 * @copyright 2018
 */
package fr.mcgivrer.prototype.ecsfmk.components;

import java.util.ArrayList;
import java.util.List;

import fr.mcgivrer.prototype.ecsfmk.math.Vector2D;
import fr.mcgivrer.prototype.ecsfmk.math.physic.World;

/**
 * Physic Component to compute all physic stuff on entities.
 * 
 * @author Frédéric Delorme<frederic.delorme@snapgames.fr>
 *
 */
public class PhysicComponent implements Component {

	// World Physic reference
	public World world;
	// Acceleration
	public Vector2D acceleration = new Vector2D(0.0f, 0.0f);
	// Velocity
	public Vector2D velocity = new Vector2D(0.0f, 0.0f);

	// Physic Attributes
	public List<Vector2D> forces = new ArrayList<Vector2D>();
	public Vector2D defaultAccel = new Vector2D(100.0f, 300.0f);
	public Vector2D defaultMaxSpeedAccel = new Vector2D(1000.0f, 1000.0f);
	public float mass = 1300.0f;
	public float resistance = 0.89f;
	public float elasticity = 0.22f;
	public float stopTreshold = 0.20f;

	/**
	 * Build this fantastic component.
	 */
	public PhysicComponent() {
	}

	/**
	 * Initialize the component with some default values:
	 * 
	 * * `maxSpeed` is the maximum speed threshold to stabilize the velocity
	 * * `defaultAcceleration` is the acceleration to use when thrust up the object.
	 * 
	 * @param maxSpeed
	 * @param defaultAcceleration
	 */
	public PhysicComponent(Vector2D maxSpeed, Vector2D defaultAcceleration) {
		this.defaultMaxSpeedAccel = maxSpeed;
		this.defaultAccel = defaultAcceleration;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.mcgivrer.prototype.ecsfmk.components.Component#getName()
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

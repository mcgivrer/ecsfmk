/**
 * GE DoseWatch
 * 
 * ecpfmk
 *
 * @copyright 2018
 */
package com.ge.prototype.ecpfmk;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ge.prototype.ecpfmk.math.Vector2D;

/**
 * A Car class to manage and display a Car :)
 * 
 * @author Frédéric Delorme<frederic.delorme@ge.com>
 *
 */
public class Car {

	private static final Logger logger = LoggerFactory.getLogger(Car.class);

	public World world;

	public List<Vector2D> forces = new ArrayList<Vector2D>();

	public Vector2D acceleration = new Vector2D(0.0f, 0.0f);
	public Vector2D velocity = new Vector2D(0.0f, 0.0f);
	public Vector2D position = new Vector2D(0.0f, 0.0f);
	public Rectangle size = new Rectangle();
	public float mass = 1300.0f;
	public float resistance = 0.89f;
	public float elasticity = 0.22f;

	public static final float CAR_ACCEL_X = 100.0f;
	public static final float CAR_ACCEL_Y = 300.0f;
	public static final float CAR_MAX_SPEED = 1000.0f;

	/**
	 * Default constructor to create a new Car.
	 */
	public Car(World world) {
		this.world = world;
	}

	/**
	 * A new constructor to build car with all its attributes values.
	 * 
	 * @param velocity
	 * @param position
	 * @param size
	 */
	public Car() {
		super();
	}

	/**
	 * compute position of the car regarding initial its position and its velocity.
	 * 
	 * @param dt
	 *            time elapsed since previous call.
	 */
	public void update(float dt) {
		float t = dt * 0.005f;

		forces.addAll(world.forces);
		logger.info("acceleration={}", this.acceleration);
		for (Vector2D v : forces) {
			this.acceleration = this.acceleration.add(v);
		}
		this.acceleration = this.acceleration.multiply(resistance).multiply(1.0f / mass).multiply(50.0f * dt);
		// compute velocity
		this.velocity.x += (this.acceleration.x * t * t);
		this.velocity.y += (this.acceleration.y * t * t);
		logger.info("velocity={}", this.velocity);

		// compute position
		this.position.x += 0.5f * (this.velocity.x * t);
		this.position.y += 0.5f * (this.velocity.y * t);
		logger.info("position={}", this.position);
		forces.clear();
	}

	/**
	 * Render car to the screen/window with the Graphics interface g.
	 * 
	 * @param g
	 *            the Graphics interface to use to draw the car.
	 */
	public void render(Graphics2D g) {
		g.setColor(Color.RED);
		g.fillRect((int) position.x, (int) position.y, size.width, size.height);
	}

	/**
	 * @param velocity
	 *            the velocity to set
	 */
	public Car setVelocity(Vector2D velocity) {
		this.velocity = velocity;
		return this;
	}

	/**
	 * @param position
	 *            the position to set
	 */
	public Car setPosition(Vector2D position) {
		this.position = position;
		return this;
	}

	/**
	 * @param size
	 *            the size to set
	 */
	public Car setSize(Rectangle size) {
		this.size = size;
		return this;
	}

	/**
	 * Set the world value.
	 * 
	 * @param world
	 * @return
	 */
	public Car setWorld(World world) {
		this.world = world;
		return this;
	}

	/**
	 * @return the acceleration
	 */
	public Vector2D getAcceleration() {
		return acceleration;
	}

	/**
	 * @param acceleration
	 *            the acceleration to set
	 */
	public Car setAcceleration(Vector2D acceleration) {
		this.acceleration = acceleration;
		return this;
	}

	/**
	 * @param mass
	 *            the mass to set
	 */
	public Car setMass(float mass) {
		this.mass = mass;
		return this;
	}

	/**
	 * @param resistance
	 *            the resistance to set
	 */
	public Car setResistance(float resistance) {
		this.resistance = resistance;
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Car [velocity=").append(velocity).append(", position=").append(position).append(", size=")
				.append(size).append("]");
		return builder.toString();
	}

}

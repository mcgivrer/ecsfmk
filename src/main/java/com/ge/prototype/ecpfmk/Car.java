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

import com.ge.prototype.ecpfmk.math.Vector2D;

/**
 * A Car class to manage and display a Car :)
 * 
 * @author Frédéric Delorme<frederic.delorme@ge.com>
 *
 */
public class Car {

	private Vector2D velocity = Vector2D.ZERO;
	private Vector2D position = Vector2D.ZERO;
	private Rectangle size = new Rectangle();

	/**
	 * Default constructor to create a new Car.
	 */
	public Car() {
		this.position = Vector2D.ZERO;
		this.velocity = Vector2D.ZERO;
		this.size = new Rectangle();
	}

	/**
	 * A new constructor to build car with all its attributes values.
	 * 
	 * @param velocity
	 * @param position
	 * @param size
	 */
	public Car(Vector2D velocity, Vector2D position, Rectangle size) {
		super();
		this.velocity = velocity;
		this.position = position;
		this.size = size;
	}

	/**
	 * compute position of the car regarding initial its position and its velocity.
	 * 
	 * @param dt
	 *            time elapsed since previous call.
	 */
	public void update(float dt) {
		position.x = position.x + velocity.x * dt;
		position.y = position.y + velocity.y * dt;
	}

	/**
	 * Render car to the screen/window with the Graphics interface g.
	 * 
	 * @param g
	 *            the Graphics interface to use to draw the car.
	 */
	public void render(Graphics2D g) {
		g.setColor(Color.RED);
		g.drawRect((int) position.x, (int) position.y, size.width, size.height);
	}

	/**
	 * @return the velocity
	 */
	public Vector2D getVelocity() {
		return velocity;
	}

	/**
	 * @param velocity
	 *            the velocity to set
	 */
	public void setVelocity(Vector2D velocity) {
		this.velocity = velocity;
	}

	/**
	 * @return the position
	 */
	public Vector2D getPosition() {
		return position;
	}

	/**
	 * @param position
	 *            the position to set
	 */
	public void setPosition(Vector2D position) {
		this.position = position;
	}

	/**
	 * @return the size
	 */
	public Rectangle getSize() {
		return size;
	}

	/**
	 * @param size
	 *            the size to set
	 */
	public void setSize(Rectangle size) {
		this.size = size;
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

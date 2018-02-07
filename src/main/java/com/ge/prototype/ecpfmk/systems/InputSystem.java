/**
 * GE DoseWatch
 * ecpfmk
 *
 * @copyright 2018
 */
package com.ge.prototype.ecpfmk.systems;

import java.awt.event.KeyEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ge.prototype.ecpfmk.Application;
import com.ge.prototype.ecpfmk.entities.Car;
import com.ge.prototype.ecpfmk.entities.Entity;
import com.ge.prototype.ecpfmk.io.InputHandler;
import com.ge.prototype.ecpfmk.math.Vector2D;

/**
 * @author Frédéric Delorme<frederic.delorme@ge.com>
 *
 */
public class InputSystem implements System {

	private static final Logger logger = LoggerFactory.getLogger(InputSystem.class);

	// parent application.
	Application app = null;

	// input handler to detect keyboard input
	InputHandler ih = null;

	/**
	 * Default constructor with parent app and the main InputHandler to be used.
	 * 
	 * @param app
	 *            the parent application.
	 * @param ih
	 *            the InputHandler to be used here to manage action keymapping.
	 */
	public InputSystem(Application app, InputHandler ih) {
		this.ih = ih;
		this.app = app;
	}

	/**
	 * Detect input keys and process Car data accordingly.
	 */
	public void update(float dt) {
		Car c = (Car) app.entities.get("car");

		if (ih.keys[KeyEvent.VK_LEFT] && Math.abs(c.physic.velocity.x) < c.physic.CAR_MAX_SPEED) {
			Vector2D moveLeft = new Vector2D(-c.physic.CAR_ACCEL_X, 0.0f);
			c.physic.forces.add(moveLeft);
			logger.debug("add left move by {}", moveLeft);
		}
		// break !
		if (ih.keys[KeyEvent.VK_RIGHT] && Math.abs(c.physic.velocity.x) < c.physic.CAR_MAX_SPEED) {
			Vector2D moveRight = new Vector2D(c.physic.CAR_ACCEL_X, 0.0f);
			c.physic.forces.add(moveRight);
			logger.debug("add right move by {}", moveRight);
		}
		// Stop !
		if (ih.keys[KeyEvent.VK_SPACE] && Math.abs(c.physic.velocity.x) < c.physic.CAR_MAX_SPEED) {
			if (c.physic.velocity.x != 0.0f) {
				if (c.physic.velocity.x > c.physic.stopTreshold) {
					c.physic.forces.add(new Vector2D(-c.physic.CAR_ACCEL_X * 4, 0.0f));
				} else if (c.physic.velocity.x < -c.physic.stopTreshold) {
					c.physic.forces.add(new Vector2D(c.physic.CAR_ACCEL_X * 4, 0.0f));
				} else {
					c.physic.velocity.x = 0.0f;
					c.physic.acceleration.x = 0.0f;
				}
			}
			if (c.physic.velocity.y != 0.0f) {
				if (c.physic.velocity.y > c.physic.stopTreshold) {
					c.physic.forces.add(new Vector2D(0.0f, -c.physic.CAR_ACCEL_X * 4));
				} else if (c.physic.velocity.y < -c.physic.stopTreshold) {
					c.physic.forces.add(new Vector2D(0.0f, c.physic.CAR_ACCEL_X * 4));
				} else {
					c.physic.velocity.y = 0.0f;
					c.physic.acceleration.y = 0.0f;
				}
			}
			logger.debug("request break");
		}
		// up !
		if (ih.keys[KeyEvent.VK_UP] && Math.abs(c.physic.velocity.y) < c.physic.CAR_MAX_SPEED) {
			Vector2D moveUp = new Vector2D(0.0f, -c.physic.CAR_ACCEL_Y);
			c.physic.forces.add(moveUp);
			logger.debug("add up move by {}", moveUp);
		}
		// nothing to do today.
		if (ih.keys[KeyEvent.VK_DOWN] && Math.abs(c.physic.velocity.y) < c.physic.CAR_MAX_SPEED) {
			Vector2D moveDown = new Vector2D(0.0f, c.physic.CAR_ACCEL_Y);
			c.physic.forces.add(moveDown);
			logger.debug("add up move by {}", moveDown);
			c.physic.forces.add(moveDown);
		}

		// reset all
		if (ih.keys[KeyEvent.VK_DELETE]) {
			c.physic.acceleration.x = 0.0f;
			c.physic.acceleration.y = 0.0f;
			c.physic.velocity.x = 0.0f;
			c.physic.velocity.y = 0.0f;
			c.pos.position.x = app.win.getWidth() / 2;
			c.pos.position.y = app.win.getHeight() / 2;
		}

		// Switch debug display mode.
		if (ih.keys[KeyEvent.VK_D]) {
			app.debug = !app.debug;
		}

		// stop rendering.
		if (ih.keys[KeyEvent.VK_PAUSE] || ih.keys[KeyEvent.VK_P]) {
			app.pause = !app.pause;
		}
		// Escape and quit simulation.
		if (ih.keys[KeyEvent.VK_ESCAPE] || ih.keys[KeyEvent.VK_Q]) {
			app.exit = true;
		}

	}
}

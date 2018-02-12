/**
 * McGivrer's Blog 
 *
 * Entity Component System framework 
 *
 * @copyright 2018
 */
package fr.mcgivrer.prototype.ecsfmk.systems;

import java.awt.event.KeyEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.mcgivrer.prototype.ecsfmk.Application;
import fr.mcgivrer.prototype.ecsfmk.components.PhysicComponent;
import fr.mcgivrer.prototype.ecsfmk.components.PositionComponent;
import fr.mcgivrer.prototype.ecsfmk.entities.Car;
import fr.mcgivrer.prototype.ecsfmk.entities.Entity;
import fr.mcgivrer.prototype.ecsfmk.io.InputHandler;
import fr.mcgivrer.prototype.ecsfmk.math.Vector2D;

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
		PositionComponent pos = (PositionComponent) app.entities.get("car").getComponent("position");
		PhysicComponent phy = (PhysicComponent) app.entities.get("car").getComponent("physic");

		if (ih.keys[KeyEvent.VK_LEFT] && Math.abs(phy.velocity.x) < phy.defaultMaxSpeedAccel.x) {
			Vector2D moveLeft = new Vector2D(-phy.defaultAccel.x, 0.0f);
			phy.forces.add(moveLeft);
			logger.debug("add left move by {}", moveLeft);
		}
		// break !
		if (ih.keys[KeyEvent.VK_RIGHT] && Math.abs(phy.velocity.x) < phy.defaultMaxSpeedAccel.x) {
			Vector2D moveRight = new Vector2D(phy.defaultAccel.x, 0.0f);
			phy.forces.add(moveRight);
			logger.debug("add right move by {}", moveRight);
		}
		// Stop !
		if (ih.keys[KeyEvent.VK_SPACE] && Math.abs(phy.velocity.x) < phy.defaultMaxSpeedAccel.x) {
			if (phy.velocity.x != 0.0f) {
				if (phy.velocity.x > phy.stopTreshold) {
					phy.forces.add(new Vector2D(-phy.defaultAccel.x * 4, 0.0f));
				} else if (phy.velocity.x < -phy.stopTreshold) {
					phy.forces.add(new Vector2D(phy.defaultAccel.x * 4, 0.0f));
				} else {
					phy.velocity.x = 0.0f;
					phy.acceleration.x = 0.0f;
				}
			}
			if (phy.velocity.y != 0.0f) {
				if (phy.velocity.y > phy.stopTreshold) {
					phy.forces.add(new Vector2D(0.0f, -phy.defaultAccel.y * 4));
				} else if (phy.velocity.y < -phy.stopTreshold) {
					phy.forces.add(new Vector2D(0.0f, phy.defaultAccel.y * 4));
				} else {
					phy.velocity.y = 0.0f;
					phy.acceleration.y = 0.0f;
				}
			}
			logger.debug("request break");
		}
		// up !
		if (ih.keys[KeyEvent.VK_UP] && Math.abs(phy.velocity.y) < phy.defaultMaxSpeedAccel.y) {
			Vector2D moveUp = new Vector2D(0.0f, -phy.defaultAccel.y);
			phy.forces.add(moveUp);
			logger.debug("add up move by {}", moveUp);
		}
		// nothing to do today.
		if (ih.keys[KeyEvent.VK_DOWN] && Math.abs(phy.velocity.y) < phy.defaultMaxSpeedAccel.x) {
			Vector2D moveDown = new Vector2D(0.0f, phy.defaultAccel.y);
			phy.forces.add(moveDown);
			logger.debug("add up move by {}", moveDown);
			phy.forces.add(moveDown);
		}

		// request for a screenshot
		if (ih.keys[KeyEvent.VK_S]) {
			app.requestScreenshot = true;
		}

		// reset all
		if (ih.keys[KeyEvent.VK_DELETE]) {
			phy.acceleration.x = 0.0f;
			phy.acceleration.y = 0.0f;
			phy.velocity.x = 0.0f;
			phy.velocity.y = 0.0f;
			pos.position.x = app.win.getWidth() / 2;
			pos.position.y = app.win.getHeight() / 2;
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

	@Override
	public void add(Entity e) {
		// TODO Auto-generated method stub

	}
}

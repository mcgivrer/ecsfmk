/**
 * McGivrer's Blog 
 *
 * Entity Component System framework 
 *
 * @copyright 2018
 */
package fr.mcgivrer.prototype.ecsfmk.systems;

import java.awt.event.KeyEvent;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.mcgivrer.prototype.ecsfmk.Application;
import fr.mcgivrer.prototype.ecsfmk.components.Component;
import fr.mcgivrer.prototype.ecsfmk.components.PhysicComponent;
import fr.mcgivrer.prototype.ecsfmk.components.PositionComponent;
import fr.mcgivrer.prototype.ecsfmk.io.InputHandler;
import fr.mcgivrer.prototype.ecsfmk.math.Vector2D;

/**
 * @author Frédéric Delorme<frederic.delorme@snapgames.fr>
 *
 */
public class PlayerInputSystem implements System {

	private static final Logger logger = LoggerFactory.getLogger(PlayerInputSystem.class);

	// parent application.
	Application app = null;

	// input handler to detect keyboard input
	InputHandler ih = null;

	/**
	 * Default constructor with parent app and the main InputHandler to be used.
	 * 
	 * @param app the parent application.
	 * @param ih  the InputHandler to be used here to manage action keymapping.
	 */
	public PlayerInputSystem(Application app, InputHandler ih) {
		this.ih = ih;
		this.app = app;
	}

	/**
	 * Detect input keys and process Car data accordingly.
	 */
	public void update(float dt) {
		app.entities.values().stream()
				.filter(v -> v.getComponent("physic").isPresent() && v.getComponent("position").isPresent())
				.forEach(c -> {
					Optional<Component> phyC = c.getComponent("physic");
					Optional<Component> posC = c.getComponent("position");

					if (phyC.isPresent() && posC.isPresent()) {
						PositionComponent pos = (PositionComponent) posC.get();
						PhysicComponent physic = (PhysicComponent) phyC.get();

						if (ih.keys[KeyEvent.VK_LEFT] && Math.abs(physic.velocity.x) < physic.defaultMaxSpeedAccel.x) {
							Vector2D moveLeft = new Vector2D(-physic.defaultAccel.x, 0.0f);
							physic.forces.add(moveLeft);
							logger.debug("add left move by {}", moveLeft);
						}
						// break !
						if (ih.keys[KeyEvent.VK_RIGHT] && Math.abs(physic.velocity.x) < physic.defaultMaxSpeedAccel.x) {
							Vector2D moveRight = new Vector2D(physic.defaultAccel.x, 0.0f);
							physic.forces.add(moveRight);
							logger.debug("add right move by {}", moveRight);
						}
						// Stop !
						if (ih.keys[KeyEvent.VK_SPACE] && Math.abs(physic.velocity.x) < physic.defaultMaxSpeedAccel.x) {
							if (physic.velocity.x != 0.0f) {
								if (physic.velocity.x > physic.stopTreshold) {
									physic.forces.add(new Vector2D(-physic.defaultAccel.x * 4, 0.0f));
								} else if (physic.velocity.x < -physic.stopTreshold) {
									physic.forces.add(new Vector2D(physic.defaultAccel.x * 4, 0.0f));
								} else {
									physic.velocity.x = 0.0f;
									physic.acceleration.x = 0.0f;
								}
							}
							if (physic.velocity.y != 0.0f) {
								if (physic.velocity.y > physic.stopTreshold) {
									physic.forces.add(new Vector2D(0.0f, -physic.defaultAccel.y * 4));
								} else if (physic.velocity.y < -physic.stopTreshold) {
									physic.forces.add(new Vector2D(0.0f, physic.defaultAccel.y * 4));
								} else {
									physic.velocity.y = 0.0f;
									physic.acceleration.y = 0.0f;
								}
							}
							logger.debug("request break");
						}
						// up !
						if (ih.keys[KeyEvent.VK_UP] && Math.abs(physic.velocity.y) < physic.defaultMaxSpeedAccel.y) {
							Vector2D moveUp = new Vector2D(0.0f, -physic.defaultAccel.y);
							physic.forces.add(moveUp);
							logger.debug("add up move by {}", moveUp);
						}
						// nothing to do today.
						if (ih.keys[KeyEvent.VK_DOWN] && Math.abs(physic.velocity.y) < physic.defaultMaxSpeedAccel.x) {
							Vector2D moveDown = new Vector2D(0.0f, physic.defaultAccel.y);
							physic.forces.add(moveDown);
							logger.debug("add up move by {}", moveDown);
							physic.forces.add(moveDown);
						}

						// request for a screenshot
						if (ih.keys[KeyEvent.VK_S]) {
							app.requestScreenshot = true;
						}

						// reset all
						if (ih.keys[KeyEvent.VK_DELETE]) {
							physic.acceleration.x = 0.0f;
							physic.acceleration.y = 0.0f;
							physic.velocity.x = 0.0f;
							physic.velocity.y = 0.0f;
							pos.position.x = (app.win.getWidth() * 0.5f);
							pos.position.y = (app.win.getHeight() * 0.5f);
						}

						// Switch debug display mode.
						if (ih.keys[KeyEvent.VK_D] && !ih.prevKeys[KeyEvent.VK_D]) {

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
				});

	}
}

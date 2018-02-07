/**
 * GE DoseWatch
 * ecpfmk
 *
 * @copyright 2018
 */
package com.ge.prototype.ecpfmk;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ge.prototype.ecpfmk.io.InputHandler;
import com.ge.prototype.ecpfmk.math.Vector2D;
import com.ge.prototype.ecpfmk.ui.DebugHelper;
import com.ge.prototype.ecpfmk.ui.Messages;
import com.ge.prototype.ecpfmk.ui.Window;

/**
 * The main application class to animate the simulation.
 * 
 * @author Frédéric Delorme<frederic.delorme@ge.com>
 *
 */
public class Application implements Runnable {

	private static final Logger logger = LoggerFactory.getLogger(Application.class);

	/**
	 * exit request flag.
	 */
	private boolean exit = false;
	/**
	 * pause request flag.
	 */
	private boolean pause = false;

	/**
	 * debug flag to activate trace on display.
	 */
	private boolean debug = true;

	/**
	 * let's play with a buffered rendering.
	 */
	private BufferedImage buff;

	/**
	 * Graphics interfaces to process rendering operation and the rendering to
	 * screen.
	 */
	private Graphics2D g;

	private Car car = null;

	private Window win = null;

	// private Dimension dim;

	private float elapsed = 0.0f;

	/**
	 * initialize simulation application.
	 */
	public Application() {
		win = new Window(Messages.get("main.title"), new Dimension(640, 480));
		buff = new BufferedImage(win.getWidth(), win.getHeight(), BufferedImage.TYPE_INT_ARGB);
		this.g = (Graphics2D) buff.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		//g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	}

	/**
	 * Main cycle for this application.
	 */
	public void run() {
		initialize();
		float currentTime = System.nanoTime();
		float previousTime = currentTime;
		float dt = 0.0f;
		while (!exit) {
			currentTime = System.nanoTime();
			dt = Math.max((currentTime - previousTime) / 1000000.0f, 20.0f);
			input(win.getInputHandler());
			if (!pause) {
				update(dt);
			}
			render(g, dt);
			waitFPS();
			elapsed = dt;
			previousTime = currentTime;
			postRenderOperation();
		}
		dispose();
	}

	/**
	 * 
	 */
	private void postRenderOperation() {
		car.forces.clear();
	}

	/**
	 * wait for next FPS.
	 */
	private void waitFPS() {
		try {
			Thread.sleep((int) (1000.0f / 60.0f));
		} catch (InterruptedException e) {
			logger.error("Unable to wait some milli's !", e);
		}
	}

	/**
	 * Initialize object for this app.
	 */
	private void initialize() {
		World world = new World(new Vector2D(0.0f, 98.1f));
		car = new Car("myCar").setWorld(world).setVelocity(new Vector2D(0.0f, 0.0f))
				.setPosition(new Vector2D(win.getWidth() / 2, win.getHeight() / 2)).setSize(new Rectangle(50, 20));
	}

	/**
	 * release all the objects resources.
	 */
	private void dispose() {
		car = null;
		win.dispose();
	}

	/**
	 * Manage Key events.
	 * 
	 * @param ih
	 */
	public void input(InputHandler ih) {
		// accelerate
		if (ih.keys[KeyEvent.VK_LEFT] && Math.abs(car.velocity.x) < Car.CAR_MAX_SPEED) {
			Vector2D moveLeft = new Vector2D(-Car.CAR_ACCEL_X, 0.0f);
			car.forces.add(moveLeft);
			logger.debug("add left move by {}", moveLeft);
		}
		// break !
		if (ih.keys[KeyEvent.VK_RIGHT] && Math.abs(car.velocity.x) < Car.CAR_MAX_SPEED) {
			Vector2D moveRight = new Vector2D(Car.CAR_ACCEL_X, 0.0f);
			car.forces.add(moveRight);
			logger.debug("add right move by {}", moveRight);
		}
		// Stop !
		if (ih.keys[KeyEvent.VK_SPACE] && Math.abs(car.velocity.x) < Car.CAR_MAX_SPEED) {
			if (car.velocity.x != 0.0f) {
				if (car.velocity.x > car.stopTreshold) {
					car.forces.add(new Vector2D(-Car.CAR_ACCEL_X * 4, 0.0f));
				} else if (car.velocity.x < -car.stopTreshold) {
					car.forces.add(new Vector2D(Car.CAR_ACCEL_X * 4, 0.0f));
				} else {
					car.velocity.x = 0.0f;
					car.acceleration.x = 0.0f;
				}
			}
			if (car.velocity.y != 0.0f) {
				if (car.velocity.y > car.stopTreshold) {
					car.forces.add(new Vector2D(0.0f, -Car.CAR_ACCEL_X * 4));
				} else if (car.velocity.y < -car.stopTreshold) {
					car.forces.add(new Vector2D(0.0f, Car.CAR_ACCEL_X * 4));
				} else {
					car.velocity.y = 0.0f;
					car.acceleration.y = 0.0f;
				}
			}
			logger.debug("request break");
		}
		// up !
		if (ih.keys[KeyEvent.VK_UP] && Math.abs(car.velocity.y) < Car.CAR_MAX_SPEED) {
			Vector2D moveUp = new Vector2D(0.0f, -Car.CAR_ACCEL_Y);
			car.forces.add(moveUp);
			logger.debug("add up move by {}", moveUp);
		}
		// nothing to do today.
		if (ih.keys[KeyEvent.VK_DOWN] && Math.abs(car.velocity.y) < Car.CAR_MAX_SPEED) {
			Vector2D moveDown = new Vector2D(0.0f, Car.CAR_ACCEL_Y);
			car.forces.add(moveDown);
			logger.debug("add up move by {}", moveDown);
			car.forces.add(moveDown);
		}

		// reset all
		if (ih.keys[KeyEvent.VK_DELETE]) {
			car.acceleration.x = 0.0f;
			car.acceleration.y = 0.0f;
			car.velocity.x = 0.0f;
			car.velocity.y = 0.0f;
			car.position.x = win.getWidth() / 2;
			car.position.y = win.getHeight() / 2;
		}

		// Switch debug display mode.
		if (ih.keys[KeyEvent.VK_D]) {
			debug = !debug;
		}

		// stop rendering.
		if (ih.keys[KeyEvent.VK_PAUSE] || ih.keys[KeyEvent.VK_P]) {
			this.pause = !this.pause;
		}
		// Escape and quit simulation.
		if (ih.keys[KeyEvent.VK_ESCAPE] || ih.keys[KeyEvent.VK_Q]) {
			this.exit = true;
		}
	}

	/**
	 * update entity of this simulation.
	 * 
	 * @param dt
	 */
	public void update(float dt) {
		car.update(dt);
		keepConstrainedTo(car, win.getDimension());
	}

	/**
	 * keep car in the field of view.
	 * 
	 * @param car2
	 * @param dim
	 */
	private void keepConstrainedTo(Car car, Dimension dim) {
		if (car.position.x > dim.getWidth() - car.size.width) {
			car.position.x = (float) dim.getWidth() - car.size.width;
			car.velocity.x *= -1 * car.elasticity;
		}

		if (car.position.x <= 0) {
			car.position.x = 0;
			car.velocity.x *= -1 * car.elasticity;

		}
		if (car.position.y > dim.getHeight() - car.size.height) {
			car.position.y = (float) dim.getHeight() - car.size.height;
			car.velocity.y *= -1 * car.elasticity;
		}
		if (car.position.y <= 0) {
			car.position.y = 0.0f;
			car.velocity.y *= -1 * car.elasticity;
		}
	}

	/**
	 * render entity for this simulation.
	 * 
	 * @param g
	 */
	public void render(Graphics2D g, float dt) {
		g.setBackground(Color.BLACK);
		g.clearRect(0, 0, win.getWidth(), win.getHeight());
		car.render(g);

		if (pause) {
			displayPause(g);
		}
		if (debug) {
			DebugHelper.showEntityInfo(g, car);

		}
		g.setColor(Color.GRAY);
		g.drawRect(0, 0, win.getWidth() - 1, win.getHeight() - 1);
		win.getGraphics().drawImage(buff, 0, 0, null);
	}

	/**
	 * @param g
	 */
	private void displayPause(Graphics2D g) {
		String txtPause = Messages.get("main.pause.label");

		Font b = g.getFont();
		Font f = b.deriveFont(24.0f);

		g.setFont(f);
		g.setColor(Color.WHITE);

		int fontHeight = g.getFontMetrics().getHeight();
		int txtWidth = g.getFontMetrics().stringWidth(txtPause);

		g.drawString(txtPause, (win.getWidth() - txtWidth) / 2, (win.getHeight() - fontHeight) / 2);

		g.setFont(b);
	}

	/**
	 * Start the simulation.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		Application app = new Application();
		app.run();

	}
}

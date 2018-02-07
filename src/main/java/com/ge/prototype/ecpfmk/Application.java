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
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ge.prototype.ecpfmk.io.InputHandler;
import com.ge.prototype.ecpfmk.math.Vector2D;
import com.ge.prototype.ecpfmk.ui.DebugHelper;

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
	 * input keyboard handler.
	 */
	private InputHandler ih;

	/**
	 * rendering frame (Java)
	 */
	private JFrame frame = null;
	/**
	 * let's play with a buffered rendering.
	 */
	private BufferedImage buff;

	/**
	 * Graphics interfaces to process rendering operation and the rendering to
	 * screen.
	 */
	private Graphics2D g;
	private Graphics2D gScreen;

	private Car car = null;

	Dimension dim = new Dimension(640, 480);

	private float elapsed = 0.0f;

	/**
	 * initialize simulation application.
	 */
	public Application(JFrame frame) {
		this.frame = frame;
		this.ih = new InputHandler();
		frame.setPreferredSize(dim);
		frame.setSize(dim);
		frame.setMaximumSize(dim);
		frame.setMinimumSize(dim);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.addKeyListener(ih);
		frame.setAlwaysOnTop(true);
		frame.setUndecorated(true);

		frame.pack();
		frame.setVisible(true);

		this.gScreen = (Graphics2D) frame.getGraphics();

		buff = new BufferedImage((int) dim.getWidth(), (int) dim.getHeight(), BufferedImage.TYPE_INT_ARGB);
		this.g = (Graphics2D) buff.createGraphics();

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
			input(ih);
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
				.setPosition(new Vector2D(320.0f, 240.0f)).setSize(new Rectangle(50, 20));
	}

	/**
	 * release all the objects resources.
	 */
	private void dispose() {
		car = null;
		ih = null;
		frame.dispose();
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
			car.position.x = 320.0f;
			car.position.y = 240.0f;
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
		keepConstrainedTo(car, dim);
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
		g.clearRect(0, 0, 640, 480);
		car.render(g);

		if (pause) {
			Font b = g.getFont();
			Font f = b.deriveFont(24.0f);
			g.setFont(f);
			g.setColor(Color.WHITE);
			g.drawString("Pause", 300, 230);
			g.setFont(b);
		}
		if (debug) {
			DebugHelper.showEntityInfo(g, car);

		}
		g.setColor(Color.GRAY);
		g.drawRect(0, 0, 640 - 1, 480 - 1);
		gScreen.drawImage(buff, 0, 0, null);
	}

	/**
	 * Start the simulation.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame("Car Simulation");
		Application app = new Application(frame);

		app.run();

	}
}

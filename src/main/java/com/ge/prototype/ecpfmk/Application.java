/**
 * GE DoseWatch
 * ecpfmk
 *
 * @copyright 2018
 */
package com.ge.prototype.ecpfmk;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;

import com.ge.prototype.ecpfmk.io.InputHandler;
import com.ge.prototype.ecpfmk.math.Vector2D;

/**
 * The main application class to animate the simulation.
 * 
 * @author Frédéric Delorme<frederic.delorme@ge.com>
 *
 */
public class Application implements Runnable {
	/**
	 * exit request flag.
	 */
	private boolean exit = false;
	/**
	 * pause request flag.
	 */
	private boolean pause = false;
	/**
	 * input keyboard handler.
	 */
	private InputHandler ih;

	/**
	 * rendering frame (Java)
	 */
	private JFrame frame = null;
	/**
	 * Graphics interface to process rendering operation.
	 */
	private Graphics2D g;

	private Car car = null;

	private static final float SPEED = 2.0f;

	/**
	 * 
	 */
	public Application(JFrame frame) {
		this.frame = frame;
		this.ih = new InputHandler();

		Dimension dim = new Dimension(640, 480);
		frame.setPreferredSize(dim);
		frame.setSize(dim);
		frame.setMaximumSize(dim);
		frame.setMinimumSize(dim);

		frame.addKeyListener(ih);
		frame.pack();
		frame.setVisible(true);
		this.g = (Graphics2D) frame.getGraphics();

	}

	/**
	 * Main cycle for this application.
	 */
	public void run() {
		initialize();
		float currentTime = System.currentTimeMillis();
		float previousTime = System.currentTimeMillis();

		while (!exit) {
			currentTime = System.currentTimeMillis();
			float dt = currentTime - previousTime;
			input(ih);
			if (!pause) {
				update(dt);
			}
			render(g);
			waitFPS();
			previousTime = currentTime;
		}
		dispose();
	}

	/**
	 * 
	 */
	private void waitFPS() {
		try {
			Thread.sleep((int) (1000.0f / 60.0f));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Initialize object for this app.
	 */
	private void initialize() {

		car = new Car(new Vector2D(0.0f, 0.0f), new Vector2D(320.0f, 240.0f), new Rectangle(50, 20));
	}

	/**
	 * release all the objects resources.
	 */
	private void dispose() {
		car = null;
		ih = null;
	}

	public void input(InputHandler ih) {
		if (ih.keys[KeyEvent.VK_LEFT]) {
			car.getVelocity().x -= SPEED;
		}
		if (ih.keys[KeyEvent.VK_RIGHT]) {
			car.getVelocity().x += SPEED;

		}
		if (ih.keys[KeyEvent.VK_UP]) {
			car.getVelocity().x = 0;
		}
		if (ih.keys[KeyEvent.VK_DOWN]) {
			// to be defined later.
		}
		if (ih.keys[KeyEvent.VK_PAUSE]) {
			this.pause = !this.pause;
		}
		if (ih.keys[KeyEvent.VK_ESCAPE]) {
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
	}

	/**
	 * render entity for this simulation.
	 * 
	 * @param g
	 */
	public void render(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.clearRect(0, 0, 640, 480);
		car.render(g);
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

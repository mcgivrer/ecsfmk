/**
 * McGivrer's Blog 
 *
 * Entity Component System framework 
 *
 * @copyright 2018
 */
package fr.mcgivrer.prototype.ecsfmk;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.mcgivrer.prototype.ecsfmk.entities.Car;
import fr.mcgivrer.prototype.ecsfmk.entities.Entity;
import fr.mcgivrer.prototype.ecsfmk.io.InputHandler;
import fr.mcgivrer.prototype.ecsfmk.math.Vector2D;
import fr.mcgivrer.prototype.ecsfmk.math.physic.World;
import fr.mcgivrer.prototype.ecsfmk.systems.MoveSystem;
import fr.mcgivrer.prototype.ecsfmk.systems.InputSystem;
import fr.mcgivrer.prototype.ecsfmk.systems.RenderSystem;
import fr.mcgivrer.prototype.ecsfmk.ui.Messages;
import fr.mcgivrer.prototype.ecsfmk.ui.Window;

/**
 * The main application class to animate the simulation.
 * 
 * @author Frédéric Delorme<frederic.delorme@ge.com>
 *
 */
public class Application implements Runnable {

	private static final Logger logger = LoggerFactory.getLogger(Application.class);

	public Map<String, Entity> entities = new ConcurrentHashMap<String, Entity>();

	/**
	 * exit request flag.
	 */
	public boolean exit = false;
	/**
	 * pause request flag.
	 */
	public boolean pause = false;

	/**
	 * debug flag to activate trace on display.
	 */
	public boolean debug = true;

	private Car theCar = null;

	public Window win = null;

	private MoveSystem carSystem;
	private RenderSystem renderSystem;
	private InputSystem inputSystem;

	/**
	 * initialize simulation application.
	 */
	public Application() {
		win = new Window(Messages.get("main.title"), new Dimension(640, 480));
	}

	/**
	 * Initialize object for this app.
	 */
	private void initialize() {
		carSystem = new MoveSystem(this, win.getDimension());
		renderSystem = new RenderSystem(this);
		inputSystem = new InputSystem(this, win.getInputHandler());

		World world = new World(new Vector2D(0.0f, 98.1f));
		theCar = new Car("car");

		theCar.physic
			.setWorld(world)
			.setVelocity(new Vector2D(0.0f, 0.0f));

		theCar.pos
			.setPosition(new Vector2D(win.getWidth() / 2, win.getHeight() / 2))
			.setSize(new Rectangle(50, 20));

		entities.put(theCar.name, theCar);
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
			input(win.getInputHandler(), dt);
			if (!pause) {
				update(dt);
			}
			render(dt);
			waitFPS();
			previousTime = currentTime;
			postRenderOperation();
		}
		dispose();
	}

	/**
	 * 
	 */
	private void postRenderOperation() {
		theCar.physic.forces.clear();
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
	 * release all the objects resources.
	 */
	private void dispose() {
		theCar = null;
		win.dispose();
	}

	/**
	 * Manage Key events.
	 * 
	 * @param ih
	 */
	public void input(InputHandler ih, float dt) {
		inputSystem.update(dt);
	}

	/**
	 * update entity of this simulation.
	 * 
	 * @param dt
	 */
	public void update(float dt) {
		carSystem.update(dt);
	}

	/**
	 * render entity for this simulation.
	 * 
	 * @param g
	 */
	public void render(float dt) {
		renderSystem.update(dt);
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

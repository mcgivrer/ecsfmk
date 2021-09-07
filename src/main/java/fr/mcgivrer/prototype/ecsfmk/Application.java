/*
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

import fr.mcgivrer.prototype.ecsfmk.systems.SystemManager;
import fr.mcgivrer.prototype.ecsfmk.systems.api.InputAction;
import fr.mcgivrer.prototype.ecsfmk.systems.api.RenderAction;
import fr.mcgivrer.prototype.ecsfmk.systems.api.UpdateAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.mcgivrer.prototype.ecsfmk.entities.Car;
import fr.mcgivrer.prototype.ecsfmk.entities.Entity;
import fr.mcgivrer.prototype.ecsfmk.io.InputHandler;
import fr.mcgivrer.prototype.ecsfmk.math.Vector2D;
import fr.mcgivrer.prototype.ecsfmk.math.physic.World;
import fr.mcgivrer.prototype.ecsfmk.systems.PhysicSystem;
import fr.mcgivrer.prototype.ecsfmk.systems.PlayerInputSystem;
import fr.mcgivrer.prototype.ecsfmk.systems.RenderSystem;
import fr.mcgivrer.prototype.ecsfmk.ui.Messages;
import fr.mcgivrer.prototype.ecsfmk.ui.Window;

/**
 * The main application class to animate the simulation.
 *
 * @author Frédéric Delorme<frederic.delorme@snapgames.fr>
 */
public class Application implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public Map<String, Entity<?>> entities = new ConcurrentHashMap<>();

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

    private Car theCar;

    public Window win;

    private PhysicSystem moveSystem;
    private RenderSystem renderSystem;
    private PlayerInputSystem pis;

    public boolean requestScreenshot;

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
        PlayerInputSystem pis = new PlayerInputSystem(this, win.getInputHandler());
        SystemManager.get(this).add(pis);
        moveSystem = new PhysicSystem(this, win.getDimension());
        SystemManager.get(this).add(moveSystem);
        renderSystem = new RenderSystem(this);
        SystemManager.get(this).add(renderSystem);

        World world = new World(new Vector2D(0.0f, 98.1f));
        theCar = new Car("car");

        theCar.setPosition(new Vector2D(win.getWidth() * 0.5f, win.getHeight() * 0.5f))
                .setSize(new Rectangle(50, 20))
                .setVelocity(new Vector2D(0.0f, 0.0f))
                .setResistance(0.90f)
                .setMass(2000.0f)
                .setWorld(world);

        add(theCar);
    }

    private void add(Entity entity) {
        entities.put(entity.name, entity);
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
            update(dt);
            render(dt);
            waitFPS();
            previousTime = currentTime;
            postOperation();
        }
        dispose();
    }

    /**
     *
     */
    private void postOperation() {
        moveSystem.postOperation();
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
     * @param ih the InputHandler to be used inside the input method.
     * @param dt the elapsed time since previous call
     */
    public void input(InputHandler ih, float dt) {
        SystemManager.get(this).findAction(InputAction.class).forEach(ia -> ((InputAction) ia).input(ih, dt));
    }

    /**
     * update entity of this simulation.
     *
     * @param dt the elapsed time since previous call.
     */
    public void update(float dt) {
        SystemManager.get(this).findAction(UpdateAction.class).forEach(ua -> ((UpdateAction) ua).update(dt));
    }

    /**
     * render entity for this simulation.
     *
     * @param dt the elapsed time since previous call.
     */
    public void render(float dt) {
        SystemManager.get(this).findAction(RenderAction.class).forEach(ra -> ((RenderAction) ra).render(dt));
    }

    /**
     * Start the simulation.
     *
     * @param args All the command line arguments to execute our process.
     */
    public static void main(String[] args) {

        Application app = new Application();
        app.run();

    }
}

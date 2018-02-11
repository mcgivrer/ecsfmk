/**
 * McGivrer's Blog 
 *
 * Entity Component System framework 
 *
 * @copyright 2018
 */
package fr.mcgivrer.prototype.ecsfmk.systems;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import fr.mcgivrer.prototype.ecsfmk.Application;
import fr.mcgivrer.prototype.ecsfmk.entities.Car;
import fr.mcgivrer.prototype.ecsfmk.ui.DebugHelper;
import fr.mcgivrer.prototype.ecsfmk.ui.Messages;

/**
 * @author Frédéric Delorme<frederic.delorme@ge.com>
 *
 */
public class RenderSystem implements System {
	/**
	 * let's play with a buffered rendering.
	 */
	private BufferedImage buff;

	/**
	 * Graphics interfaces to process rendering operation and the rendering to
	 * screen.
	 */
	public Graphics2D g;

	private Application app;

	public RenderSystem(Application app) {
		this.app = app;
		buff = new BufferedImage(app.win.getWidth(), app.win.getHeight(), BufferedImage.TYPE_INT_ARGB);
		this.g = (Graphics2D) buff.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		// g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
		// RenderingHints.VALUE_ANTIALIAS_ON);
	}

	/**
	 * Application
	 * 
	 * @param app
	 * @param dt
	 */
	public void update(float dt) {
		Car theCar = (Car) app.entities.get("car");
		g.setBackground(Color.BLACK);
		g.clearRect(0, 0, app.win.getWidth(), app.win.getHeight());

		renderCar(g, dt, theCar);

		if (app.pause) {
			displayPause(g);
		}
		if (app.debug) {
			DebugHelper.showEntityInfo(g, theCar);

		}
		g.setColor(Color.GRAY);
		g.drawRect(0, 0, app.win.getWidth() - 1, app.win.getHeight() - 1);
		app.win.getGraphics().drawImage(buff, 0, 0, null);

	}

	/**
	 * Process the Car rendering.
	 * 
	 * @param g
	 *            the Graphics2D interface to render things
	 * @param dt
	 *            the elapsed time (will be use with animated sprites).
	 * @param c
	 *            the Car to be rendered/animated.
	 */
	public void renderCar(Graphics2D g, float dt, Car c) {
		g.setColor(c.render.color);
		g.fillRect((int) c.pos.position.x, (int) c.pos.position.y, c.pos.size.width, c.pos.size.height);

	}

	/**
	 * Display translated Pause message
	 * 
	 * @param g
	 *            the Graphics2D interface to render things.
	 */
	private void displayPause(Graphics2D g) {
		String txtPause = Messages.get("main.pause.label");

		Font b = g.getFont();
		Font f = b.deriveFont(24.0f);

		g.setFont(f);
		g.setColor(Color.WHITE);

		int fontHeight = g.getFontMetrics().getHeight();
		int txtWidth = g.getFontMetrics().stringWidth(txtPause);

		g.drawString(txtPause, (app.win.getWidth() - txtWidth) / 2, (app.win.getHeight() - fontHeight) / 2);

		g.setFont(b);
	}

}

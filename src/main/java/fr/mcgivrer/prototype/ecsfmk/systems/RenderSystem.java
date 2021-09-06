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
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.mcgivrer.prototype.ecsfmk.Application;
import fr.mcgivrer.prototype.ecsfmk.components.Component;
import fr.mcgivrer.prototype.ecsfmk.components.PositionComponent;
import fr.mcgivrer.prototype.ecsfmk.components.RenderComponent;
import fr.mcgivrer.prototype.ecsfmk.entities.Entity;
import fr.mcgivrer.prototype.ecsfmk.ui.DebugHelper;
import fr.mcgivrer.prototype.ecsfmk.ui.Messages;

/**
 * @author Frédéric Delorme<frederic.delorme@snapgames.fr>
 *
 */
public class RenderSystem implements System {

	private static final Logger logger = LoggerFactory.getLogger(RenderSystem.class);

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
		this.g = buff.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	}

	/**
	 * Application
	 * 
	 * @param app
	 * @param dt
	 */
	public void update(float dt) {
		// clear buffer
		g.setBackground(Color.BLACK);
		g.clearRect(0, 0, app.win.getWidth(), app.win.getHeight());

		// render all entities
		app.entities.values().stream()
				.filter(v -> v.getComponent("physic").isPresent() && v.getComponent("position").isPresent())
				.forEach(e -> {

					renderEntity(g, dt, e);
					if (app.debug) {
						DebugHelper.showEntityInfo(g, e);

					}
				});

		// display pause message (if required)
		if (app.pause) {
			displayPause(g);
		}

		// draw buffer.
		g.setColor(Color.GRAY);
		g.drawRect(0, 0, app.win.getWidth() - 1, app.win.getHeight() - 1);
		app.win.getGraphics().drawImage(buff, 0, 0, null);

		// capture screen shot (if requested)
		if (app.requestScreenshot) {
			writeScreenshot(app, buff);
		}

	}

	/**
	 * Write a screenshot from the current buffer.
	 * 
	 * @param app  Application attached to.
	 * @param buff the buffer to be written.
	 */
	private void writeScreenshot(Application app, BufferedImage buff) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd-hms");
			String strDate = sdf.format(new Date());
			File sc = new File(String.format("./screenshot_%s.png", strDate));
			ImageIO.write(buff, "PNG", sc);
		} catch (IOException e) {
			logger.error("unable to write the screenshot", e);
		}
		app.requestScreenshot = false;
	}

	/**
	 * Process the Car rendering.
	 * 
	 * @param g  the Graphics2D interface to render things
	 * @param dt the elapsed time (will be use with animated sprites).
	 * @param c  the Car to be rendered/animated.
	 */
	public void renderEntity(Graphics2D g, float dt, Entity<?> c) {

		Optional<Component> rendC = c.getComponent("render");
		Optional<Component> posC = c.getComponent("position");

		if (rendC.isPresent() && posC.isPresent()) {
			RenderComponent render = (RenderComponent) rendC.get();
			g.setColor(render.color);

			PositionComponent pos = (PositionComponent) posC.get();
			g.fillRect((int) pos.position.x, (int) pos.position.y, pos.size.width, pos.size.height);
		}
	}

	/**
	 * Display translated Pause message
	 * 
	 * @param g the Graphics2D interface to render things.
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

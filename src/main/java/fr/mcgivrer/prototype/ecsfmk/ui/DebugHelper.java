/**
 * McGivrer's Blog 
 *
 * Entity Component System framework 
 *
 * @copyright 2018
 */
package fr.mcgivrer.prototype.ecsfmk.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

import fr.mcgivrer.prototype.ecsfmk.components.PhysicComponent;
import fr.mcgivrer.prototype.ecsfmk.components.PositionComponent;
import fr.mcgivrer.prototype.ecsfmk.entities.Entity;
import fr.mcgivrer.prototype.ecsfmk.math.Vector2D;

/**
 * Utilities used to debug info on display.
 * 
 * @author Frédéric Delorme<frederic.delorme@snapgames.fr>
 *
 */
public class DebugHelper {

	private DebugHelper() {
	}

	/**
	 * display some debug info about car.
	 * 
	 * @param g
	 * @param car
	 */
	public static void showEntityInfo(Graphics2D g, Entity<?> e) {

		PositionComponent pos = (PositionComponent) e.getComponent("position").get();
		PhysicComponent physic = (PhysicComponent) e.getComponent("physic").get();

		g.setColor(Color.YELLOW);
		g.drawString(String.format("name: %s,", e.name), pos.position.x + pos.size.width + 4, pos.position.y - 40);
		g.drawString(String.format("p: %s,", pos.position), pos.position.x + pos.size.width + 4, pos.position.y - 28);
		float cx = pos.position.x + (pos.size.width * 0.5f);
		float cy = pos.position.y + (pos.size.height * 0.5f);
		g.setStroke(new BasicStroke(2.0f));
		g.setColor(Color.RED);
		g.drawLine((int) cx, (int) cy, (int) (cx + physic.velocity.x), (int) (cy + physic.velocity.y));
		g.drawString(String.format("v: %s,", physic.velocity), pos.position.x + pos.size.width + 4,
				pos.position.y - 16);
		g.setColor(Color.GREEN);
		g.drawLine((int) cx, (int) cy, (int) (cx + (physic.acceleration.x / 10)),
				(int) (cy + (physic.acceleration.y / 10)));
		g.drawString(String.format("a: %s}", physic.acceleration), pos.position.x + pos.size.width + 4, pos.position.y);
		g.setStroke(new BasicStroke(1.0f, 1, 1, 1.0f, new float[] { 0.1f, 1.0f }, 0.5f));
		for (Vector2D v : physic.forces) {
			g.drawLine((int) cx, (int) cy, (int) (cx + (v.x / 2)), (int) (cy + (v.y / 2)));
		}
		g.setStroke(new BasicStroke(1.0f));

	}

}

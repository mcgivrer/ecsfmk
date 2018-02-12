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
 * @author Frédéric Delorme<frederic.delorme@ge.com>
 *
 */
public class DebugHelper {
	/**
	 * display some debug info about car.
	 * 
	 * @param g
	 * @param car
	 */
	public static void showEntityInfo(Graphics2D g, Entity car) {
		PositionComponent pos = (PositionComponent) car.getComponent("position");
		PhysicComponent phy = (PhysicComponent) car.getComponent("physic");
		
		g.setColor(Color.YELLOW);
		g.drawString(String.format("name: %s,", car.name), pos.position.x + pos.size.width + 4,
				pos.position.y - 40);
		g.drawString(String.format("p: %s,", pos.position), pos.position.x + pos.size.width + 4,
				pos.position.y - 28);
		float cx = pos.position.x + (pos.size.width / 2);
		float cy = pos.position.y + (pos.size.height / 2);
		g.setStroke(new BasicStroke(2.0f));
		g.setColor(Color.RED);
		g.drawLine((int) cx, (int) cy, (int) (cx + phy.velocity.x), (int) (cy + phy.velocity.y));
		g.drawString(String.format("v: %s,", phy.velocity), pos.position.x + pos.size.width + 4,
				pos.position.y - 16);
		g.setColor(Color.GREEN);
		g.drawLine((int) cx, (int) cy, (int) (cx + (phy.acceleration.x / 10)),
				(int) (cy + (phy.acceleration.y / 10)));
		g.drawString(String.format("a: %s}", phy.acceleration), pos.position.x + pos.size.width + 4,
				pos.position.y);
		g.setStroke(new BasicStroke(1.0f, 1, 1, 1.0f, new float[] { 0.1f, 1.0f }, 0.5f));
		for (Vector2D v : phy.forces) {
			g.drawLine((int) cx, (int) cy, (int) (cx + (v.x / 2)), (int) (cy + (v.y / 2)));
		}
		g.setStroke(new BasicStroke(1.0f));

	}

}

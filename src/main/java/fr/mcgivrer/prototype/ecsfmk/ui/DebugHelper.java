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

import fr.mcgivrer.prototype.ecsfmk.entities.Car;
import fr.mcgivrer.prototype.ecsfmk.math.Vector2D;

/**
 * Utilities used to debug info on display.
 * 
 * @author Frédéric Delorme<frederic.delorme@snapgames.fr>
 *
 */
public class DebugHelper {
	/**
	 * display some debug info about car.
	 * 
	 * @param g
	 * @param car
	 */
	public static void showEntityInfo(Graphics2D g, Car car) {
		g.setColor(Color.YELLOW);
		g.drawString(String.format("name: %s,", car.name), car.pos.position.x + car.pos.size.width + 4,
				car.pos.position.y - 40);
		g.drawString(String.format("p: %s,", car.pos.position), car.pos.position.x + car.pos.size.width + 4,
				car.pos.position.y - 28);
		float cx = car.pos.position.x + (car.pos.size.width / 2);
		float cy = car.pos.position.y + (car.pos.size.height / 2);
		g.setStroke(new BasicStroke(2.0f));
		g.setColor(Color.RED);
		g.drawLine((int) cx, (int) cy, (int) (cx + car.physic.velocity.x), (int) (cy + car.physic.velocity.y));
		g.drawString(String.format("v: %s,", car.physic.velocity), car.pos.position.x + car.pos.size.width + 4,
				car.pos.position.y - 16);
		g.setColor(Color.GREEN);
		g.drawLine((int) cx, (int) cy, (int) (cx + (car.physic.acceleration.x / 10)),
				(int) (cy + (car.physic.acceleration.y / 10)));
		g.drawString(String.format("a: %s}", car.physic.acceleration), car.pos.position.x + car.pos.size.width + 4,
				car.pos.position.y);
		g.setStroke(new BasicStroke(1.0f, 1, 1, 1.0f, new float[] { 0.1f, 1.0f }, 0.5f));
		for (Vector2D v : car.physic.forces) {
			g.drawLine((int) cx, (int) cy, (int) (cx + (v.x / 2)), (int) (cy + (v.y / 2)));
		}
		g.setStroke(new BasicStroke(1.0f));

	}

}

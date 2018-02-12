/**
 * SnapGames
 * 
 * Game Development Java
 * 
 * ecsfmk
 * 
 * @year 2018
 */
package fr.mcgivrer.prototype.ecsfmk.entities;

import fr.mcgivrer.prototype.ecsfmk.components.PhysicComponent;
import fr.mcgivrer.prototype.ecsfmk.components.PositionComponent;
import fr.mcgivrer.prototype.ecsfmk.components.RenderComponent;
import fr.mcgivrer.prototype.ecsfmk.math.physic.World;

/**
 * 
 * @author Frédéric Delorme
 *
 */
public class Drop extends Entity {
	
	public Drop(World world, String name) {
		super(world, name);
		addComponent(new PositionComponent());
		addComponent(new PhysicComponent());
		addComponent(new RenderComponent());
	}
	

}

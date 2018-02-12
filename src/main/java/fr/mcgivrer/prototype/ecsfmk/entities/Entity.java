/**
 * McGivrer's Blog 
 *
 * Entity Component System framework 
 *
 * @copyright 2018
 */
package fr.mcgivrer.prototype.ecsfmk.entities;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import fr.mcgivrer.prototype.ecsfmk.components.Component;
import fr.mcgivrer.prototype.ecsfmk.math.physic.World;

/**
 * A Car class to manage and display a Car :)
 * 
 * @author Frédéric Delorme<frederic.delorme@ge.com>
 *
 */
public class Entity {
	// Entity Name
	public String name;

	private Map<String, Component> components = new ConcurrentHashMap<>();

	private World world;

	/**
	 * A new constructor to build car with all its attributes values.
	 */
	public Entity(World world, String name) {
		this();
		this.name = name;
		this.world = world;
	}

	/**
	 * Default constructor to create a new Car.
	 */
	public Entity() {
		super();
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * retrieve the component named <code>name</code> from the component's list.
	 * 
	 * @param name
	 * @return
	 */
	public Component getComponent(String name) {
		return components.get(name);
	}

	/**
	 * Add a component to the entity.
	 * 
	 * @param component
	 */
	public void addComponent(Component component) {
		this.components.put(component.getName(), component);
	}

	/**
	 * remove a component from the list.
	 * 
	 * @param name
	 */
	public void removeComponent(String name) {
		this.components.remove(name);
	}

}

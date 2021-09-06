/**
 * McGivrer's Blog
 * <p>
 * Entity Component System framework
 *
 * @copyright 2018
 */
package fr.mcgivrer.prototype.ecsfmk.entities;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import fr.mcgivrer.prototype.ecsfmk.components.Component;

/**
 * A Car class to manage and display a Car :)
 *
 * @author Frédéric Delorme<frederic.delorme@snapgames.fr>
 */
public class Entity<T> {
    // Entity Name
    public String name;

    private Map<String, Component> components = new HashMap<>();

    /**
     * A new constructor to build car with all its attributes values.
     */
    public Entity(String name) {
        this();
        this.name = name;
    }

    /**
     * Default constructor to create a new Car.
     */
    public Entity() {
        super();
    }

    public boolean hasComponent(String name) {
        return components.containsKey(name);
    }

    public Optional<Component> getComponent(String name) {
        return Optional.of(components.get(name));
    }

    public T add(Component c) {
        components.put(c.getName(), c);
        return (T) this;
    }

    public void removeComponent(String componentName) {
        components.remove(componentName);
    }

    public Collection<Component> getComponents() {
        return components.values();
    }
}

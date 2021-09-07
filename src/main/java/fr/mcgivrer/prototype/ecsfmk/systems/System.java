/*
 * McGivrer's Blog
 *
 * Entity Component System framework
 *
 * @copyright 2018
 */
package fr.mcgivrer.prototype.ecsfmk.systems;

/**
 * The interface to create new System.
 * A System must inherit one ore more on the following interface :
 *
 * - {@link fr.mcgivrer.prototype.ecsfmk.systems.api.InputAction},
 * - {@link fr.mcgivrer.prototype.ecsfmk.systems.api.UpdateAction}
 * - and {@link fr.mcgivrer.prototype.ecsfmk.systems.api.RenderAction }
 *
 * @see fr.mcgivrer.prototype.ecsfmk.systems.api.Action
 *
 * @author Frédéric Delorme<frederic.delorme@snapgames.fr>
 *
 */
public interface System {

    String getName();

    int getPriority();


    default void preOperation() {

    }

    default void postOperation() {

    }
}

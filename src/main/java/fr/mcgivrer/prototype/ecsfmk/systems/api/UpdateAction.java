/*
 * McGivrer's Blog
 *
 * Entity Component System framework
 *
 * @copyright 2021
 */
package fr.mcgivrer.prototype.ecsfmk.systems.api;

/**
 * The interface to create new UpdateAction.
 *
 * @author Frédéric Delorme<frederic.delorme@snapgames.fr>
 * @see Action
 */
public interface UpdateAction extends Action {

    /**
     * This is where the magic run.
     *
     * @param dt elapsed time since previous call.
     */
    void update(float dt);
}

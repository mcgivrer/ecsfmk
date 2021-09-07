/*
 * McGivrer's Blog
 *
 * Entity Component System framework
 *
 * @copyright 2021
 */
package fr.mcgivrer.prototype.ecsfmk.systems.api;

/**
 * The interface to create new RenderAction.
 *
 * @author Frédéric Delorme<frederic.delorme@snapgames.fr>
 * @see Action
 */
public interface RenderAction extends Action {

    void render(float dt);
}

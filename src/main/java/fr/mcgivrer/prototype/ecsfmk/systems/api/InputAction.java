/*
 * McGivrer's Blog
 *
 * Entity Component System framework
 *
 * @copyright 2021
 */
package fr.mcgivrer.prototype.ecsfmk.systems.api;

import fr.mcgivrer.prototype.ecsfmk.io.InputHandler;

/**
 * The interface to create new InputAction.
 *
 * @author Frédéric Delorme<frederic.delorme@snapgames.fr>
 * @see Action
 */
public interface InputAction extends Action {
    public void input(InputHandler ih, float dt);
}

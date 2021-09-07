/*
 * McGivrer's Blog
 *
 * Entity Component System framework
 *
 * @copyright 2021
 */
package fr.mcgivrer.prototype.ecsfmk.systems.api;

/**
 * The interface to create new System.
 *
 * <p>
 * An Action is an interface for any action the main application will use a {@link fr.mcgivrer.prototype.ecsfmk.systems.System} for.
 * Currently, an inherit action can be:
 * - {@link InputAction}
 * - {@link UpdateAction}
 * - {@link RenderAction}
 * <p>
 * A System will implement one or more af those action.
 * <pre>
 * class MySystem implements System, InputAction, UpdateAction{
 *   public final static String SERVICE_NAME="MySystem";
 *   public String getName(){ return SERVICE_NAME;}
 *   public void update(float dt){ }
 *   public void input(InputHandler ih, float dt)
 * }
 * </pre>
 *
 * @author Frédéric Delorme<frederic.delorme@snapgames.fr>
 * @see System
 */
public interface Action {
}

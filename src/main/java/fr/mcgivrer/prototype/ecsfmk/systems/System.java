/**
 * McGivrer's Blog 
 *
 * Entity Component System framework 
 *
 * @copyright 2018
 */
package fr.mcgivrer.prototype.ecsfmk.systems;

/**
 * The interface to create new System.
 * 
 * @author Frédéric Delorme<frederic.delorme@ge.com>
 *
 */
public interface System {
	/**
	 * This is where the magic run.
	 * 
	 * @param dt
	 *            elapsed time since previous call.
	 */
	public void update(float dt);
}

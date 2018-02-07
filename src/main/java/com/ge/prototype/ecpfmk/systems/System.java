/**
 * GE DoseWatch
 * ecpfmk
 *
 * @copyright 2018
 */
package com.ge.prototype.ecpfmk.systems;

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

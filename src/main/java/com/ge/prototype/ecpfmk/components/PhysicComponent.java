/**
 * GE DoseWatch
 * ecpfmk
 *
 * @copyright 2018
 */
package com.ge.prototype.ecpfmk.components;

/**
 * Physic Component to compute all physic stuff on entities.
 * 
 * @author Frédéric Delorme<frederic.delorme@ge.com>
 *
 */
public class PhysicComponent implements Component {

	/**
	 * Build this fantastic component.
	 */
	public PhysicComponent() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ge.prototype.ecpfmk.components.Component#getName()
	 */
	@Override
	public String getName() {
		return "physic";
	}

}

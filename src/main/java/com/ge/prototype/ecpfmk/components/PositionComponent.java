/**
 * GE DoseWatch
 * ecpfmk
 *
 * @copyright 2018
 */
package com.ge.prototype.ecpfmk.components;

import com.ge.prototype.ecpfmk.math.Vector2D;

/**
 * This component intends to contain position information.
 * 
 * @author Frédéric Delorme<frederic.delorme@ge.com>
 *
 */
public class PositionComponent implements Component {

	public Vector2D position;

	/**
	 * 
	 */
	public PositionComponent() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getName() {

		return "position";
	}

}

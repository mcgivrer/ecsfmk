/**
 * McGivrer's Blog 
 *
 * Entity Component System framework 
 *
 * @copyright 2018
 */
package fr.mcgivrer.prototype.ecsfmk.ui;

import java.util.ResourceBundle;

/**
 * @author Frédéric Delorme<frederic.delorme@ge.com>
 *
 */
public class Messages {

	private static ResourceBundle messages = ResourceBundle.getBundle("messages");

	public static String get(String key) {
		assert(key!=null && !key.equals(""));
		if(Messages.messages.containsKey(key)) {
			return messages.getString(key);
		}else {
			return "["+key+"] unknown.";
		}
	}

}

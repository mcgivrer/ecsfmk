/**
 * McGivrer's Blog 
 *
 * Entity Component System framework 
 *
 * @copyright 2018
 */
package fr.mcgivrer.prototype.ecsfmk.io;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * A small key input handler to manage key events.
 * 
 * @author Frédéric Delorme<frederic.delorme@snapgames.fr>
 * 
 * @see https://gist.github.com/jake7864/3212569
 *
 */
public class InputHandler implements KeyListener {
	public boolean[] keys = new boolean[65536];
	public boolean[] prevKeys = new boolean[65536];

	/**
	 * detect pressed key.
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		prevKeys[keyCode] = keys[keyCode];
		keys[keyCode] = true;
	}

	/**
	 * detect released key.
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		int keyCode = e.getKeyCode();
		prevKeys[keyCode] = keys[keyCode];
		keys[keyCode] = false;
	}

	/**
	 * detect Typed key
	 */
	public void keyTyped(KeyEvent e) {
		int keyCode = e.getKeyCode();
		prevKeys[keyCode] = keys[keyCode];
		keys[keyCode] = true;
	}
}
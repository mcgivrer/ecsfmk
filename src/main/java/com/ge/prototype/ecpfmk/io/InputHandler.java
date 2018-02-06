/**
 * GE DoseWatch
 * ecpfmk
 *
 * @copyright 2018
 */
package com.ge.prototype.ecpfmk.io;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * A small key input handler to manage key events.
 * 
 * @author Frédéric Delorme<frederic.delorme@ge.com>
 * 
 * @see https://gist.github.com/jake7864/3212569
 *
 */
public class InputHandler implements KeyListener {
	public boolean[] keys = new boolean[65536];

	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		keys[keyCode] = true;
	}

	public void keyReleased(KeyEvent e) {
		int keyCode = e.getKeyCode();
		keys[keyCode] = false;
	}

	public void keyTyped(KeyEvent e) {
		int keyCode = e.getKeyCode();
		keys[keyCode] = true;
	}
}
/**
 * GE DoseWatch
 * ecpfmk
 *
 * @copyright 2018
 */
package com.ge.prototype.ecpfmk.ui;

import java.awt.Dimension;
import java.awt.Graphics2D;

import javax.swing.JFrame;

import com.ge.prototype.ecpfmk.io.InputHandler;

/**
 * A Window object to manage windowing stuff and link to OS.
 * 
 * @author Frédéric Delorme<frederic.delorme@ge.com>
 *
 */
public class Window {

	/**
	 * internal frame to render things.
	 */
	private JFrame frame = null;

	private Dimension dimension;
	/**
	 * the attached InputHandler.
	 */
	private InputHandler ih = null;

	private Graphics2D gScreen;

	/**
	 * Create a new Window to be displayed.
	 * 
	 * @param title
	 * @param dim
	 */
	public Window(String title, Dimension dim) {
		initializeWindow(title, dim);
	}

	/**
	 * Attach an inputHandler to the window.
	 * 
	 * @param ih
	 */
	public void addInputHandler(InputHandler ih) {
		this.ih = new InputHandler();
	}

	/**
	 * @param frame
	 */
	private void initializeWindow(String title, Dimension dim) {
		this.dimension = dim;
		frame = new JFrame(title);
		frame.setPreferredSize(dim);
		frame.setSize(dim);
		frame.setMaximumSize(dim);
		frame.setMinimumSize(dim);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		if (ih == null) {
			ih = new InputHandler();
		}
		frame.addKeyListener(ih);
		frame.setAlwaysOnTop(true);
		frame.setUndecorated(false);

		frame.pack();
		frame.setVisible(true);

		this.gScreen = (Graphics2D) frame.getGraphics();

	}

	public int getWidth() {
		return (int) dimension.width;
	}

	public int getHeight() {
		return (int) dimension.height;
	}

	public void show() {
		frame.setVisible(true);
	}

	public void hide() {
		frame.setVisible(false);
	}

	public boolean isVisible() {
		return frame.isVisible();
	}

	public Dimension getDimension() {
		return dimension;
	}

	public Graphics2D getGraphics() {
		return gScreen;
	}

	public void dispose() {
		frame.dispose();

	}

	public InputHandler getInputHandler() {
		return ih;
	}
}

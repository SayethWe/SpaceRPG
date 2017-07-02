package sineSection.util;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

public class GraphicsUtils {

	/**
	 * Gets the width of a string in a graphics context.<br>
	 * Uses the font set by {@link #java.awt.Graphics.setFont(Font f)}
	 * 
	 * @param g
	 *            The graphics context object
	 * @param str
	 *            The string to measure
	 * @return The string width
	 */
	public static int getStringWidth(Graphics g, String str) {
		FontMetrics fm = g.getFontMetrics();
		return fm.stringWidth(str);
	}

	/**
	 * Gets the height of the current font in a graphics context.
	 * 
	 * @param g
	 *            The graphics context object
	 * @return The font height
	 */
	public static int getFontHeight(Graphics g) {
		FontMetrics fm = g.getFontMetrics();
		return fm.getHeight();
	}

}

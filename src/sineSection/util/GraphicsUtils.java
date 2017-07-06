package sineSection.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.util.List;

public class GraphicsUtils {

	/**
	 * Gets the width of a string in a graphics context.<br>
	 * Uses the font set by {@link Graphics#setFont(Font)}
	 * 
	 * @param g
	 *            the graphics context object
	 * @param str
	 *            the string to measure
	 * @return the string width
	 * @see Font
	 */
	public static int getStringWidth(Graphics g, String str) {
		FontMetrics fm = g.getFontMetrics();
		return fm.stringWidth(str);
	}

	public static int getLargestWidth(Graphics g, List<String> strs) {
		int ret = 0;
		for (String str : strs) {
			if (getStringWidth(g, str) > ret) {
				ret = getStringWidth(g, str);
			}
		}
		return ret;
	}

	/**
	 * Gets the height of the current font in a graphics context.
	 * 
	 * @param g
	 *            the graphics context object
	 * @return The font height
	 */
	public static int getFontHeight(Graphics g) {
		FontMetrics fm = g.getFontMetrics();
		return fm.getHeight();
	}

	public static int getFontHeight(Font f) {
		AffineTransform affinetransform = new AffineTransform();
		FontRenderContext frc = new FontRenderContext(affinetransform, false, false);
		return (int) f.getStringBounds(" ", frc).getHeight();
	}

	public static int getStringWidth(Font f, String s) {
		AffineTransform affinetransform = new AffineTransform();
		FontRenderContext frc = new FontRenderContext(affinetransform, false, false);
		return (int) f.getStringBounds(s, frc).getWidth();
	}

	public static int getLargestWidth(Font f, List<String> strs) {
		int ret = 0;
		for (String str : strs) {
			if (getStringWidth(f, str) > ret) {
				ret = getStringWidth(f, str);
			}
		}
		return ret;
	}

	public static final int BAR_DIR_LEFT_TO_RIGHT = 1; // 00001
	public static final int BAR_DIR_RIGHT_TO_LEFT = 2; // 00010
	public static final int BAR_DIR_DOWN_TO_UP = 4; // 00100
	public static final int BAR_DIR_UP_TO_DOWN = 8; // 01000
	public static final int BAR_NO_BORDER = 16; // 10000

	/**
	 * Draw a bar that is filled depending on a float value.
	 * 
	 * @param g
	 *            the graphics object to draw with
	 * @param x
	 *            the x position of the bar
	 * @param y
	 *            the x position of the bar
	 * @param width
	 *            the width of the bar
	 * @param height
	 *            the height of the bar
	 * @param value
	 *            the amount of the bar that is filled (0.0f - 1.0f)
	 * @param flags
	 *            draw flags. use the "or" <code>( | )</code> bit operator to
	 *            combine flags.<br>
	 *            <ul>
	 *            <li>{@link #BAR_DIR_LEFT_TO_RIGHT}
	 *            <li>{@link #BAR_DIR_RIGHT_TO_LEFT}
	 *            <li>{@link #BAR_DIR_DOWN_TO_UP}
	 *            <li>{@link #BAR_DIR_UP_TO_DOWN}
	 *            <li>{@link #BAR_NO_BORDER}
	 *            </ul>
	 * @param bg
	 *            The background color
	 * @param fg
	 *            The foreground/bar color
	 * @param brdr
	 *            The border color
	 * 
	 * @author Richard Abbott
	 */
	public static void drawBar(Graphics g, int x, int y, int width, int height, float value, int flags, Color bg, Color fg, Color brdr) {
		if (value > 1.0f)
			value = 1.0f;
		if (value < 0.0f)
			value = 0.0f;
		int barDir = 0;
		if ((flags & BAR_DIR_LEFT_TO_RIGHT) == BAR_DIR_LEFT_TO_RIGHT)
			barDir = 0;
		if ((flags & BAR_DIR_RIGHT_TO_LEFT) == BAR_DIR_RIGHT_TO_LEFT)
			barDir = 1;
		if ((flags & BAR_DIR_DOWN_TO_UP) == BAR_DIR_DOWN_TO_UP)
			barDir = 2;
		if ((flags & BAR_DIR_UP_TO_DOWN) == BAR_DIR_UP_TO_DOWN)
			barDir = 3;
		boolean drawBorder = !((flags & BAR_NO_BORDER) == BAR_NO_BORDER);

		g.setColor(bg);
		g.fillRect(x, y, width, height);
		g.setColor(fg);
		int barLength;
		switch (barDir) {
		default:
		case 0:
			barLength = (int) Math.round(value * (float) width);
			g.fillRect(x, y, barLength, height);
			break;
		case 1:
			barLength = (int) Math.round(value * (float) width);
			g.fillRect(x + (width - barLength), y, barLength, height);
			break;
		case 2:
			barLength = (int) Math.round(value * (float) height);
			g.fillRect(x, y + (height - barLength), width, barLength);
			break;
		case 3:
			barLength = (int) Math.round(value * (float) height);
			g.fillRect(x, y, width, barLength);
			break;
		}

		if (drawBorder) {
			g.setColor(brdr);
			g.drawRect(x, y, width - 1, height - 1);
		}
	}
}
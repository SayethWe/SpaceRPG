package sineSection.util;

import java.awt.Color;
import java.awt.Graphics;

public class SineSection {
	
	private static dPoint[] p;
	
	public static void initialize() {
		p = new dPoint[40];
		for(double i = 0D; i < 4D; i += 0.1D) {
			p[(int)Math.round(i * 10D)] = new dPoint();
			p[(int)Math.round(i * 10D)].x = Math.round(2D * Math.sin(4D * Math.PI * (i + 1D)) + 1D);
			p[(int)Math.round(i * 10D)].y = Math.round(Math.sin(6D * Math.PI * (i + 1D)) + 1D);
		}
	}
	
	public static void draw(Graphics g, int x, int y, double scale, Color col) {
		g.setColor(col);
		for(int i = 0; i < p.length; i++) {
			g.drawLine(x + (int)(p[i].x * scale), y + (int)(p[i].y * scale), x +(int)(p[i].x * scale), y + (int)(p[i].y * scale));
		}
	}
}

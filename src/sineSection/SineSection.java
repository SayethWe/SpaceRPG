package sineSection;

import java.awt.Color;
import java.awt.Graphics;

import sineSection.util.dPoint;

public class SineSection {
	
	private static dPoint[] p;
	
	private static int ACCURACY = 81;
	
	public static void initialize() {
		p = new dPoint[ACCURACY];
		for(double i = 0D; i < 1D; i += (1D / ACCURACY)) {
			p[(int)Math.round(i * (double) ACCURACY)] = new dPoint();
			p[(int)Math.round(i * (double) ACCURACY)].x = 5D * Math.sin(4D * Math.PI * (i + 1D)) + 5D;
			p[(int)Math.round(i * (double) ACCURACY)].y = 2.5D * Math.sin(6D * Math.PI * (i + 1D)) - 2.5D;
		}
	}
	
	public static void draw(Graphics g, int x, int y, double scale, Color col) {
		g.setColor(col);
		for(int i = 0; i < p.length; i++) {
			if(i == 0) 
				g.drawLine(x + (int)(p[i].x * scale), y + (int)(-p[i].y * scale), x +(int)(p[p.length - 1].x * scale), y + (int)(-p[p.length - 1].y * scale));
			else
				g.drawLine(x + (int)(p[i].x * scale), y + (int)(-p[i].y * scale), x +(int)(p[i - 1].x * scale), y + (int)(-p[i - 1].y * scale));
		}
	}
}

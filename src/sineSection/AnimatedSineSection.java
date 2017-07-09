package sineSection;

import java.awt.Color;
import java.awt.Graphics;

public class AnimatedSineSection implements Runnable {
	
	private final int WIDTH = 100;
	private final int HEIGHT = 50;

	private Thread thread;
	private boolean running = false;
	private final int size;
	private final int trailLength;
	private final long delay;
	private final double scale;
	private final Color col;
	private double time = 0D;

	private double[] x;
	private double[] y;

	public AnimatedSineSection(int size, double scale, int trailLength, Color col, long delay) {
		if(scale < 0) scale = 1;
		if(size < 1) size = 1;
		if(trailLength < 1) trailLength = 1;
		if(delay < 1) delay = 1;
		this.size = size;
		this.trailLength = trailLength;
		this.delay = delay;
		this.col = col;
		this.scale = scale;
		x = new double[trailLength];
		y = new double[trailLength];
		thread = new Thread(this);
	}

	public synchronized void start() {
		if (running)
			return;
		running = true;
		thread.start();
	}

	public synchronized void stop() {
		if (!running)
			return;
		running = false;
		time = 0D;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		while (running) {
			time += 0.001D;
			for (int i = 0; i < trailLength; i++) {
				double timeOffset = -(i * 0.001f);
				x[i] = scale * (50D * Math.sin(4D * Math.PI * ((time - timeOffset) + 1D)) + 50D);
				y[i] = -(scale * (25D * Math.sin(6D * Math.PI * ((time - timeOffset) + 1D)) - 25D));
			}
			try {
				Thread.sleep(delay);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public int getWidth() {
		return (int) (Math.round(WIDTH * scale) + size);
	}
	public int getHeight() {
		return (int) (Math.round(HEIGHT * scale) + size);
	}

	public void draw(int x, int y, Graphics g) {
		float alpha = 255f;
		for (int i = 0; i < trailLength; i++) {
			if (this.x[i] != 0D || this.y[i] != 0D) {
				alpha = i * (255f / (float) (trailLength));
				g.setColor(new Color(col.getRed(), col.getGreen(), col.getBlue(), (int) alpha));
				g.fillOval((int) (x + this.x[i] - size / 2D), (int) (y + this.y[i] - size / 2D), size, size);
			}
		}

	}

}

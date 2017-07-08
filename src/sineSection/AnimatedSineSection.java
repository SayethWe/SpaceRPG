package sineSection;

import java.awt.Color;
import java.awt.Graphics;

public class AnimatedSineSection implements Runnable {

	private Thread thread;
	private boolean running = false;
	private final int size;
	private final int trailLength;
	private final long delay;
	private final Color col;
	private double time = 0D;

	private double[] x;
	private double[] y;

	public AnimatedSineSection(int size, int trailLength, Color col, long delay) {
		this.size = size;
		this.trailLength = trailLength;
		this.delay = delay;
		this.col = col;
		thread = new Thread(this);
		x = new double[trailLength];
		y = new double[trailLength];
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
				x[i] = 50D * Math.sin(4D * Math.PI * ((time - timeOffset) + 1D)) + 50D;
				y[i] = 25D * Math.sin(6D * Math.PI * ((time - timeOffset) + 1D)) - 25D;
			}
			try {
				Thread.sleep(delay);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
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

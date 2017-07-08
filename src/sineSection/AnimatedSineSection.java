package sineSection;

import java.awt.Color;
import java.awt.Graphics;

public class AnimatedSineSection implements Runnable {

	private Thread thread;
	private boolean running = false;
	private final int size;
	private final long delay;
	private final Color col;
	private double time = 0D;

	private static final int TRAIL_LENGTH = 50;

	private int lastIndex = TRAIL_LENGTH - 1;
	private double[] x = new double[TRAIL_LENGTH];
	private double[] y = new double[TRAIL_LENGTH];

	public AnimatedSineSection(int size, Color col, long delay) {
		this.size = size;
		this.delay = delay;
		this.col = col;
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
			time += 0.001;
			if (lastIndex < 0) {
				lastIndex = TRAIL_LENGTH - 1;
			}
			x[lastIndex] = 50D * Math.sin(4D * Math.PI * (time + 1D)) + 50D;
			y[lastIndex] = 25D * Math.sin(6D * Math.PI * (time + 1D)) - 25D;
			lastIndex--;
			try {
				Thread.sleep(delay);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void draw(int x, int y, Graphics g) {
		float alpha = 1.0f;
		for (int i = 0; i < TRAIL_LENGTH; i++) {
			if (this.x[i] != 0D || this.y[i] != 0D) {
				alpha -= (1.0f / (float) TRAIL_LENGTH);
				g.setColor(new Color(col.getRed(), col.getGreen(), col.getBlue(), (int)(255F * alpha)));
				g.fillOval((int) (x + this.x[i] - size / 2D), (int) (y + this.y[i] - size / 2D), size, size);
			}
		}

	}

}

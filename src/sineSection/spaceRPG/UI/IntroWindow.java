package sineSection.spaceRPG.UI;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JWindow;

import sineSection.AnimatedSineSection;
import sineSection.spaceRPG.GameInfo;
import sineSection.spaceRPG.UI.panel.CommandBar;
import sineSection.util.GraphicsUtils;

public class IntroWindow extends JWindow {
	private static final long serialVersionUID = 6112814198320261512L;

	private static final Font INTO_WINDOW_TITLE_FONT = CommandBar.COMMAND_BAR_FONT.deriveFont(32f);

	private static final String SINESECTION = "(c) SineSection Software 2017";
	private static final Font INTO_WINDOW_SINESECTION_FONT = CommandBar.COMMAND_BAR_FONT.deriveFont(18f).deriveFont(Font.PLAIN);

	private static final Color INTO_WINDOW_TEXT_COLOR = new Color(0, 255, 0);
	private static final Color INTO_WINDOW_LOGO_COLOR = new Color(0, 255, 0);
	private static final Color INTO_WINDOW_BG_COLOR = new Color(0, 0, 0);
	private static final Color INTO_WINDOW_FG_COLOR = new Color(0, 255, 0);

	private static final int WIDTH = 220, HEIGHT = 150;
	private static final long SHOW_LENGTH = 6 * 1000;
	private boolean showing = false;
//	private long startTime = 0;
	private AnimatedSineSection logo = new AnimatedSineSection(10, 1D, 150, INTO_WINDOW_LOGO_COLOR, 4);
	private IntroWindowPanel panel;

	public IntroWindow() {
		setSize(WIDTH, HEIGHT);
		setAlwaysOnTop(true);
		setLocationRelativeTo(null);
		panel = new IntroWindowPanel();
		add(panel);
		setVisible(true);
	}

	public void start() {
		if (showing)
			return;
		showing = true;
		setVisible(true);
		panel.start();
		logo.start();
//		startTime = System.currentTimeMillis();
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				showing = false;
				logo.stop();
				panel.stop();
				setVisible(false);
				dispose();
			}
		}, SHOW_LENGTH);
//		while (showing) {
//			try {
//				Thread.sleep(1);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//			if (System.currentTimeMillis() - startTime >= SHOW_LENGTH)
//				showing = false;
//		}
	}
	
	public boolean ended() {
		return !showing;
	}

	private class IntroWindowPanel extends Canvas implements Runnable {
		private static final long serialVersionUID = 7774169424692288673L;
		
		private Thread thread;
		private boolean running = false;

		public void draw(Graphics g) {
			g.setColor(INTO_WINDOW_BG_COLOR);
			g.fillRect(0, 0, getWidth(), getHeight());
			g.setColor(INTO_WINDOW_FG_COLOR);
			g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);

			g.setColor(INTO_WINDOW_TEXT_COLOR);
			g.setFont(INTO_WINDOW_TITLE_FONT);
			g.drawString(GameInfo.TITLE, getWidth() / 2 - GraphicsUtils.getStringWidth(g, GameInfo.TITLE) / 2, 30);
			g.setFont(INTO_WINDOW_SINESECTION_FONT);
			g.drawString(SINESECTION, getWidth() / 2 - GraphicsUtils.getStringWidth(g, SINESECTION) / 2, getHeight() - 10);
			logo.draw(getWidth() / 2 - logo.getWidth() / 2, getHeight() / 2 - logo.getHeight() / 2 + 10, g);
		}

		public synchronized void start() {
			if(running) return;
			running = true;
			thread = new Thread(this);
			createBufferStrategy(2);
			thread.start();
		}

		public synchronized void stop() {
			if(!running) return;
			running = false;
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			getBufferStrategy().dispose();
		}
		
		public void run() {
			while(running) {
				Graphics2D g = (Graphics2D) getBufferStrategy().getDrawGraphics();
				g.clearRect(0, 0, WIDTH, HEIGHT);
				draw(g);
				g.dispose();
				getBufferStrategy().show();
			}
		}

	}

}

package sineSection.spaceRPG.UI.panel;

import java.awt.Color;
import java.awt.Font;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.JTextArea;

public class GameScreen extends JTextArea{
	private static final long serialVersionUID = -3410321645542492470L;
	
	public static final int DEFAULT_FONT_SIZE = 6;
	private static final float[] FONT_SIZES = new float[] { 8f, 9f, 10f, 11f, 12f, 14f, 16f, 18f, 20f, 22f, 24f };

	private static final int TYPE_DELAY = 50;
	private static final TimeUnit DELAY_UNIT = TimeUnit.MILLISECONDS;
	
	private int fontSize = DEFAULT_FONT_SIZE;

	public static final Font GAME_SCREEN_FONT = new Font("VT323", Font.PLAIN, (int) FONT_SIZES[DEFAULT_FONT_SIZE]);
	
	private int placeInLine = 0;

	public GameScreen() {
		setAlignmentX(LEFT_ALIGNMENT);
		setEditable(false);
		setLineWrap(true);
		setWrapStyleWord(true);
		setFont(GAME_SCREEN_FONT);
		setBackground(Color.BLACK);
		setForeground(Color.GREEN);
		getCaret().setVisible(true);
		setCaretColor(Color.YELLOW);
	}
	
	public void writeScroll(String text) {
		for(String s : text.split("\n")) {
			final ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
			exec.scheduleAtFixedRate(new Runnable() {
				@Override
				public void run() {
					if(appendNextChar(s)) {
						exec.shutdown();
					}
				}
			}, TYPE_DELAY, TYPE_DELAY, DELAY_UNIT);
			while(!exec.isShutdown());
			append("\n");
			//line return sound
		}
		append("\n");
	}
	
	public void write(String s) {
		append(s);
		updateCaret();
	}
	
	/**
	 * 
	 * @param s
	 * @return
	 */
	private boolean appendNextChar(String s) {
		boolean result = (placeInLine == s.length());
		if(!result) {
			append(s.charAt(placeInLine));
			placeInLine++;
			updateCaret();
			//play a typing sound
		} else {
			placeInLine = 0;
		}
		return result;
	}
	
	private void updateCaret() {
		setCaretPosition(getDocument().getLength());
	}

	/**
	 * Sets the <b>INDEX</b> of the {@link #FONT_SIZES} to use.
	 * 
	 * @param fontSizeIndex
	 */
	public void setFontSize(int fontSizeIndex) {
		if (fontSizeIndex > FONT_SIZES.length)
			fontSizeIndex = FONT_SIZES.length;
		if (fontSizeIndex < 0)
			fontSizeIndex = 0;
		fontSize = fontSizeIndex;
		if (fontSize == DEFAULT_FONT_SIZE) {
			setFont(GAME_SCREEN_FONT);
		} else {
			setFont(GAME_SCREEN_FONT.deriveFont(FONT_SIZES[fontSize]));
		}
		append("Font size set to " + FONT_SIZES[fontSize] + "!\n(" + (fontSize + 1) + "/" + FONT_SIZES.length + ")\n");
	}
	
	public void increaseFontSize() {
		if (fontSize < FONT_SIZES.length - 1) {
			fontSize++;
			if (fontSize == DEFAULT_FONT_SIZE) {
				setFont(GAME_SCREEN_FONT);
			} else {
				setFont(GAME_SCREEN_FONT.deriveFont(FONT_SIZES[fontSize]));
			}
			repaint();
			append("Font size increased!\n(" + (fontSize + 1) + "/" + FONT_SIZES.length + ")\n");
		} else {
			append("Can't increase font size any more!\n");
		}
	}

	public void decreaseFontSize() {
		if (fontSize > 0) {
			fontSize--;
			if (fontSize == DEFAULT_FONT_SIZE) {
				setFont(GAME_SCREEN_FONT);
			} else {
				setFont(GAME_SCREEN_FONT.deriveFont(FONT_SIZES[fontSize]));
			}
			append("Font size decreased!\n(" + (fontSize + 1) + "/" + FONT_SIZES.length + ")\n");
		} else {
			append("Can't decrease font size any more!\n");
		}
	}
	
	public void append(char c) {
		append(String.valueOf(c));
	}

}

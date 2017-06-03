package sineSection.spaceRPG.UI.panel;

import java.awt.GridBagConstraints;

import javax.swing.JLabel;
import javax.swing.JTextArea;

public class InfoPanel extends AbstractPanel {
	private static final long serialVersionUID = -2435708821319951292L;
	private final String title;
	private JTextArea display;
	
	public InfoPanel(String title) {
		display = new JTextArea();
		constraints.gridy = 1;
		add(display,constraints);
		
		
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridy = 0;
		constraints.weighty = 0;
		add(new JLabel(this.title = title),constraints);
	}
	
	public String getTitle() {
		return title;
	}
	
	public void reFill() {
		StringBuilder fill = new StringBuilder("");
		display.setText(fill.toString());
	}
	
}
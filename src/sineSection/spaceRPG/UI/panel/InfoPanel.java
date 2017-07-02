package sineSection.spaceRPG.UI.panel;

import java.awt.GridBagConstraints;
import java.util.Set;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

public class InfoPanel<E> extends AbstractPanel {
	private static final long serialVersionUID = -2435708821319951292L;
	private final String title;
	private JTextArea display;

	public InfoPanel(String title) {
		display = new JTextArea();
		JScrollPane displayPane = new JScrollPane(display);
		constraints.gridy = 1;
		add(displayPane, constraints);

		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridy = 0;
		constraints.weighty = 0;
		add(new JLabel(this.title = title, SwingConstants.CENTER), constraints);
	}

	public String getTitle() {
		return title;
	}

	public void reFill(Set<E> in) {
		StringBuilder fill = new StringBuilder("");
		in.forEach((item) -> fill.append(item.toString()));
		display.setText(fill.toString());
	}

}
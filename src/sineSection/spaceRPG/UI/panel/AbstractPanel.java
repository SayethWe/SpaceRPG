package sineSection.spaceRPG.UI.panel;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

public abstract class AbstractPanel extends JPanel {
	private static final long serialVersionUID = 982468463010626978L;
	protected GridBagConstraints constraints;

	public AbstractPanel() {
		super(new GridBagLayout());
		constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
		constraints.weightx = 0.5;
		constraints.weighty = 0.5;
	}
}

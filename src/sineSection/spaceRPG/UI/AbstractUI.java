 package sineSection.spaceRPG.UI;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;

public abstract class AbstractUI extends JFrame{
	private static final long serialVersionUID = -1386522460580786346L;
	protected GridBagConstraints constraints;
	
	protected AbstractUI() {
		setLayout(new GridBagLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		constraints = new GridBagConstraints();
	}
}

 package sineSection.spaceRPG.UI;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class AbstractUI extends JFrame{
	private static final long serialVersionUID = -1386522460580786346L;
	protected GridBagConstraints constraints;
	
	protected AbstractUI() {
		setLayout(new GridBagLayout());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		constraints = new GridBagConstraints();
		constraints.weightx = 0.5;
		constraints.weighty = 0.5;
	}
	
	public AbstractUI(JPanel content) {
		this();
		setContentPane(content);
	}
	
	public void display() {
		setSize(getPreferredSize());
		setVisible(true);
	}
}

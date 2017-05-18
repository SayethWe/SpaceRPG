package sineSection.spaceRPG.UI;

import javax.swing.JButton;

public class GameUI extends AbstractUI {
	private static final long serialVersionUID = 8764045071574261230L;
	JButton button;
	
	public GameUI() {
		button = new JButton("hi");
		button.addActionListener((e) -> sayHi());
		add(button);
		setVisible(true);
	}
	
	public void sayHi() {
		System.out.println("hi");
	}
}

package sineSection.spaceRPG.UI;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

import sineSection.util.Utils;

public class AbstractUI extends JFrame {
	private static final long serialVersionUID = -1386522460580786346L;
	protected GridBagConstraints constraints;

	protected AbstractUI() {
		updateLookAndFeel();
		setLayout(new GridBagLayout());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		constraints = new GridBagConstraints();
		constraints.weightx = 0.5;
		constraints.weighty = 0.5;
		constraints.fill = GridBagConstraints.BOTH;
		setWindowsIcon();
	}

	private void updateLookAndFeel() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			try {
				UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
	private void setWindowsIcon() {
		int[] iconRes = new int[] {16,32,64};
		ArrayList<BufferedImage> icons = new ArrayList<>();
		for(int i = 0; i < iconRes.length; i++) {
			icons.add(Utils.loadImageResource("/image/icon/icon" + iconRes[i] + ".png"));
		}
		setIconImages(icons);
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

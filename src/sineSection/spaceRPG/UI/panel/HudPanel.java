package sineSection.spaceRPG.UI.panel;

import java.awt.GridBagConstraints;

import sineSection.spaceRPG.character.Stat;
import sineSection.spaceRPG.world.item.Item;

/**
 * the Hud. Displays your inventory, stats, health, etc.
 * Maybe update w/ a command
 * @author geekman9097
 *
 */
public class HudPanel extends AbstractPanel {
	private static final long serialVersionUID = -2435708821319951292L;
	private InfoPanel<Item> inventory;
	private InfoPanel<Stat> stats;
	
	public HudPanel() {
		inventory = new InfoPanel<>("Inventory");
		constraints.fill = GridBagConstraints.BOTH;
		add(inventory, constraints);
		
		stats = new InfoPanel<>("Player Stats");
		constraints.gridy = 1;
		add(stats, constraints);
	} 

}

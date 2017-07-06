package sineSection.spaceRPG.world;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import sineSection.spaceRPG.SpaceRPG;
import sineSection.spaceRPG.world.item.Item;
import sineSection.spaceRPG.world.item.ItemReference;
import sineSection.util.LogWriter;

public class ItemGenerator {
	protected List<ItemReference> validTypes = new ArrayList<>();
	protected Random RNJesus;

	/**
	 * Creates a new ItemGenerator with a system-determined seed
	 * 
	 * @see java.util.Random
	 */
	public ItemGenerator() {
		RNJesus = new Random(SpaceRPG.getNewSeed());
	}

	public void addType(ItemReference type) {
		validTypes.add(type);
	}

	protected ItemReference generateType() {
		ItemReference result;
		int index = RNJesus.nextInt(validTypes.size());
		result = validTypes.get(index);
		LogWriter.print("Crafting a item: " + result);
		return result;
	}

	public Item generate() {
		Item result;
		result = generateType().createItem();
		return result;
	}

}

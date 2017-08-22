package sineSection.spaceRPG.world.item;

import java.util.ArrayList;
import java.util.List;

public class Inventory {
	
	private int size;
	private List<Item> items;
	private Container container;
	
	public Inventory(Container container, int size) {
		if(size < 0) size = 0;
		this.size = size;
		this.container = container;
		items = new ArrayList<>();
	}
	
	public boolean addItem(Item i) {
		if(!items.contains(i)) return false;
		if(items.size() + 1 > size) {
			return false;
		}
		container.onItemAdded(i);
		return items.add(i);
	}
	
	public boolean removeItem(Item i) {
		if(!items.contains(i)) return false;
		container.onItemRemoved(i);
		return items.remove(i);
	}
	
	public boolean hasItemWithName(String itemName) {
		return numItemsWithName(itemName) > 0;
	}
	
	public int numItemsWithName(String itemName) {
		int amount = 0;
		for(Item i : items) {
			if(i.getName().equalsIgnoreCase(itemName))
				amount++;
		}
		return amount;
	}
	
	public List<Item> getItemsWithName(String itemName) {
		List<Item> ret = new ArrayList<>();
		for(Item i : items) {
			if(i.getName().equalsIgnoreCase(itemName))
				ret.add(i);
		}
		return ret;
	}
	
	/**
	 * 
	 * @param size
	 * @return A list of items that did not fit into the newly sized inventory.
	 */
	public List<Item> setSize(int size) {
		if(size < 0) size = 0;
		this.size = size;
		List<Item> pushedOut = new ArrayList<>();
		while(items.size() > this.size) {
			Item i = items.get(items.size() - 1);
			items.remove(i);
			pushedOut.add(i);
		}
		return pushedOut;
	}
	
	public List<Item> getItems() {
		return items;
	}

	public int getSize() {
		return size;
	}

	public boolean hasItem(Item item) {
		return items.contains(item);
	}

}

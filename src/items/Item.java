package items;

import java.util.ArrayList;
import java.util.List;

import player.Player;
import ui.UI;
import interfaces.Inspectable;
import interfaces.ItemContainer;
import interfaces.Useable;

public abstract class Item implements Inspectable, Useable, ItemContainer {
	protected String name;
	protected ItemContainer container;
	protected List<Item> items;

	public Item(String name) {
		this.name = name;
		container = null;
		items = new ArrayList<>();
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void use(Item item) {
		if (!(item.container instanceof Player)) {
			UI.write("You should own the item you want to use!");
		} else if (item instanceof Magnifier) {
			boolean nothingToSee = true;
			for (Item content : items) {
				if (content instanceof Fingerprint) {
					UI.write("There is a fingerprint on it.");
					nothingToSee = false;
				}
			}
			if (nothingToSee) {
				UI.write("There is nothing to see.");
			}
		} else {
			UI.write("This won't work.");
		}
	}

	public void insertInto(ItemContainer container) {
		if (this.container != null) {
			this.container.removeItem(this);
		}
		container.insertItem(this);
		this.container = container;
	}

	@Override
	public void insertItem(Item item) {
		if (item instanceof Fingerprint) {
			items.add(item);
			UI.write(item.getName() + " is now in " + name + ".");
		} else {
			UI.write("This won't work.");
		}
	}

	@Override
	public void removeItem(Item item) {
		items.remove(item);
	}

	@Override
	public Item[] getItems() {
		return (Item[]) items.toArray(new Item[0]);
	}

}

package items;

import interfaces.Inspectable;
import interfaces.ItemContainer;
import interfaces.Useable;

public abstract class Item implements Inspectable, Useable {
	protected String name;
	protected ItemContainer container;

	public Item(String name) {
		this.name = name;
		container = null;
	}

	@Override
	public String getName() {
		return name;
	}

	public void insertInto(ItemContainer container) {
		if (this.container != null) {
			this.container.removeItem(this);
		}
		container.insertItem(this);
		this.container = container;
	}

}

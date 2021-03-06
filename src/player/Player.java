package player;

import java.util.ArrayList;
import java.util.List;

import interfaces.Inspectable;
import interfaces.ItemContainer;
import items.Item;
import places.Place;
import ui.UI;

public class Player implements ItemContainer, Inspectable {
	private Place place;
	private List<Item> items;

	public Player() {
		items = new ArrayList<>();
	}

	public void goTo(Place place) {
		this.place = place;
	}

	public Place getPlace() {
		return place;
	}

	public boolean hasItem(Item item) {
		for (Item i : items) {
			if (i == item)
				return true;
		}
		return false;
	}

	@Override
	public void insertItem(Item item) {
		items.add(item);
		UI.write(item.getName() + " is now in your bag");
	}

	@Override
	public void removeItem(Item item) {
		items.remove(item);
	}

	@Override
	public String getName() {
		return "me";
	}

	@Override
	public void inspect() {
		UI.write("You have the following items:");
		for (Item i : items) {
			UI.write(i.getName());
		}
	}

	@Override
	public Item[] getItems() {
		return (Item[]) items.toArray(new Item[0]);
	}
}

package player;

import java.util.ArrayList;
import java.util.List;

import interfaces.ItemContainer;
import items.Item;
import places.Place;

public class Player implements ItemContainer {
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

	@Override
	public void insertItem(Item item) {
		items.add(item);
	}

	@Override
	public void removeItem(Item item) {
		items.remove(item);
	}
}

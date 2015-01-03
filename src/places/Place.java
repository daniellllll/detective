package places;

import java.util.ArrayList;
import java.util.List;

import main.Environment;
import persons.Person;
import time.Time;
import ui.UI;
import interfaces.Inspectable;
import interfaces.ItemContainer;
import interfaces.Useable;
import items.Item;

public abstract class Place implements Inspectable, ItemContainer {

	protected String name;
	protected List<Item> items;
	protected List<Place> reachablePlaces;

	public Place(String name) {
		this.name = name;
		items = new ArrayList<>();
		reachablePlaces = new ArrayList<>();
	}

	public Inspectable[] getInspectables() {
		Person persons[] = getPersons();
		Inspectable insps[] = new Inspectable[persons.length + items.size()
				+ reachablePlaces.size()];
		int i = 0;
		for (Person p : persons) {
			insps[i++] = (Inspectable) p;
		}
		for (Item item : items) {
			insps[i++] = (Inspectable) item;
		}
		for (Place place : reachablePlaces) {
			insps[i++] = place;
		}
		return insps;
	}

	public Useable[] getUseables() {
		Useable useables[] = new Useable[items.size()];
		int i = 0;
		for (Item item : items) {
			useables[i++] = (Useable) item;
		}
		return useables;
	}

	public Person[] getPersons() {
		List<Person> persons = new ArrayList<Person>();
		for (Person p : Environment.getAllPersons()) {
			if (p.getCalendar().get(Time.now()).getPlace() == this) {
				persons.add(p);
			}
		}
		Person p[] = new Person[persons.size()];
		int i = 0;
		for (Person person : persons) {
			p[i++] = person;
		}
		return p;
	}

	public Place[] getReachablePlaces() {
		Place places[] = new Place[reachablePlaces.size()];
		int i = 0;
		for (Place p : reachablePlaces) {
			places[i++] = p;
		}
		return places;
	}

	public void addReachablePlace(Place place) {
		reachablePlaces.add(place);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void inspect() {
		UI.write("The following people are in this room:");
		for (Person p : getPersons()) {
			UI.write(p.getName());
		}
		UI.write("The following Items are in this room:");
		for (Item i : items) {
			UI.write(i.getName());
		}
		UI.write("The following places are reachable:");
		for (Place p : reachablePlaces) {
			UI.write(p.getName());
		}
	}

	@Override
	public void insertItem(Item item) {
		items.add(item);
		UI.write(item.getName() + " is now in " + name + ".");
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

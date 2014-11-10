package places;

import java.util.ArrayList;
import java.util.List;

import persons.Person;
import ui.UI;
import interfaces.Inspectable;
import interfaces.ItemContainer;
import interfaces.Useable;
import items.Item;

public abstract class Place implements Inspectable, ItemContainer {

	protected String name;
	protected List<Person> persons;
	protected List<Item> items;
	protected List<Place> reachablePlaces;

	public Place(String name) {
		this.name = name;
		persons = new ArrayList<>();
		items = new ArrayList<>();
		reachablePlaces = new ArrayList<>();
	}

	public void addPerson(Person person) {
		persons.add(person);
	}

	public Inspectable[] getInspectables() {
		Inspectable insps[] = new Inspectable[persons.size() + items.size()];
		int i = 0;
		for (Person p : persons) {
			insps[i++] = (Inspectable) p;
		}
		for (Item item : items) {
			insps[i++] = (Inspectable) item;
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
		Person pers[] = new Person[persons.size()];
		int i = 0;
		for (Person p : persons) {
			pers[i++] = p;
		}
		return pers;
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
		for (Person p : persons) {
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

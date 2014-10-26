package places;

import java.util.ArrayList;
import java.util.List;

import persons.Person;
import ui.UI;
import interfaces.Inspectable;
import interfaces.ItemContainer;
import interfaces.Useable;
import items.Item;

public class Place implements Inspectable, ItemContainer {

	private String name;
	private List<Person> persons;
	private List<Item> items;

	public Place(String name) {
		this.name = name;
		persons = new ArrayList<>();
		items = new ArrayList<>();
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
	
	public Useable[] getUseables(){
		Useable useables[] = new Useable[items.size()];
		int i=0;
		for (Item item : items) {
			useables[i++] = (Useable) item;
		}
		return useables;
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

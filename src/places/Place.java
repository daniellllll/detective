package places;

import java.util.ArrayList;
import java.util.List;

import persons.Person;
import ui.UI;
import interfaces.Inspectable;

public class Place implements Inspectable {

	private String name;
	private List<Person> persons;

	public Place(String name) {
		this.name = name;
		persons = new ArrayList<>();
	}
	
	public void addPerson(Person person){
		persons.add(person);
	}
	
	public Inspectable[] getInspectables(){
		Inspectable insps[] = new Inspectable[persons.size()];
		int i=0;
		for(Person p : persons){
			insps[i++]=(Inspectable)p;
		}
		return insps;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void inspect() {
		UI.write("Following people are in this room:");
		for(Person p : persons){
			UI.write(p.getName());
		}
	}

}

package places;

import persons.Person;

public class Cabin extends Place implements Residence {
	private Person residents[];

	public Cabin(String name) {
		super(name);
	}

	@Override
	public Person[] getResidents() {
		return residents;
	}

	@Override
	public void setResidents(Person[] persons) {
		residents = persons;
	}

}
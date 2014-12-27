package places.interfaces;

import persons.Person;

public interface Residence {

	public Person[] getResidents();

	public void addResident(Person resident);
}

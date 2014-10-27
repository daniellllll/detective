package calendar;

import persons.Person;
import places.Place;

public class Event {	
	private Place place;
	private Person persons[];
	
	public Event(Place place, Person persons[]){	
		this.place = place;
		this.persons = persons;
	}
}
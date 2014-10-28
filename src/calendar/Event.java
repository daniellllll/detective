package calendar;

import persons.Person;
import places.Place;

public class Event {
	private enum Activity {
		WORKING
	}

	private Place place;
	private Person persons[];
	private Activity activity;

	public Event(Activity activity, Place place, Person persons[]) {
		this.activity = activity;
		this.place = place;
		this.persons = persons;
	}

	public Place getPlace() {
		return place;
	}

	public Person[] getPersons() {
		return persons;
	}

	public Activity getActivity() {
		return activity;
	}
	
}
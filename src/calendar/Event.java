package calendar;

import persons.Person;
import places.Place;

public class Event {
	public enum Activity {
		BUYING_TICKET, WORKING, SLEEPING, BREAKFAST, LUNCH, DINNER, READING_A_BOOK, SHOPPING, BATH, CHOP_WOOD, SEWING, FREETIME_ACTIVITY, WALK, RELAX, MEET_AFFAIR
	}

	private Place place;
	private Activity activity;
	private Person person;

	public Event(Activity activity, Place place) {
		this.activity = activity;
		this.place = place;
	}

	public Event(Activity activity, Place place, Person person) {
		this.activity = activity;
		this.place = place;
		this.person = person;
	}

	public Person getPerson() {
		return person;
	}

	public Place getPlace() {
		return place;
	}

	public Activity getActivity() {
		return activity;
	}

	@Override
	public String toString() {
		switch (activity) {
		case WORKING:
			return "I was working at the " + place.getName();
		case SLEEPING:
			return "I was sleeping at the " + place.getName();
		case BREAKFAST:
			return "I was having breakfast at the " + place.getName();
		case LUNCH:
			return "I was having lunch at the " + place.getName();
		case READING_A_BOOK:
			return "I was reading a book at the " + place.getName();
		case DINNER:
			return "I was having dinner at the " + place.getName();
		case SHOPPING:
			return "I was shopping at the " + place.getName();
		case BATH:
			return "I was having a bath at the " + place.getName();
		case CHOP_WOOD:
			return "I was chopping wood at the " + place.getName();
		case SEWING:
			return "I was sewing some clothes at the " + place.getName();
		case FREETIME_ACTIVITY:
			return "I was visiting the " + place.getName()
					+ ". Oh, it was so much fun!";
		case WALK:
			return "I was just walking at the " + place.getName();
		case RELAX:
			return "I needed a rest, so I was relaxing at the "
					+ place.getName() + ". I felt much better after that.";
		case MEET_AFFAIR:
			return "I met my affair " + person.getName() + " at "
					+ place.getName();
		case BUYING_TICKET:
			return "I bought a ticket at " + place.getName() + ".";
		}
		return null;
	}
}
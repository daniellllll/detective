package calendar;

import places.Place;

public class Event {
	public enum Activity {
		WORKING, SLEEPING, BREAKFAST, LUNCH, DINNER, READING_A_BOOK, SHOPPING
	}

	private Place place;
	private Activity activity;

	public Event(Activity activity, Place place) {
		this.activity = activity;
		this.place = place;
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
			return "I was working at " + place.getName();
		case SLEEPING:
			return "I was sleeping at " + place.getName();
		case BREAKFAST:
			return "I was having breakfast at " + place.getName();
		case LUNCH:
			return "I was having lunch at " + place.getName();
		case READING_A_BOOK:
			return "I was reading a book at " + place.getName();
		case DINNER:
			return "I was having dinner at " + place.getName();
		case SHOPPING:
			return "I was shopping at " + place.getName();
		}
		return null;
	}
}
package calendar;

import places.Place;

public class Event {
	public enum Activity {
		WORKING, SLEEPING, BREAKFAST, LUNCH, READING_A_BOOK
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
			return "I had breakfast at " + place.getName();
		case LUNCH:
			return "I had lunch at " + place.getName();
		case READING_A_BOOK:
			return "I was reading a book at " + place.getName();
		}
		return null;
	}
}
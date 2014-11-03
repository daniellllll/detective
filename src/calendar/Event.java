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
		return activity.toString() + " @ " + place.getName();
	}
}
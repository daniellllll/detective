package places;

import calendar.Event;
import calendar.Event.Activity;
import persons.Person;
import time.Time;
import time.Timespan;

public abstract class Enterprise extends Place {

	private Time startTime;
	private Time endTime;
	private Time lastWorkerTime;
	private Timespan openingTime;

	public Enterprise(String name, Timespan openingTime) {
		super(name);
		startTime = openingTime.getStartTime();
		endTime = openingTime.getEndTime();
		this.openingTime = openingTime;
		lastWorkerTime = startTime;
	}

	public void addPerson(Person person) {
		person.setWorkplace(this);
		Time start = lastWorkerTime;
		Time end = new Time(lastWorkerTime.toSeconds() + 60 * 60 * 8);
		if (end.toSeconds() > endTime.toSeconds()) {
			long diff = end.toSeconds() - endTime.toSeconds();
			start = new Time(start.toSeconds() - diff);
			if (start.toSeconds() < startTime.toSeconds()) {
				start = startTime;
			}
			end = endTime;
		}
		person.getCalendar().addDailyEvent(new Event(Activity.WORKING, this),
				start, end);
		lastWorkerTime = end;
		if (lastWorkerTime.toSeconds() >= endTime.toSeconds()) {
			lastWorkerTime = startTime;
		}
	}

	public Timespan getOpeningTime() {
		return openingTime;
	}

}

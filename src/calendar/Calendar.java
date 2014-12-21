package calendar;

import java.util.ArrayList;
import java.util.List;

import time.Time;
import time.Time.Weekday;

public class Calendar {

	private class Entry {
		public Event event;
		public Time from, to;
		public Weekday weekday = null;

		public Entry(Event event, Time from, Time to) {
			this.event = event;
			this.from = from;
			this.to = to;
		}

		public Entry(Event event, Time from, Time to, Weekday weekday) {
			this.event = event;
			this.from = from;
			this.to = to;
			this.weekday = weekday;
		}
	}

	private List<Entry> events;
	private List<Entry> dailyEvents;
	private List<Entry> weeklyEvents;

	public Calendar() {
		events = new ArrayList<>();
		dailyEvents = new ArrayList<>();
		weeklyEvents = new ArrayList<>();
	}

	public void addEvent(Event event, Time from, Time to) {
		events.add(new Entry(event, from, to));
	}

	/**
	 * Adds a daily event. Daily events happen every day. Events which are added
	 * earlier have a higher priority when polling them.
	 * 
	 * @param event
	 *            Event
	 * @param from
	 *            starting time
	 * @param to
	 *            ending time
	 */
	public void addDailyEvent(Event event, Time from, Time to) {
		dailyEvents.add(new Entry(event, from, to));
	}

	public void addWeeklyEvent(Event event, Time from, Time to, Weekday day) {
		weeklyEvents.add(new Entry(event, from, to, day));
	}

	public Event get(Time time) {
		// normal events
		for (Entry entry : events) {
			if (entry.from.toSeconds() <= time.toSeconds()
					&& entry.to.toSeconds() >= time.toSeconds()) {
				return entry.event;
			}
		}

		// normalize time for periodic events
		Weekday weekday = time.getWeekday();
		time = new Time(time.getHour(), time.getMinute());

		// weekly events
		for (Entry entry : weeklyEvents) {
			if (entry.to.toSeconds() < entry.from.toSeconds()) {
				if (time.toSeconds() < entry.to.toSeconds()
						|| time.toSeconds() > entry.from.toSeconds()) {
					if (entry.weekday == weekday) {
						return entry.event;
					}
				}
			}
			if (entry.from.toSeconds() <= time.toSeconds()
					&& entry.to.toSeconds() >= time.toSeconds()) {
				if (entry.weekday == weekday) {
					return entry.event;
				}
			}
		}

		// daily events
		for (Entry entry : dailyEvents) {
			if (entry.to.toSeconds() < entry.from.toSeconds()) {
				if (time.toSeconds() < entry.to.toSeconds()
						|| time.toSeconds() > entry.from.toSeconds()) {
					return entry.event;
				}
			}
			if (entry.from.toSeconds() <= time.toSeconds()
					&& entry.to.toSeconds() >= time.toSeconds()) {
				return entry.event;
			}
		}
		return null;
	}

	@Override
	public String toString() {
		String str = "";
		for (Entry e : events) {
			str += "[" + e.from + " - " + e.to + "] " + e.event + "\n";
		}
		return str;
	}

}

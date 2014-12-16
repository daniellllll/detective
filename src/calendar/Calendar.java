package calendar;

import java.util.ArrayList;
import java.util.List;

import time.Time;

public class Calendar {

	private class Entry {
		public Event event;
		public Time from, to;

		public Entry(Event event, Time from, Time to) {
			this.event = event;
			this.from = from;
			this.to = to;
		}
	}

	private List<Entry> events;
	private List<Entry> dailyEvents;

	public Calendar() {
		events = new ArrayList<>();
		dailyEvents = new ArrayList<>();
	}

	public void addEvent(Event event, Time from, Time to) {
		events.add(new Entry(event, from, to));
	}

	public void addDailyEvent(Event event, Time from, Time to) {
		dailyEvents.add(new Entry(event, from, to));
	}

	public Event get(Time time) {
		for (Entry entry : events) {
			if (entry.from.toSeconds() <= time.toSeconds()
					&& entry.to.toSeconds() >= time.toSeconds()) {
				return entry.event;
			}
		}
		for (Entry entry : dailyEvents) {
			if (entry.from.getHour() <= time.getHour()
					&& entry.from.getMinute() <= time.getMinute()
					&& entry.to.getHour() >= time.getHour()
					&& entry.to.getMinute() >= time.getMinute()) {
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

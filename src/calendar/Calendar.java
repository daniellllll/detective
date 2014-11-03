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

	private List<Entry> calendar;

	public Calendar() {
		calendar = new ArrayList<>();
	}

	public void add(Event event, Time from, Time to) {
		calendar.add(new Entry(event, from, to));
	}

	public Event get(Time time) {
		for (Entry entry : calendar) {
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
		for (Entry e : calendar) {
			str += "[" + e.from.toString() + " - " + e.to.toString() + "] "
					+ e.event.toString() + "\n";
		}
		return str;
	}

}

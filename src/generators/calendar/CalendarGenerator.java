package generators.calendar;

import time.Time;
import calendar.Calendar;

public abstract class CalendarGenerator {
	protected Time from, to;

	public CalendarGenerator(Time from, Time to) {
		this.from = from;
		this.to = to;
	}

	public abstract Calendar generate();
}

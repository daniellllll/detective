package generators.calendar;

import places.Pub;
import time.Time;
import calendar.Calendar;

public class BarkeeperCalendarGenerator extends CalendarGenerator {
	private Pub pub;

	public BarkeeperCalendarGenerator(Time from, Time to, Pub pub) {
		super(from, to);
		this.pub = pub;
	}

	@Override
	public Calendar generate() {
		// TODO Auto-generated method stub
		return null;
	}

}

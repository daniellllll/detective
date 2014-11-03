package generators.calendar;

import persons.Person;
import time.Time;
import calendar.Calendar;
import calendar.Event;

public abstract class CalendarGenerator {
	protected Time from, to;
	protected Person person;

	public CalendarGenerator(Time from, Time to, Person person) {
		this.from = from;
		this.to = to;
		this.person = person;
	}

	public abstract void generate();

	protected void generateCalendar(Calendar dailyRoutine) {
		Calendar calendar = person.getCalendar();

		Event actEvent = dailyRoutine.get(from);
		Event lastEvent = null;
		Time startTime = new Time(from.toSeconds());

		for (long t = from.toSeconds(); t <= to.toSeconds(); t += 60) {
			Time t1 = new Time(t);
			Time t2 = new Time(t1.getHour(), t1.getMinute());
			actEvent = dailyRoutine.get(t2);
			if (actEvent != lastEvent) {
				calendar.add(actEvent, startTime, new Time(t));
				startTime = new Time(t + 60);
				lastEvent = actEvent;
			}
		}
	}
}
package generators.calendar;

import persons.Person;
import time.Time;
import calendar.Calendar;
import calendar.Event;
import calendar.Event.Activity;

public class BarkeeperCalendarGenerator extends CalendarGenerator {
	private Calendar dailyRoutine;

	public BarkeeperCalendarGenerator(Time from, Time to, Person person) {
		super(from, to, person);

		dailyRoutine = new Calendar();
		Event working = new Event(Activity.WORKING, person.getWorkplace());
		Event sleeping = new Event(Activity.SLEEPING, person.getResidence());
		Event breakfast = new Event(Activity.BREAKFAST, person.getResidence());
		Event bookreading = new Event(Activity.READING_A_BOOK,
				person.getResidence());
		Event lunch = new Event(Activity.LUNCH, person.getResidence());

		dailyRoutine.addEvent(working, new Time(0, 0), new Time(1, 0));
		dailyRoutine.addEvent(sleeping, new Time(1, 1), new Time(10, 0));
		dailyRoutine.addEvent(breakfast, new Time(10, 1), new Time(11, 0));
		dailyRoutine.addEvent(bookreading, new Time(11, 1), new Time(14, 0));
		dailyRoutine.addEvent(lunch, new Time(14, 1), new Time(15, 0));
		dailyRoutine.addEvent(working, new Time(15, 1), new Time(23, 59));
	}

	@Override
	public void generate() {
		generateCalendar(dailyRoutine);
	}

}

package generators.calendar;

import persons.Person;
import time.Time;
import calendar.Calendar;
import calendar.Event;
import calendar.Event.Activity;

public class DockworkerCalendarGenerator extends CalendarGenerator {
	private Calendar dailyRoutine;

	public DockworkerCalendarGenerator(Time from, Time to, Person person) {
		super(from, to, person);

		dailyRoutine = new Calendar();
		Event working = new Event(Activity.WORKING, person.getWorkplace());
		Event sleeping = new Event(Activity.SLEEPING, person.getResidence());
		Event breakfast = new Event(Activity.BREAKFAST, person.getResidence());
		Event bookreading = new Event(Activity.READING_A_BOOK,
				person.getResidence());
		Event lunch = new Event(Activity.LUNCH, person.getWorkplace());
		Event dinner = new Event(Activity.DINNER, person.getResidence());

		dailyRoutine.add(sleeping, new Time(0, 0), new Time(6, 0));
		dailyRoutine.add(breakfast, new Time(6, 1), new Time(7, 0));
		dailyRoutine.add(working, new Time(7, 1), new Time(13, 0));
		dailyRoutine.add(lunch, new Time(13, 1), new Time(14, 0));
		dailyRoutine.add(working, new Time(14, 1), new Time(18, 0));
		dailyRoutine.add(dinner, new Time(18, 1), new Time(19, 0));
		dailyRoutine.add(bookreading, new Time(19, 1), new Time(21, 0));
		dailyRoutine.add(sleeping, new Time(21, 1), new Time(23, 59));		
	}

	@Override
	public void generate() {
		generateCalendar(dailyRoutine);
	}

}
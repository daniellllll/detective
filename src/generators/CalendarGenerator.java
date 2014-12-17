package generators;

import persons.Person;
import places.Place;
import time.Time;
import calendar.Calendar;
import calendar.Event;
import calendar.Event.Activity;

public class CalendarGenerator {

	public static void generate(Person person) {
		Calendar c = person.getCalendar();
		Place home = person.getResidence();

		Event breakfast = new Event(Activity.BREAKFAST, home);
		Event lunch = new Event(Activity.LUNCH, home);
		Event dinner = new Event(Activity.DINNER, home);
		Event book = new Event(Activity.READING_A_BOOK, home);
		Event bath = new Event(Activity.BATH, home);
		Event sleeping = new Event(Activity.SLEEPING, home);

		c.addDailyEvent(breakfast, new Time(7, 0), new Time(7, 45));
		c.addDailyEvent(book, new Time(7, 45), new Time(9, 0));
		c.addDailyEvent(bath, new Time(9, 0), new Time(10, 0));
		c.addDailyEvent(book, new Time(10, 0), new Time(12, 0));
		c.addDailyEvent(lunch, new Time(12, 0), new Time(13, 0));
		c.addDailyEvent(book, new Time(13, 0), new Time(14, 0));
		c.addDailyEvent(bath, new Time(14, 0), new Time(15, 0));
		c.addDailyEvent(book, new Time(15, 0), new Time(18, 0));
		c.addDailyEvent(bath, new Time(18, 0), new Time(19, 0));
		c.addDailyEvent(dinner, new Time(19, 0), new Time(20, 0));
		c.addDailyEvent(book, new Time(20, 0), new Time(23, 0));
		c.addDailyEvent(sleeping, new Time(23, 0), new Time(7, 0));
	}
}

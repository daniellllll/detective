package generators.events;

import calendar.Calendar;
import calendar.Event;
import calendar.Event.Activity;
import main.Environment;
import persons.Person;
import places.Place;
import time.Time;
import time.Time.Weekday;

public class WalkGenerator {

	public static void generateWeeklyEvent(Person person, Place start,
			Place target, Weekday day, Time arrival) {
		Calendar calendar = person.getCalendar();
		Place[] way = Environment.getCity().getShortestWay(start, target);
		System.out.println(start.getName() + " " + target.getName());
		if (way.length < 2)
			return;

		Time from;
		Time to = arrival;
		for (int i = way.length - 2; i >= 0; i--) {
			from = new Time(to.toSeconds() - 60 * 10);
			Event walk = new Event(Activity.WALK, way[i]);
			calendar.addWeeklyEvent(walk, from, to, day);
			to = from;
		}
	}
}

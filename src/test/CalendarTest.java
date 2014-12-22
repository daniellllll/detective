package test;

import static org.junit.Assert.*;

import org.junit.Test;

import places.Bakery;
import time.Time;
import calendar.Calendar;
import calendar.Event;
import calendar.Event.Activity;

public class CalendarTest {

	@Test
	public void testDailyEvents() {
		Calendar c = new Calendar();

		Bakery place = new Bakery("bakery");
		Event e1 = new Event(Activity.WORKING, place);
		Event e2 = new Event(Activity.WORKING, place);

		c.addDailyEvent(e1, new Time(12, 30), new Time(13, 0));
		c.addDailyEvent(e2, new Time(23, 00), new Time(1, 0));

		// test event 1
		assertEquals(null, c.get(new Time(12, 29)));
		assertEquals(e1, c.get(new Time(12, 45)));
		assertEquals(null, c.get(new Time(13, 1)));
		assertEquals(null, c.get(new Time(1900, 1, 1, 12, 29)));
		assertEquals(e1, c.get(new Time(1900, 1, 1, 12, 45)));
		assertEquals(null, c.get(new Time(1900, 1, 1, 13, 1)));

		// test event 2
		assertEquals(null, c.get(new Time(22, 59)));
		assertEquals(e2, c.get(new Time(23, 1)));
		assertEquals(e2, c.get(new Time(0, 0)));
		assertEquals(e2, c.get(new Time(0, 59)));
		assertEquals(null, c.get(new Time(1, 1)));
	}

	@Test
	public void testDailyEventPriorities() {
		Calendar c = new Calendar();

		Bakery place = new Bakery("bakery");
		Event e1 = new Event(Activity.WORKING, place);
		Event e2 = new Event(Activity.WORKING, place);

		c.addDailyEvent(e1, new Time(12, 30), new Time(13, 0));
		c.addDailyEvent(e2, new Time(11, 00), new Time(14, 0));

		assertEquals(e2, c.get(new Time(12, 29)));
		assertEquals(e1, c.get(new Time(12, 30)));
		assertEquals(e1, c.get(new Time(12, 59)));
		assertEquals(e2, c.get(new Time(13, 01)));

	}

}

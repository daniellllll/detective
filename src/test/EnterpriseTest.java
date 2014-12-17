package test;

import static org.junit.Assert.*;

import org.junit.Test;

import calendar.Event.Activity;
import persons.Person;
import persons.Person.Gender;
import places.Pub;
import time.Time;
import time.Timespan;

public class EnterpriseTest {

	@Test
	public void testWorkingTime() {
		Pub enterprise = new Pub("pub");
		Person p1 = new Person(Gender.male, "a", "b", "c", "d", null, null);
		Person p2 = new Person(Gender.male, "a", "b", "c", "d", null, null);
		Person p3 = new Person(Gender.male, "a", "b", "c", "d", null, null);
		enterprise.addPerson(p1);
		enterprise.addPerson(p2);
		enterprise.addPerson(p3);

		// check working times
		Timespan openingTime = enterprise.getOpeningTime();
		for (long sec = openingTime.getStartTime().toSeconds(); sec < openingTime
				.getEndTime().toSeconds(); sec++) {
			Time t = new Time(sec);
			if (p1.getCalendar().get(t).getActivity() != Activity.WORKING) {
				if (p2.getCalendar().get(t).getActivity() != Activity.WORKING) {
					if (p3.getCalendar().get(t).getActivity() != Activity.WORKING) {
						fail();
					}
				}
			}
		}

	}

}

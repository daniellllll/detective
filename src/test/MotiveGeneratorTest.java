package test;

import static org.junit.Assert.*;
import main.Environment;
import org.junit.Test;
import calendar.Event.Activity;
import crime.motive.AffairGenerator;
import crime.motive.MotiveGenerator;
import persons.Person;
import persons.Person.Gender;
import places.Enterprise;
import places.Pub;

public class MotiveGeneratorTest {

	@Test
	public void testAffair() {
		for (int i = 0; i < 1000000; i++) {
			Person personA = new Person(Gender.male, "personA", "", "", "",
					null, null);
			Person personB = new Person(Gender.female, "personB", "", "", "",
					null, null);
			Person affairA = new Person(Gender.female, "affairA", "", "", "",
					null, null);
			Person affairB = new Person(Gender.male, "affairB", "", "", "",
					null, null);
			personA.marry(personB);

			Enterprise workplace = new Pub("pub");
			workplace.addPerson(personA);
			workplace.addPerson(personB);
			workplace.addPerson(affairA);
			workplace.addPerson(affairB);

			Environment.setPersons(new Person[] { personA, personB, affairA,
					affairB });

			MotiveGenerator motive = new AffairGenerator();

			Person affair;
			if (affairA.getCalendar().findWeeklyActivity(Activity.MEET_AFFAIR) != null) {
				affair = affairA;
			} else if (affairB.getCalendar().findWeeklyActivity(
					Activity.MEET_AFFAIR) != null) {
				affair = affairB;
			} else {
				fail("no affair found");
			}
			
			assertNotEquals(motive.getVictim(), null);
			assertNotEquals(motive.getOffender(), null);
		}
	}

}

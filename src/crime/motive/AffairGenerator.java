package crime.motive;

import java.util.ArrayList;
import java.util.List;
import calendar.Event;
import calendar.Event.Activity;
import persons.Person;
import persons.Person.Relationship;
import main.Environment;
import time.Time;
import time.Time.Weekday;
import time.Timespan;
import utils.Random;

public class AffairGenerator extends MotiveGenerator {
	private String reason;

	public AffairGenerator() {
		// find person with partner
		Person person = Random.getRandElem(Environment.getAllPersons());
		while (person.getPartner() == null) {
			person = Random.getRandElem(Environment.getAllPersons());
		}

		// find affair (different gender, no relative)
		List<Relationship> relatives = new ArrayList<>();
		relatives.add(Relationship.FATHER);
		relatives.add(Relationship.MOTHER);
		relatives.add(Relationship.PARTNER);
		relatives.add(Relationship.SIBLING);
		Person affair = Random.getRandElem(Environment.getAllPersons());
		while (person.getGender() == affair.getGender()
				|| relatives.contains(person.getRelationship(affair))) {
			affair = Random.getRandElem(Environment.getAllPersons());
		}

		// add meetings to their calendars
		Weekday weekdays[] = { Weekday.MON, Weekday.TUE, Weekday.WED,
				Weekday.THU, Weekday.FRI, Weekday.SAT, Weekday.SUN };
		Weekday weekday = Random.getRandElem(weekdays);

		Timespan worktimeA = person.getCalendar().findDailyActivity(
				Activity.WORKING);
		Timespan worktimeB = affair.getCalendar().findDailyActivity(
				Activity.WORKING);
		Time from = new Time(0, 0);
		Time to = new Time(from.toSeconds() + 60 * 60);
		while (worktimeA.isInside(to) || worktimeA.isInside(from)
				|| worktimeB.isInside(to) || worktimeB.isInside(from)) {
			from = new Time(from.toSeconds() + 60);
			to = new Time(from.toSeconds() + 60 * 60);
		}

		person.getCalendar().addWeeklyEvent(
				new Event(Activity.MEET_AFFAIR, affair.getResidence(), affair),
				from, to, weekday);
		affair.getCalendar().addWeeklyEvent(
				new Event(Activity.MEET_AFFAIR, affair.getResidence(), person),
				from, to, weekday);

		// determine offender and victim
		reason = person.getName() + ", the partner of "
				+ person.getPartner().getName() + ", has an affair with "
				+ affair.getName() + ". ";
		switch (Random.randInt(1, 4)) {
		// partner of cheater kills affair
		case 1:
			victim = affair;
			offender = person.getPartner();
			reason += "Therefore " + offender.getName() + " killed "
					+ victim.getName() + ".";
			break;
		// partner kills cheater
		case 2:
			victim = person;
			offender = person.getPartner();
			reason += "Therefore " + offender.getName() + " killed "
					+ victim.getName() + ".";
			break;
		// affair kills partner of cheater
		case 3:
			victim = person.getPartner();
			offender = affair;
			reason += "Therefore " + offender.getName() + " killed "
					+ victim.getName() + ".";
			break;
		}
	}

	@Override
	public String toString() {
		return reason;
	}
}

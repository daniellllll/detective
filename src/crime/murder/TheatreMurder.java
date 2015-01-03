package crime.murder;

import java.util.ArrayList;
import java.util.List;

import calendar.Event;
import calendar.Event.Activity;
import main.Environment;
import persons.Person;
import places.Place;
import places.Theatre;
import time.Time;
import utils.Random;

public class TheatreMurder extends MurderGenerator {
	private Time buyingTicketTime;

	public TheatreMurder(Person offender, Person victim) {
		super(offender, victim);
		time = generateTime();
		place = generatePlace();

		Event buyingTicket = new Event(Activity.BUYING_TICKET, place);
		buyingTicketTime = new Time(time.toSeconds() - 60 * 60 * 24 * 7);
		offender.getCalendar().addEvent(buyingTicket, buyingTicketTime,
				new Time(buyingTicketTime.toSeconds() + 600));
	}

	private static Place generatePlace() {
		List<Place> places = new ArrayList<>();
		for (Place p : Environment.getCity().getPlaces()) {
			if (p instanceof Theatre) {
				places.add(p);
			}
		}
		return Random.getRandElem(places);
	}

	private static Time generateTime() {
		Time time = new Time(Time.now().toSeconds() - 60 * 60 * 24 * 7);
		return time;
	}

	@Override
	public String toString() {
		return "At " + buyingTicketTime + " " + offender.getName()
				+ " bought a Ticket for " + place.getName() + ". " + "When "
				+ offender.getName() + " gave " + victim.getName()
				+ " a ticket to " + place.getName() + ", " + victim.getName()
				+ " went to " + place.getName() + " at " + time + ". There "
				+ offender.getName() + " killed " + victim.getName()
				+ " at the toilet.";
	}
}

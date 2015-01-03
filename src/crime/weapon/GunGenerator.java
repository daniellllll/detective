package crime.weapon;

import calendar.Event;
import calendar.Event.Activity;
import main.Environment;
import items.Gun;
import persons.Person;
import persons.Person.Gender;
import places.GunSmithery;
import places.Place;
import time.Time;

public class GunGenerator extends WeaponGenerator {
	private Place place;
	private Time shoppingTime;

	public GunGenerator(Person offender, Time time) {
		super(offender, time);
		weapon = new Gun("gun");
		place = null;
		for (Place p : Environment.getAllPlaces()) {
			if (p instanceof GunSmithery)
				place = p;
		}
		shoppingTime = new Time(time.toSeconds() - 60 * 60 * 24 * 7);
		offender.getCalendar().addEvent(new Event(Activity.SHOPPING, place),
				shoppingTime, new Time(shoppingTime.toSeconds() + 60 * 10));
		addFingerprints();
	}

	@Override
	public String toString() {
		String article = "he";
		if (offender.getGender() == Gender.female)
			article = "she";
		return offender.getName() + " used a gun which " + article
				+ " bought at " + place.getName() + " at " + shoppingTime + ".";
	}
}

package crime.murder;

import persons.Person;
import places.Place;
import time.Time;

public abstract class MurderGenerator {
	protected Person offender;
	protected Person victim;
	protected Time time;
	protected Place place;

	public MurderGenerator(Person offender, Person victim) {
		this.offender = offender;
		this.victim = victim;
	}

	public Place getPlace() {
		return place;
	}

	public Time getTime() {
		return time;
	}
}

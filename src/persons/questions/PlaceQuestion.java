package persons.questions;

import persons.Person;
import time.Time;

public class PlaceQuestion implements Question {
	private Person person;
	private Time time;

	public PlaceQuestion(Person person, Time time) {
		this.person = person;
		this.time = time;
	}

	@Override
	public void answer() {
		// TODO Auto-generated method stub

	}
}

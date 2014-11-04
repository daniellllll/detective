package persons.questions;

import calendar.Event;
import persons.Person;
import time.Time;
import ui.UI;

public class PlaceQuestion implements Question {
	private Person person;
	private Time time;

	public PlaceQuestion(Person person, Time time) {
		this.person = person;
		this.time = time;
	}

	@Override
	public void answer() {
		Event event = person.getCalendar().get(time);
		if (event == null) {
			UI.write("I don't know.");
		} else {
			UI.write(event.toString());
		}
	}
}

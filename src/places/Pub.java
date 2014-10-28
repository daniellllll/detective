package places;

import time.Time;

public class Pub extends Place implements Enterprise {

	public Pub(String name) {
		super(name);
	}

	@Override
	public Time getOpeningTime() {
		return new Time(0, 0, 0, 15, 0);
	}

	@Override
	public Time getClosingTime() {
		return new Time(0, 0, 0, 24, 0);
	}

}

package places;

import time.Timespan;

public class Bakery extends Enterprise {

	public Bakery(String name) {
		super(name, new Timespan(6, 0, 18, 0));
	}

}

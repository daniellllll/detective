package places;

import time.Timespan;

public class Theatre extends Enterprise {

	public Theatre(String name) {
		super(name, new Timespan(18, 0, 23, 0));
	}

}

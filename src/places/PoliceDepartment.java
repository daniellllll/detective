package places;

import time.Timespan;

public class PoliceDepartment extends Enterprise {

	public PoliceDepartment(String name) {
		super(name, new Timespan(0, 0, 23, 59));
	}

}

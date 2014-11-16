package places;

import time.Timespan;

public class FireDepartment extends Enterprise {

	public FireDepartment(String name) {
		super(name, new Timespan(0, 0, 23, 59));
	}

}

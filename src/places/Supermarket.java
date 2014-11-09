package places;

import places.interfaces.Enterprise;
import time.Time;

public class Supermarket extends Place implements Enterprise{

	public Supermarket(String name) {
		super(name);
	}

	@Override
	public Time getOpeningTime() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Time getClosingTime() {
		// TODO Auto-generated method stub
		return null;
	}

}

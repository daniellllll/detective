package places;

import places.interfaces.Enterprise;
import time.Time;

public class Bakery extends Place implements Enterprise{

	public Bakery(String name) {
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

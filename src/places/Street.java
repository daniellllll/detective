package places;

import places.interfaces.Enterprise;
import places.interfaces.Residence;

public class Street extends Place {

	public Street(String name) {
		super(name);
	}

	public int getLastHousenumber() {
		int i = 0;
		for (Place place : reachablePlaces) {
			if (place instanceof Enterprise || place instanceof Residence) {
				i++;
			}
		}
		return i;
	}

}

package items;

import places.Place;
import places.Street;
import places.Enterprise;
import ui.UI;
import main.Environment;

public class Map extends Item {

	public Map(String name) {
		super(name);
	}

	@Override
	public void inspect() {
		for (Place p : Environment.getCity().getPlaces()) {
			if (p instanceof Street) {
				UI.write(p.getName());
				for (Place r : p.getReachablePlaces()) {
					if (r instanceof Enterprise) {
						UI.write("   " + r.getName());
					}
				}
			}
		}
	}

}

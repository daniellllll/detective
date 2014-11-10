package items;

import places.Place;
import places.Street;
import places.interfaces.Enterprise;
import ui.UI;
import main.Environment;

public class Map extends Item {

	public Map(String name) {
		super(name);
	}

	@Override
	public void inspect() {
		for (Place p : Environment.getAllPlaces()) {
			if (p instanceof Street) {
				UI.write(p.getName());
				for(Place r:p.getReachablePlaces()){
					if(r instanceof Enterprise){
						UI.write("   " + r.getName());
					}
				}
			}
		}
	}

	@Override
	public void use(Item item) {
		// TODO Auto-generated method stub

	}

}

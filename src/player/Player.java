package player;

import places.Place;

public class Player {
	private Place place;

	public void goTo(Place place) {
		this.place = place;
	}
	
	public Place getPlace(){
		return place;
	}
}

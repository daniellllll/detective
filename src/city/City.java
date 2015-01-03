package city;

import java.util.ArrayList;
import java.util.List;
import places.Place;
import places.Street;

public class City {
	private List<Place> places;

	public City() {
		places = new ArrayList<>();
	}

	public void addPlace(Place place) {
		places.add(place);
	}

	public void connectPlaces(Place a, Place b) {
		a.addReachablePlace(b);
		b.addReachablePlace(a);
	}

	public Street[] getStreets() {
		int count = 0;
		for (Place p : places) {
			if (p instanceof Street)
				count++;
		}
		Street[] streets = new Street[count];
		int i = 0;
		for (Place p : places) {
			if (p instanceof Street)
				streets[i++] = (Street) p;
		}
		return streets;
	}

	public Place[] getPlaces() {
		Place[] p = new Place[places.size()];
		int i = 0;
		for (Place place : places) {
			p[i++] = place;
		}
		return p;
	}

	public Place[] getShortestWay(Place start, Place target) {
		return findShortestWay(new Place[] { start }, target);
	}

	private Place[] findShortestWay(Place[] way, Place target) {
		Place actPlace = way[way.length - 1];
		if (actPlace == target)
			return way;
		Place[] newWay = new Place[way.length + 1];
		for (int i = 0; i < way.length; i++) {
			newWay[i] = way[i];
		}
		for (Place place : actPlace.getReachablePlaces()) {
			if (!contains(way, place)) {
				Place[] nextWay = newWay.clone();
				nextWay[nextWay.length - 1] = place;
				Place[] shortestWay = findShortestWay(nextWay, target);
				if (shortestWay != null)
					return shortestWay;
			}
		}
		return null;
	}

	private boolean contains(Place[] places, Place place) {
		for (Place p : places) {
			if (p == place)
				return true;
		}
		return false;
	}

}

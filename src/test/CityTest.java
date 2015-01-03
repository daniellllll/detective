package test;

import static org.junit.Assert.*;

import org.junit.Test;

import places.Place;
import places.Pub;
import places.Street;
import city.City;

public class CityTest {

	@Test
	public void testShortestWay() {
		City city = new City();
		Place start = new Pub("start");
		Place streetA = new Street("streetA");
		Place streetB = new Street("streetB");
		Place streetC = new Street("streetC");
		Place streetD = new Street("streetD");
		Place streetE = new Street("streetE");
		Place streetF = new Street("streetF");
		Place targetA = new Pub("targetA");
		Place targetB = new Pub("targetB");
		Place targetC = new Pub("targetC");
		Place targetD = new Pub("targetD");

		city.connectPlaces(start, streetA);
		city.connectPlaces(streetA, streetB);
		city.connectPlaces(streetB, streetC);
		city.connectPlaces(streetC, targetA);
		city.connectPlaces(streetB, streetD);
		city.connectPlaces(streetD, streetE);
		city.connectPlaces(streetE, streetF);
		city.connectPlaces(streetF, targetA);
		city.connectPlaces(streetF, targetC);
		city.connectPlaces(start, targetD);

		Place[] wayA = new Place[] { start, streetA, streetB, streetC, targetA };
		Place[] wayB = new Place[] { start, streetA, streetB, streetC, targetA,
				streetF, targetC };
		Place[] wayC = new Place[] { start, targetD };
		Place[] wayD = new Place[] { start };
		assertArrayEquals(wayA, city.getShortestWay(start, targetA));
		assertArrayEquals(null, city.getShortestWay(start, targetB));
		assertArrayEquals(wayB, city.getShortestWay(start, targetC));
		assertArrayEquals(wayC, city.getShortestWay(start, targetD));
		assertArrayEquals(wayD, city.getShortestWay(start, start));
	}

}

package generators.places;

import persons.Person;
import places.Cabin;
import places.Residence;

public class ResidenceGenerator {

	private static int houseNumber = 0;
	
	public static Residence generate(Person[] persons) {
		Residence residence = null;
		switch (utils.Random.randInt(0, 1)) {
		case 0:
			residence = new Cabin("Feldweg "+ ++houseNumber);
			break;
		}
		residence.setResidents(persons);
		return residence;
	}
}

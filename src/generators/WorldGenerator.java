package generators;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import main.Environment;
import persons.Person;
import places.*;
import places.Enterprise;
import places.interfaces.Residence;

public class WorldGenerator {

	private static List<Place> places = new ArrayList<>();
	private static Street streets[];

	public static void generate() {
		generateStreets();
		generateResidences();
		generateWorkplaces();
		Place p[] = new Place[places.size()];
		int i = 0;
		for (Place place : places) {
			p[i++] = place;
		}
		Environment.setPlaces(p);
		generateCalendars();
	}

	private static void generateStreets() {
		String names[] = { "Church Street", "Kingsley Road", "Ives Street",
				"Moore Street", "Winchester Street", "Wooler Street",
				"Kinglake Road", "Coopers Road", "Oldfield Grove",
				"Croft Street", "Mayflower Avenue", "Downtown Road",
				"Silver Walk", "Fair Street" };
		streets = new Street[names.length];
		int i = 0;
		for (String name : names) {
			streets[i++] = new Street(name);
			places.add(streets[i - 1]);
		}

		// connect streets
		for (i = 0; i < 5; i++) {
			for (int j = streets.length - 5; j < streets.length; j++) {
				streets[i].addReachablePlace(streets[j]);
				streets[j].addReachablePlace(streets[i]);
			}
		}

	}

	private static void generateResidences() {
		Person persons[] = Environment.getAllPersons();
		int i;
		for (i = 0; i + 2 < persons.length; i += 3) {
			Person residents[] = new Person[3];
			residents[0] = persons[i];
			residents[1] = persons[i + 1];
			residents[2] = persons[i + 2];
			Street street = getNextStreet();
			Residence residence = new Cabin(street.getName() + " "
					+ (street.getLastHousenumber() + 1));
			street.addReachablePlace((Place) residence);
			((Place) residence).addReachablePlace(street);
			places.add((Place) residence);
			residents[0].setResidence((Place) residence);
			residents[1].setResidence((Place) residence);
			residents[2].setResidence((Place) residence);
		}
		int rest = persons.length - i;
		if (rest > 0) {
			Person residents[] = new Person[rest];
			for (int j = 0; j < rest; j++) {
				residents[j] = persons[i++];
			}
			Street street = getNextStreet();
			Residence residence = new Cabin(street.getName() + " "
					+ (street.getLastHousenumber() + 1));
			street.addReachablePlace((Place) residence);
			((Place) residence).addReachablePlace(street);
			places.add((Place) residence);
			for (int j = 0; j < rest; j++) {
				residents[j].setResidence((Place) residence);
			}
		}
	}

	private static void generateWorkplaces() {
		List<Place> workplaces = new ArrayList<Place>();
		workplaces.add(new Supermarket("supermarket"));
		workplaces.add(new Pub("pub"));
		workplaces.add(new Park("park"));
		workplaces.add(new Harbor("harbor"));
		workplaces.add(new Salon("salon"));
		workplaces.add(new Bakery("bakery"));
		workplaces.add(new ButchersShop("butchers shop"));
		workplaces.add(new GunSmithery("gunsmithery"));
		workplaces.add(new Tailoring("tailoring"));
		workplaces.add(new Boat("boat Salty"));
		workplaces.add(new Boat("boat Sandy"));
		workplaces.add(new Boat("boat Calaloo"));
		workplaces.add(new Brothel("brothel"));
		workplaces.add(new Theatre("theatre"));
		workplaces.add(new TownHall("townhall"));
		workplaces.add(new FireDepartment("firedepartment"));
		workplaces.add(new PoliceDepartment("policedepartment"));

		for (Place p : workplaces) {
			places.add(p);
			Street street = getNextStreet();
			p.addReachablePlace(street);
			street.addReachablePlace(p);
		}
	}

	private static void generateCalendars() {
		Person randpersons[] = new Person[Environment.getAllPersons().length];
		utils.Random.getUniqueElems(Environment.getAllPersons(), randpersons);
		Queue<Person> persons = new LinkedList<>();
		for (Person p : randpersons) {
			persons.offer(p);
		}

		for (Place place : Environment.getAllPlaces()) {
			if (place instanceof Enterprise) {
				if (place instanceof Supermarket) {
					assignPersonsToJob(persons, 3, place);
				} else if (place instanceof Park) {
					assignPersonsToJob(persons, 1, place);
				} else if (place instanceof Bakery) {
					assignPersonsToJob(persons, 2, place);
				} else if (place instanceof ButchersShop) {
					assignPersonsToJob(persons, 2, place);
				} else if (place instanceof Brothel) {
					assignPersonsToJob(persons, 4, place);
				} else if (place instanceof FireDepartment) {
					assignPersonsToJob(persons, 10, place);
				} else if (place instanceof GunSmithery) {
					assignPersonsToJob(persons, 1, place);
				} else if (place instanceof Harbor) {
					assignPersonsToJob(persons, 20, place);
				} else if (place instanceof PoliceDepartment) {
					assignPersonsToJob(persons, 6, place);
				} else if (place instanceof Pub) {
					assignPersonsToJob(persons, 2, place);
				} else if (place instanceof Salon) {
					assignPersonsToJob(persons, 2, place);
				} else if (place instanceof Tailoring) {
					assignPersonsToJob(persons, 1, place);
				} else if (place instanceof Theatre) {
					assignPersonsToJob(persons, 8, place);
				} else if (place instanceof TownHall) {
					assignPersonsToJob(persons, 1, place);
				} else if (place instanceof Boat) {
					assignPersonsToJob(persons, 5, place);
				}
			}
		}

		for (Person p : Environment.getAllPersons()) {
			CalendarGenerator.generate(p);
		}

		// TODO unemployed persons
		int count = 0;
		Place place = new Pub("Place for unemployed people");
		while (persons.size() != 0) {
			count++;
			assignPersonsToJob(persons, 1, place);
		}
		System.out.println("TODO: " + count + " unemployed persons");
	}

	private static Street getNextStreet() {
		Street smallest = null;
		int nr = 999;
		for (Street s : streets) {
			if (s.getLastHousenumber() < nr) {
				smallest = s;
				nr = s.getLastHousenumber();
			}
		}
		return smallest;
	}

	private static void assignPersonsToJob(Queue<Person> persons, int number,
			Place place) {

		for (int i = 0; i < number; i++) {
			Person p = persons.poll();
			((Enterprise) place).addPerson(p);
		}
	}

}

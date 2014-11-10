package generators;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import main.Environment;
import generators.calendar.*;
import time.Time;
import utils.Random;
import persons.Person;
import persons.Person.Gender;
import places.*;
import places.interfaces.Enterprise;
import places.interfaces.Residence;

public class WorldGenerator {

	private static String[] surnames_m_german = { "Peter", "Michael", "Klaus",
			"Kurt", "Richard", "Franz", "Maximillian", "Johann", "Christian",
			"Manuel", "Gabriel", "Gerhard", "Paul", "Daniel", "Oskar",
			"Herbert", "Adolf", "Friedolin", "Janosch", "Jonas", "Fabian",
			"Philipp", "Sebastian", "Lukas", "Niko", "Olaf" };
	private static String[] surnames_f_german = { "Anna", "Lisa", "Lena",
			"Ronja", "Lara", "Maria", "Johanna", "Karolin", "Annemarie",
			"Lotte", "Lieschen", "Lieselotte", "Laura", "Rebecka", "Alexandra",
			"Petra", "Beate", "Ursula", "Rosi", "Erika", "Jolanda", "Tina",
			"Maresa", "Elisabeth", "Josephine", "Heidi" };
	private static String[] lastnames_german = { "Meyer", "Becker",
			"Kirschenbaum", "Silbereisen", "Berg", "Stein", "Vogel", "Eckel",
			"Bamberger", "Hertz", "Bauer", "Henkel", "Vetter", "Hofmann",
			"Theis", "Schneider", "Fischer", "Weber", "Wagner", "Schulz",
			"Koch", "Schwarz", "Weiss", "Wolf", "Braun", "Zimmermann",
			"Schmidt", "Hahn", "Berger" };
	private static String[] surnames_m_english = { "Zachory", "Mike", "Alan",
			"Dorian", "Matt", "Douglas", "John", "Jack", "Ronan", "Harry",
			"Jim", "Ted", "Stewart", "Peter", "Mikel", "Ethan", "Berry",
			"Phil", "Barney", "George", "Malcolm", "Steven", "Coby", "Hall",
			"Jason", "Justin", "Charlie", "Joe" };
	private static String[] surnames_f_english = { "Debbie", "Liz", "Lesley",
			"Kendra", "Jules", "Ruby", "Jean", "Victoria", "Rose", "Maggy",
			"Diana", "Linda", "Lilith", "Nicole", "Stephanie", "Joana",
			"Elizabeth", "Megan", "Carry" };
	private static String[] lastnames_english = { "Smith", "Miller", "Roberts",
			"Cooper", "Colt", "Middleton", "Tannenbaum", "Kirschenbaum",
			"Tanner", "Jackson", "Bolton", "King", "Nichols" };
	private static String[] surnames_m_japanese = { "Akio", "Daiki", "Daisuke",
			"Haruto", "Hayato", "Hiroki", "Hiroshi", "Itsuki", "Kaito",
			"Masahiro", "Masaru", "Shinichi", "Shin", "Takeshi", "Tetsuya",
			"Tsubasa", "Yamato", "Yoshio" };
	private static String[] surnames_f_japanese = { "Ai", "Aiko", "Aimi",
			"Akemi", "Akira", "Amaya", "Emi", "Beniko", "Chiyoko", "Halura",
			"Fumiko", "Hina", "Hotaru", "Kazumi", "Mana", "Mayumi", "Mei",
			"Misaki", "Miyu", "Sakura", "Sayuri", "Teiko", "Yoko", "Yukiko",
			"Yumi", };
	private static String[] lastnames_japanese = { "Fujimoto", "Gasai",
			"Ichigawa", "Ishida", "Joshura", "Kawazu", "Kitamura", "Nishimoto",
			"Okamura", "Suzuki", "Tanaka", "Tsung", "Yamamoto", "Watabe" };
	private static String[][][] countries = {
			{ surnames_m_japanese, surnames_f_japanese, lastnames_japanese },
			{ surnames_m_german, surnames_f_german, lastnames_german },
			{ surnames_m_english, surnames_f_english, lastnames_english } };
	private static String[] surnames_m;
	private static String[] surnames_f;
	private static String[] lastnames;
	private static String[] haircolors = { "blonde", "brunette", "black", "red" };
	private static String[] characteristics = { "a big nose" };
	private static List<Place> places = new ArrayList<>();
	private static Street streets[];

	public static void generate() {
		generatePersons();
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

	private static void generatePersons() {
		String country[][] = Random.getRandElem(countries);
		surnames_m = country[0];
		surnames_f = country[1];
		lastnames = country[2];

		Person[] persons = new Person[50];

		int firstGenNum = 20;
		int personCount = 0;

		String familynames[] = new String[firstGenNum / 2];
		Random.getUniqueElems(lastnames, familynames);
		for (String lastname : familynames) {
			// generate parents (1. Gen.)
			Person man = getRandPerson(persons, Gender.male, lastname);
			Person woman = getRandPerson(persons, Gender.female, lastname);
			man.setPartner(woman);
			woman.setPartner(man);
			persons[personCount++] = man;
			persons[personCount++] = woman;
			// generate childs (2. Gen.)
			persons[personCount++] = getRandPerson(persons, Gender.male, man,
					woman);
			persons[personCount++] = getRandPerson(persons, Gender.male, man,
					woman);
			persons[personCount++] = getRandPerson(
					persons,
					Random.getRandElem(new Gender[] { Gender.male,
							Gender.female }), man, woman);
		}
		Environment.setPersons(persons);
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
		Time start = new Time(Time.getStartTime().toSeconds() - 60 * 60 * 24
				* 7);
		Time end = Time.getStartTime();
		Person randpersons[] = new Person[Environment.getAllPersons().length];
		utils.Random.getUniqueElems(Environment.getAllPersons(), randpersons);
		Queue<Person> persons = new LinkedList<>();
		for (Person p : randpersons) {
			persons.offer(p);
		}

		for (Place place : Environment.getAllPlaces()) {
			if (place instanceof Enterprise) {
				if (place instanceof Supermarket) {
					assignPersonsToJob(persons, 3, place,
							ShopassistantCalendarGenerator.class, start, end);
				} else if (place instanceof Park) {
					assignPersonsToJob(persons, 1, place,
							GardenerCalendarGenerator.class, start, end);
				} else if (place instanceof Bakery) {
					assignPersonsToJob(persons, 2, place,
							BakerCalendarGenerator.class, start, end);
				} else if (place instanceof ButchersShop) {
					assignPersonsToJob(persons, 2, place,
							ButcherCalendarGenerator.class, start, end);
				} else if (place instanceof Brothel) {
					assignPersonsToJob(persons, 4, place,
							ProstituteCalendarGenerator.class, start, end);
				} else if (place instanceof FireDepartment) {
					assignPersonsToJob(persons, 5, place,
							FirefighterCalendarGenerator.class, start, end);
				} else if (place instanceof GunSmithery) {
					assignPersonsToJob(persons, 1, place,
							GunsmithCalendarGenerator.class, start, end);
				} else if (place instanceof Harbor) {
					assignPersonsToJob(persons, 10, place,
							DockworkerCalendarGenerator.class, start, end);
				} else if (place instanceof PoliceDepartment) {
					assignPersonsToJob(persons, 5, place,
							PoliceofficerCalendarGenerator.class, start, end);
				} else if (place instanceof Pub) {
					assignPersonsToJob(persons, 2, place,
							BarkeeperCalendarGenerator.class, start, end);
				} else if (place instanceof Salon) {
					assignPersonsToJob(persons, 2, place,
							HairdresserCalendarGenerator.class, start, end);
				} else if (place instanceof Tailoring) {
					assignPersonsToJob(persons, 1, place,
							TailorCalendarGenerator.class, start, end);
				} else if (place instanceof Theatre) {
					assignPersonsToJob(persons, 2, place,
							ActorCalendarGenerator.class, start, end);
				} else if (place instanceof TownHall) {
					assignPersonsToJob(persons, 1, place,
							MayorCalendarGenerator.class, start, end);
				} else if (place instanceof Boat) {
					assignPersonsToJob(persons, 3, place,
							FisherCalendarGenerator.class, start, end);
				}
			}
		}
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
			Place place, @SuppressWarnings("rawtypes") Class generator,
			Time start, Time end) {

		for (int i = 0; i < number; i++) {
			Person p = persons.poll();
			p.setWorkplace(place);
			try {
				@SuppressWarnings("unchecked")
				Constructor<?> con = generator.getConstructor(Time.class,
						Time.class, Person.class);
				CalendarGenerator g = (CalendarGenerator) con.newInstance(
						start, end, p);
				g.generate();
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
	}

	private static boolean nameExists(Person[] persons, String surname,
			String lastname) {
		for (Person person : persons) {
			if (person != null) {
				if (person.getLastname().equals(lastname)
						&& person.getSurname().equals(surname)) {
					return true;
				}
			}
		}
		return false;
	}

	private static String getSurname(Person[] persons, String lastname,
			Gender gender) {
		String[] surnames = surnames_m;
		if (gender == Gender.female) {
			surnames = surnames_f;
		}
		String surname = surnames[Random.randInt(0, surnames.length)];
		while (nameExists(persons, surname, lastname)) {
			surname = surnames[Random.randInt(0, surnames.length)];
		}
		return surname;
	}

	private static Person getRandPerson(Person persons[], Gender gender,
			Person father, Person mother) {
		String lastname = mother.getLastname();
		String surname = getSurname(persons, lastname, gender);
		String haircolor = Random.getRandElem(haircolors);
		String characteristic = Random.getRandElem(characteristics);
		return new Person(gender, surname, lastname, haircolor, characteristic,
				father, mother);
	}

	private static Person getRandPerson(Person persons[], Gender gender,
			String lastname) {
		String surname = getSurname(persons, lastname, gender);
		String haircolor = Random.getRandElem(haircolors);
		String characteristic = Random.getRandElem(characteristics);
		return new Person(gender, surname, lastname, haircolor, characteristic,
				null, null);
	}
}

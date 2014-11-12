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
import persons.Person.Relationship;
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
			"Tanner", "Jackson", "Bolton", "King", "Nichols", "Brown" };
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
		// choose random country
		String country[][] = Random.getRandElem(countries);
		surnames_m = country[0];
		surnames_f = country[1];
		lastnames = country[2];

		// One array per generation, one list for all persons
		Person gen1[] = new Person[28];
		Person gen2[] = new Person[42];
		Person gen3[] = new Person[63];
		List<Person> persons = new ArrayList<>();

		// generate 1. and 2. generation
		String familynames[] = new String[gen1.length / 2];
		Random.getUniqueElems(lastnames, familynames);
		int g1count = 0, g2count = 0, g3count = 0;
		for (String familyname : familynames) {
			// 1. generation (man + woman)
			Person man = getRandPerson(persons, Gender.male, familyname);
			Person woman = getRandPerson(persons, Gender.female, familyname);
			man.marry(woman);
			gen1[g1count++] = man;
			gen1[g1count++] = woman;
			persons.add(man);
			persons.add(woman);
			// 2. generation (female + male + random Child of man + woman)
			gen2[g2count++] = man.makeChild(
					getSurname(persons, man.getLastname(), Gender.male),
					Gender.male);
			persons.add(gen2[g2count - 1]);
			gen2[g2count++] = man.makeChild(
					getSurname(persons, man.getLastname(), Gender.female),
					Gender.female);
			persons.add(gen2[g2count - 1]);
			Gender gender = getGender();
			gen2[g2count++] = man.makeChild(
					getSurname(persons, man.getLastname(), gender), gender);
			persons.add(gen2[g2count - 1]);

		}

		// marry people of 2. generation, generate 3. generation
		Queue<Person> unmarried = new LinkedList<>();
		for (Person p : gen2) {
			unmarried.offer(p);
		}
		while (!unmarried.isEmpty()) {
			// find right partner for marriage.
			// Put wrong partners back into queue
			Person p1 = unmarried.poll();
			Person p2 = unmarried.poll();
			while (p2.getGender() == p1.getGender()
					|| p1.getRelationship(p2) == Relationship.SIBLING) {
				unmarried.offer(p2);
				p2 = unmarried.poll();
			}
			p1.marry(p2);
			// 3. generation (female + male + random Child of p1 + p2)
			gen3[g3count++] = p1.makeChild(
					getSurname(persons, p1.getLastname(), Gender.male),
					Gender.male);
			persons.add(gen3[g3count - 1]);
			gen3[g3count++] = p1.makeChild(
					getSurname(persons, p1.getLastname(), Gender.female),
					Gender.female);
			persons.add(gen3[g3count - 1]);
			Gender gender = getGender();
			gen3[g3count++] = p1.makeChild(
					getSurname(persons, p1.getLastname(), gender), gender);
			persons.add(gen3[g3count - 1]);
		}

		// marry people of 3. generation
		for (Person p : gen3) {
			unmarried.offer(p);
		}
		while (unmarried.size() >= 2) {
			// find right partner for marriage.
			// Put wrong partners back into queue
			Person p1 = unmarried.poll();
			Person p2 = unmarried.poll();
			while (p2.getGender() == p1.getGender()
					|| p1.getRelationship(p2) == Relationship.SIBLING) {
				unmarried.offer(p2);
				p2 = unmarried.poll();
			}
			p1.marry(p2);
		}

		// add all persons to Environment
		Person[] allpersons = new Person[persons.size()];
		for (int i = 0; i < allpersons.length; i++) {
			allpersons[i] = persons.get(i);
		}
		Environment.setPersons(allpersons);
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
		Time end = new Time(Time.now().toSeconds() + 60 * 60 * 24 * 30);
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
					assignPersonsToJob(persons, 10, place,
							FirefighterCalendarGenerator.class, start, end);
				} else if (place instanceof GunSmithery) {
					assignPersonsToJob(persons, 1, place,
							GunsmithCalendarGenerator.class, start, end);
				} else if (place instanceof Harbor) {
					assignPersonsToJob(persons, 20, place,
							DockworkerCalendarGenerator.class, start, end);
				} else if (place instanceof PoliceDepartment) {
					assignPersonsToJob(persons, 6, place,
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
					assignPersonsToJob(persons, 8, place,
							ActorCalendarGenerator.class, start, end);
				} else if (place instanceof TownHall) {
					assignPersonsToJob(persons, 1, place,
							MayorCalendarGenerator.class, start, end);
				} else if (place instanceof Boat) {
					assignPersonsToJob(persons, 5, place,
							FisherCalendarGenerator.class, start, end);
				}
			}
		}

		// TODO unemployed persons
		int count = 0;
		Place place = new Pub("Place for unemployed people");
		while (persons.size() != 0) {
			count++;
			assignPersonsToJob(persons, 1, place,
					TailorCalendarGenerator.class, start, end);
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

	private static boolean nameExists(List<Person> persons, String surname,
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

	private static String getSurname(List<Person> persons, String lastname,
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

	private static Person getRandPerson(List<Person> persons, Gender gender,
			String lastname) {
		String surname = getSurname(persons, lastname, gender);
		String haircolor = Random.getRandElem(haircolors);
		String characteristic = Random.getRandElem(characteristics);
		return new Person(gender, surname, lastname, haircolor, characteristic,
				null, null);
	}

	private static Gender actGender = Gender.male;

	private static Gender getGender() {
		if (actGender == Gender.male) {
			actGender = Gender.female;
			return Gender.female;
		}
		actGender = Gender.male;
		return Gender.male;
	}
}

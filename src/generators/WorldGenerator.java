package generators;

import java.util.List;

import main.Environment;
import generators.calendar.*;
import generators.places.ResidenceGenerator;
import time.Time;
import utils.Random;
import persons.Person;
import persons.Person.Gender;
import places.*;

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
	private static List<Place> places;

	public static void generate() {
		generatePersons();
		generateResidences();
		generateWorkplacesAndCalendars();
		Place p[] = new Place[places.size()];
		int i = 0;
		for (Place place : places) {
			p[i++] = place;
		}
		Environment.setPlaces(p);
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

	private static void generateResidences() {
		Person persons[] = Environment.getAllPersons();
		int i;
		for (i = 0; i + 2 < persons.length; i += 3) {
			Person residents[] = new Person[3];
			residents[0] = persons[i];
			residents[1] = persons[i + 1];
			residents[2] = persons[i + 2];
			Residence residence = ResidenceGenerator.generate(residents);
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
			Residence residence = ResidenceGenerator.generate(residents);
			places.add((Place) residence);
			for (int j = 0; j < rest; j++) {
				residents[j].setResidence((Place) residence);
			}
		}
	}

	private static void generateWorkplacesAndCalendars() {
		Person persons[] = Environment.getAllPersons();
		Place supermarket = new Supermarket("supermarket");
		Place pub = new Pub("pub");
		Place park = new Park("park");
		Place harbor = new Harbor("harbor");
		Place salon = new Salon("salon");
		Place bakery = new Bakery("bakery");
		Place butchersshop = new ButchersShop("butchers shop");
		Place gunSmithery = new GunSmithery("gunsmithery");
		Place tailoring = new Tailoring("tailoring");
		Place boatsalty = new Boat("boat Salty");
		Place boatsandy = new Boat("boat Sandy");
		Place boatcalaloo = new Boat("boat Calaloo");
		Place brothel = new Brothel("brothel");
		places.add(supermarket);
		places.add(pub);
		places.add(park);
		places.add(harbor);
		places.add(salon);
		places.add(bakery);
		places.add(butchersshop);
		places.add(gunSmithery);
		places.add(tailoring);
		places.add(boatsalty);
		places.add(boatsandy);
		places.add(boatcalaloo);
		places.add(brothel);
		Person randpersons[] = new Person[persons.length];
		utils.Random.getUniqueElems(persons, randpersons);

		int i = 0;
		for (Person p : randpersons) {
			Time start = new Time(Time.getStartTime().toSeconds() - 60 * 60
					* 24 * 7);
			Time end = Time.getStartTime();
			CalendarGenerator generator = null;

			if (i < 3) {
				p.setWorkplace(supermarket);
				generator = new ShopassistantCalendarGenerator(start, end, p);
			} else if (i < 4) {
				p.setWorkplace(park);
				generator = new GardenerCalendarGenerator(start, end, p);
			} else if (i < 14) {
				p.setWorkplace(harbor);
				generator = new DockworkerCalendarGenerator(start, end, p);
			} else if (i < 18) {
				p.setWorkplace(salon);
				generator = new HairdresserCalendarGenerator(start, end, p);
			} else if (i < 21) {
				p.setWorkplace(bakery);
				generator = new BakerCalendarGenerator(start, end, p);
			} else if (i < 24) {
				p.setWorkplace(butchersshop);
				generator = new ButcherCalendarGenerator(start, end, p);
			} else if (i < 25) {
				p.setWorkplace(gunSmithery);
				generator = new GunsmithCalendarGenerator(start, end, p);
			} else if (i < 27) {
				p.setWorkplace(tailoring);
				generator = new TailorCalendarGenerator(start, end, p);
			} else if (i < 33) {
				p.setWorkplace(boatsalty);
				generator = new FisherCalendarGenerator(start, end, p);
			} else if (i < 38) {
				p.setWorkplace(boatsandy);
				generator = new FisherCalendarGenerator(start, end, p);
			} else if (i < 43) {
				p.setWorkplace(boatcalaloo);
				generator = new FisherCalendarGenerator(start, end, p);
			} else if (i < 48) {
				p.setWorkplace(brothel);
				generator = new ProstituteCalendarGenerator(start, end, p);
			} else {
				p.setWorkplace(pub);
				generator = new BarkeeperCalendarGenerator(start, end, p);
			}

			generator.generate();

			i++;
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

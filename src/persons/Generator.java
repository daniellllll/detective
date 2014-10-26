package persons;

import utils.Random;
import persons.Person.Gender;

public class Generator {

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

	public static Person[] generate() {
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

		// Nachname auswählen
		// Anzahl der Generationen festlegen
		// alle Familien der 1. Generation erzeugen (nur Geschwister)
		// Familien heiraten untereinander
		// Paare bekommen Kinder
		// Kinder heiraten jemanden aus der eigenen Generation (nicht aus
		// Family)
		return persons;
	}

	private static boolean nameExists(Person[] persons, String lastname) {
		for (Person person : persons) {
			if (person != null) {
				if (person.getLastname().equals(lastname)) {
					return true;
				}
			}
		}
		return false;
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

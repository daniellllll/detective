package persons;

import utils.Random;
import persons.Person.Gender;

public class Generator {

	private static String[] surnames_m = { "Daniel", "Heinz", "Peter", "Max",
			"Marko", "Johann", "Sebastian", "Harald", "Xaver", "Wolfgang",
			"Richard", "Johnny", "Flin", "Legolas", "Patrick", "Tony",
			"Michael", "Dr. Reinhard" };
	private static String[] surnames_f = { "Susanna", "Josefine", "Tina",
			"Spohie", "Marie", "Gina", "Tekla", "Babette", "Hanna", "Roxanne",
			"Colette", "Zoey", "Isabelle", "Jasmin", "Rose", "Ena", "Meike",
			"Lisa", "Tammie" };
	private static String[] lastnames = { "von Hamsterdamm", "Pinkeldington",
			"Jones", "von Hier", "Miller", "Smith", "Colt", "Middleton",
			"Bang", "Williams", "Richardson" };
	private static String[] haircolors = { "blonde", "brunette", "black", "red" };
	private static String[] characteristics = { "a big nose" };

	public static Person[] generate() {
		Person[] persons = new Person[100];

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
			System.out.println(man.getSurname() + " und " + woman.getSurname()
					+ " " + lastname);
			// generate childs (2. Gen.)
			persons[personCount++] = getRandPerson(persons, Gender.male, man,
					woman);
			persons[personCount++] = getRandPerson(persons, Gender.male, man,
					woman);
			persons[personCount++] = getRandPerson(
					persons,
					Random.getRandElem(new Gender[] { Gender.male,
							Gender.female }), man, woman);
			System.out.println("   Kinder: "
					+ persons[personCount - 1].getSurname() + ", "
					+ persons[personCount - 2].getSurname() + ", "
					+ persons[personCount - 3].getSurname() + "\n");
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

	private static String getNewFamilyname(Person[] persons) {
		String lastname = lastnames[Random.randInt(0, lastnames.length)];
		while (nameExists(persons, lastname)) {
			lastname = lastnames[Random.randInt(0, lastnames.length)];
		}
		return lastname;
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

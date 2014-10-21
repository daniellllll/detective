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
		int[] numChilds = { 2, 2, 4, 4, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3,
				3, 3, 3, 4, 4, 4, 4, 4 };
		int childCount = 0;
		int personCount = 0;

		// for (personCount = 0; personCount < firstGenNum; personCount += 2) {
		// String lastname = getNewFamilyname(persons);
		// String surname_m = getSurname(persons, lastname, Gender.male);
		// String surname_f = getSurname(persons, lastname, Gender.female);
		// persons[personCount] = new Person(Gender.male, surname_m, lastname,
		// null, null, null, null);
		// persons[personCount + 1] = new Person(Gender.female, surname_f,
		// lastname, null, null, null, null);
		// System.out
		// .println(surname_m + " und " + surname_f + " " + lastname);
		// }

		String familynames[] = new String[firstGenNum / 2];
		Random.getUniqueElems(lastnames, familynames);
		for (String lastname : familynames) {
			Person man = getRandPerson(persons, Gender.male, lastname);
			Person woman = getRandPerson(persons, Gender.female, lastname);
			System.out.println(man.getSurname()+ " und "+woman.getSurname()+" "+lastname);
			
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

	private static Person getRandPerson(Person persons[], Gender gender, Person father, Person mother){
		String lastname = mother.getLastname();
		String surname = getSurname(persons,lastname,gender);
		String haircolor = Random.getRandElem(haircolors);
		String characteristic = Random.getRandElem(characteristics);
		return new Person(gender, surname, lastname, haircolor, characteristic, father, mother);
	}
	
	private static Person getRandPerson(Person persons[], Gender gender, String lastname){
		String surname = getSurname(persons,lastname,gender);
		String haircolor = Random.getRandElem(haircolors);
		String characteristic = Random.getRandElem(characteristics);
		return new Person(gender, surname, lastname, haircolor, characteristic, null, null);
	}
}

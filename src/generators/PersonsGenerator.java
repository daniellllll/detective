package generators;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import main.Environment;
import persons.Person;
import persons.Person.Gender;
import persons.Person.Relationship;
import utils.Random;

public class PersonsGenerator {

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

	public static void generate() {
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

	private static Person getRandPerson(List<Person> persons, Gender gender,
			String lastname) {
		String surname = getSurname(persons, lastname, gender);
		String haircolor = Random.getRandElem(haircolors);
		String characteristic = Random.getRandElem(characteristics);
		return new Person(gender, surname, lastname, haircolor, characteristic,
				null, null);
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

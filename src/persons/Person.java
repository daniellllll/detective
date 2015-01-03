package persons;

import persons.questions.Question;
import places.Place;
import ui.UI;
import interfaces.Inspectable;
import items.Fingerprint;
import calendar.Calendar;

public class Person implements Inspectable {
	private static int fingerprintCount = 1;

	public enum Relationship {
		FATHER, MOTHER, SIBLING, FRIEND, PARTNER
	}

	private String surname, lastname;
	private String haircolor;
	private String characteristic;
	private Circle friends, enemies, family, acquaintances;
	private Calendar calendar;
	private Person father, mother, partner;
	private Gender gender;
	private Place residence, workplace;
	private int askedQuestions;

	public enum Gender {
		male, female
	};

	public Person(Gender gender, String surname, String lastname,
			String haircolor, String characteristic, Person father,
			Person mother) {
		this.surname = surname;
		this.lastname = lastname;
		this.haircolor = haircolor;
		this.characteristic = characteristic;
		friends = new Circle();
		enemies = new Circle();
		family = new Circle();
		acquaintances = new Circle();
		calendar = new Calendar();
		this.father = father;
		this.mother = mother;
		this.gender = gender;
		askedQuestions = 0;
	}

	public void ask(Question question) {
		askedQuestions++;
		if (askedQuestions < 5) {
			question.answer();
			UI.write("It's a pleasure for me to help you.");
		} else if (askedQuestions < 10) {
			question.answer();
			UI.write("It's a pleasure for me to help you but I'm in a hurry now.");
		} else if (askedQuestions < 15) {
			question.answer();
			UI.write("I'm sorry, but your questions are annoying me a bit.");
		} else {
			UI.write("Go please. I don't want to answer any further questions.");
		}
	}

	public Relationship getRelationship(Person person) {
		if (person == father) {
			return Relationship.FATHER;
		}
		if (person == mother) {
			return Relationship.MOTHER;
		}
		if (person.father == father && person.mother == mother
				&& father != null && mother != null) {
			return Relationship.SIBLING;
		}
		if (person.partner == this) {
			return Relationship.PARTNER;
		}

		return null;
	}

	public void marry(Person partner) {
		this.partner = partner;
		partner.partner = this;
		if (gender == Gender.female) {
			lastname = partner.lastname;
		} else {
			partner.lastname = lastname;
		}
	}

	public Person makeChild(String surname, Gender gender) {
		Person child;
		if (gender == Gender.male) {
			child = new Person(gender, surname, lastname, haircolor,
					characteristic, this, partner);
		} else {
			child = new Person(gender, surname, lastname, haircolor,
					characteristic, partner, this);
		}
		return child;
	}

	public String getSurname() {
		return surname;
	}

	public String getLastname() {
		return lastname;
	}

	public String getHaircolor() {
		return haircolor;
	}

	public String getCharacteristic() {
		return characteristic;
	}

	public Circle getFriends() {
		return friends;
	}

	public Circle getEnemies() {
		return enemies;
	}

	public Circle getFamily() {
		return family;
	}

	public Calendar getCalendar() {
		return calendar;
	}

	public Person getFather() {
		return father;
	}

	public Person getMother() {
		return mother;
	}

	public Person getPartner() {
		return partner;
	}

	public Gender getGender() {
		return gender;
	}

	public Circle getAcquaintances() {
		return acquaintances;
	}

	public Calendar getHistory() {
		return calendar;
	}

	public Place getResidence() {
		return residence;
	}

	public void setResidence(Place residence) {
		this.residence = residence;
	}

	public Place getWorkplace() {
		return workplace;
	}

	public void setWorkplace(Place workplace) {
		this.workplace = workplace;
	}

	public Fingerprint createFingerprint() {
		return new Fingerprint("fingerprint #" + fingerprintCount++, this);
	}

	@Override
	public String getName() {
		return surname + " " + lastname;
	}

	@Override
	public void inspect() {
		UI.write(surname + " " + lastname + " has " + haircolor + " hair and "
				+ characteristic + ".");
	}

}

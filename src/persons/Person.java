package persons;

import persons.questions.Question;
import places.Place;
import ui.UI;
import interfaces.Inspectable;
import calendar.Calendar;

public class Person implements Inspectable {

	public enum Relationship {
		FATHER, MOTHER, SIBLING, FRIEND, PARTNER
	}

	private String surname, lastname;
	private String haircolor;
	private String characteristic;
	private Circle friends, enemies, family, acquaintances;
	private Calendar calendar;
	private Person father, mother, siblings[], partner;
	private Gender gender;
	private Place residence, workplace;

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
		siblings = new Person[0];
		acquaintances = new Circle();
		calendar = new Calendar();
		this.father = father;
		this.mother = mother;
		this.gender = gender;
	}

	public void ask(Question question) {
		question.answer();
	}

	public Relationship getRelationship(Person person) {
		if (person == father) {
			return Relationship.FATHER;
		}
		if (person == mother) {
			return Relationship.MOTHER;
		}
		for (Person p : siblings) {
			if (p == person) {
				return Relationship.SIBLING;
			}
		}

		return null;
	}

	public void setSiblings(Person[] siblings) {
		this.siblings = siblings;
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

	public Person[] getSiblings() {
		return siblings;
	}

	public Person getPartner() {
		return partner;
	}

	public void setPartner(Person partner) {
		this.partner = partner;
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

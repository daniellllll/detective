package persons;

import ui.UI;
import interfaces.Inspectable;
import calendar.Calendar;

public class Person implements Inspectable {

	private String surname, lastname;
	private String haircolor;
	private String characteristic;
	private Circle friends, enemies, family, acquaintances;
	private Calendar calendar;
	private Person father, mother, siblings[], partner;
	private Gender gender;

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

	@Override
	public String getName() {
		return surname + " " + lastname;
	}

	@Override
	public void inspect() {
		UI.write(surname + " " + lastname + " has " + haircolor
				+ " hair and " + characteristic + ".");
	}

}

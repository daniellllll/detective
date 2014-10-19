package persons;

import calendar.Calendar;

public class Person {
	
	private String name;
	private String haircolor;
	private String characteristic;
	private Circle friends, enemies, family, acquaintances;
	private Calendar history;
	
	public Person (String name, String haircolor, String characteristic){
		this.name = name;
		this.haircolor = haircolor;
		this.characteristic = characteristic;
		friends = new Circle();
		enemies = new Circle();
		family = new Circle();
		acquaintances = new Circle();
	}
	
	public String getName(){
		return name;
	}
	
	public String getHaircolor(){
		return haircolor;
	}
	
	public String getCharacteristic(){
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

	public Circle getAcquaintances() {
		return acquaintances;
	}
	
	public Calendar getHistory(){
		return history;
	}
	

	
	
}

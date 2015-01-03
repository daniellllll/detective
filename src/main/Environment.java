package main;

import city.City;
import interfaces.Inspectable;
import interfaces.Useable;
import items.Item;
import persons.Person;
import places.Place;
import player.Player;

public class Environment {
	private static Player player;
	private static Person[] persons;
	private static City city;

	public static void setPlayer(Player p) {
		player = p;
	}

	public static Player getPlayer() {
		return player;
	}

	public static void setCity(City c) {
		city = c;
	}

	public static City getCity() {
		return city;
	}

	public static void setPersons(Person p[]) {
		persons = p;
	}

	public static Person[] getAllPersons() {
		return persons;
	}	

	public static Useable[] getUseables() {
		Useable useables[] = new Useable[player.getPlace().getUseables().length
				+ player.getItems().length];
		int i = 0;
		for (Useable u : player.getPlace().getUseables()) {
			useables[i++] = u;
		}
		for (Item item : player.getItems()) {
			useables[i++] = item;
		}

		return useables;
	}

	public static Inspectable[] getInspectables() {
		Inspectable[] inspectables = new Inspectable[2
				+ player.getPlace().getInspectables().length
				+ player.getItems().length];
		inspectables[0] = player.getPlace();
		inspectables[1] = player;
		int i = 2;
		for (Inspectable insp : player.getPlace().getInspectables()) {
			inspectables[i++] = insp;
		}
		for (Item item : player.getItems()) {
			inspectables[i++] = item;
		}
		return inspectables;
	}

	public static Place getActPlace() {
		return player.getPlace();
	}
}

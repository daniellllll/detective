package main;

import interfaces.Inspectable;
import interfaces.Useable;
import items.Item;
import persons.Person;
import places.Place;
import player.Player;

public class Environment {
	private static Player player;
	private static Person[] persons;
	private static Place[] places;

	public static void setPlayer(Player p) {
		player = p;
	}

	public static void setPersons(Person p[]) {
		persons = p;
	}

	public static Person[] getAllPersons() {
		return persons;
	}

	public static void setPlaces(Place p[]) {
		places = p;
	}

	public static Place[] getAllPlaces() {
		return places;
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

	public static Person getPerson(String fullname) {
		for (Person p : player.getPlace().getPersons()) {
			if (p.getName().equals(fullname)) {
				return p;
			}
		}
		return null;
	}
}

package main;

import interfaces.Inspectable;
import interfaces.Useable;
import items.Item;
import persons.Person;
import player.Player;

public class Environment {
	private Player player;

	public Environment(Player player) {
		this.player = player;
	}

	public Useable[] getUseables() {
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

	public Inspectable[] getInspectables() {
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
	
	public Person getPerson(String fullname) {		
		for (Person p : player.getPlace().getPersons()) {
			if(p.getName().equals(fullname)){
				return p;
			}
		}
		return null;
	}
}

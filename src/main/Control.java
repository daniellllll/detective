package main;

import interfaces.Inspectable;
import interfaces.Useable;
import items.*;
import persons.Generator;
import persons.Person;
import persons.Person.QuestionType;
import places.Place;
import player.Player;
import time.Time;
import ui.UI;
import ui.UIListener;

public class Control implements UIListener {
	private Inspectable inspectables[];
	private Useable useables[];
	private Person persons[];
	private Player player;

	public static void main(String[] args) {
		new Control();
	}

	public Control() {
		// set time
		Time.set(new Time(1815, 3, 21, 12, 0));
		
		persons = Generator.generate();
		Place pub = new Place("Pub");
		for (Person p : persons) {
			pub.addPerson(p);
		}

		Knife knife = new Knife("knife");
		knife.insertInto(pub);
		Pencil pencil = new Pencil("pencil");
		pencil.insertInto(pub);
		Notepad notepad = new Notepad("notepad");
		notepad.insertInto(pub);
		Watch watch = new Watch("watch");
		watch.insertInto(pub);

		player = new Player();
		player.goTo(pub);

		UI.addUIListener(this);
		UI.write("You are in the Pub.");
		while (true) {
			UI.waitForInput();
		}
	}

	@Override
	public void onInspect(String name) {
		updateInspectables();
		for (Inspectable insp : inspectables) {
			if (insp.getName().equals(name)) {
				insp.inspect();
				return;
			}
		}
		UI.write("There is no \"" + name + "\"");
	}

	@Override
	public void onUse(String item1, String item2) {
		updateUseables();
		Useable a = null, b = null;
		for (Useable useable : useables) {
			if (useable.getName().equals(item1)) {
				a = useable;
			} else if (useable.getName().equals(item2)) {
				b = useable;
			}
			if (a != null && b != null) {
				b.use((Item) a);
				return;
			}
		}
		UI.write("There is no " + item1 + " or " + item2 + ".");
	}

	@Override
	public void onTake(String name) {
		updateInspectables();
		for (Inspectable insp : inspectables) {
			if (insp.getName().equals(name)) {
				if (insp instanceof Item) {
					((Item) insp).insertInto(player);
					return;
				}
			}
		}
		UI.write("There is no \"" + name + "\"");
	}

	private void updateInspectables() {
		inspectables = new Inspectable[2
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
	}

	private void updateUseables() {
		useables = new Useable[player.getPlace().getUseables().length
				+ player.getItems().length];
		int i = 0;
		for (Useable u : player.getPlace().getUseables()) {
			useables[i++] = u;
		}
		for (Item item : player.getItems()) {
			useables[i++] = item;
		}
	}

	private Person getAvailablePerson(String name) {
		for (Person p : player.getPlace().getPersons()) {
			if (p.getName().equals(name)) {
				return p;
			}
		}
		return null;
	}

	@Override
	public void onAsk(String name, QuestionType type, String question) {
		Person personA = getAvailablePerson(name);
		Person personB = getAvailablePerson(question);
		if (personA == null) {
			UI.write(personA.getName() + " is not here!");
			return;
		}
		if (personB == null) {
			UI.write(personB.getName() + " is not here!");
			return;
		}

		personA.ask(type, personB);

	}

}

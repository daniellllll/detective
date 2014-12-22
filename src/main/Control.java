package main;

import crime.CrimeGenerator;
import generators.PersonsGenerator;
import generators.WorldGenerator;
import interfaces.Inspectable;
import interfaces.Useable;
import items.*;
import persons.Person;
import persons.questions.Question;
import places.*;
import player.Player;
import time.Time;
import ui.UI;
import ui.UIListener;

public class Control implements UIListener {
	private Player player;

	public static void main(String[] args) {
		new Control();
	}

	public Control() {
		// set time
		Time.setStartTime(new Time(1815, 3, 21, 17, 50));

		PersonsGenerator.generate();
		WorldGenerator.generate();
		CrimeGenerator.generate();

		Place pub = null;
		for (Place p : Environment.getAllPlaces()) {
			if (p instanceof Pub) {
				pub = p;
			}
		}

		Knife knife = new Knife("knife");
		knife.insertInto(pub);
		Pencil pencil = new Pencil("pencil");
		pencil.insertInto(pub);
		Notepad notepad = new Notepad("notepad");
		notepad.insertInto(pub);
		Watch watch = new Watch("watch");
		watch.insertInto(pub);
		Map map = new Map("map");
		map.insertInto(pub);

		player = new Player();
		Environment.setPlayer(player);
		player.goTo(pub);

		UI.setUIListener(this);
		UI.write("You are in the pub.");
		while (true) {
			UI.waitForInput();
		}
	}

	@Override
	public void onInspect(Inspectable inspectable) {
		inspectable.inspect();
	}

	@Override
	public void onUse(String item1, String item2) {
		Useable a = null, b = null;
		for (Useable useable : Environment.getUseables()) {
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
		for (Inspectable insp : Environment.getInspectables()) {
			if (insp.getName().equals(name)) {
				if (insp instanceof Item) {
					((Item) insp).insertInto(player);
					return;
				}
			}
		}
		UI.write("There is no \"" + name + "\"");
	}

	@Override
	public void onAsk(Person person, Question question) {
		person.ask(question);
	}

	@Override
	public void onGoto(Place place) {
		player.goTo(place);
	}

}

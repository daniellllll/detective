package main;

import city.City;
import crime.CrimeGenerator;
import generators.PersonsGenerator;
import generators.WorldGenerator;
import interfaces.Inspectable;
import interfaces.ItemContainer;
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
		UI.disableOutput();

		// set time
		Time.setStartTime(new Time(1815, 3, 21, 17, 50));

		// create city
		Environment.setCity(new City());

		// start generators
		PersonsGenerator.generate();
		WorldGenerator.generate();
		CrimeGenerator.generate();

		// start in pub
		Place pub = null;
		for (Place p : Environment.getCity().getPlaces()) {
			if (p instanceof Pub) {
				pub = p;
			}
		}
		player = new Player();
		Environment.setPlayer(player);
		player.goTo(pub);

		// add example Items
		Knife knife = new Knife("knife");
		knife.insertInto(pub);
		Pencil pencil = new Pencil("pencil");
		pencil.insertInto(pub);
		Notepad notepad = new Notepad("notepad");
		notepad.insertInto(pub);
		Watch watch = new Watch("watch");
		watch.insertInto(pub);
		Fingerprint fingerprint = new Fingerprint("Fingerprint", null);
		fingerprint.insertInto(watch);
		Map map = new Map("map");
		map.insertInto(pub);
		FingerprintFile fingerprintfile = new FingerprintFile(
				"fingerprint file");
		fingerprintfile.insertInto(player);
		Magnifier magnifier = new Magnifier("magnifier");
		magnifier.insertInto(player);

		UI.enableOutput();
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
	public void onTake(Item item, Item from) {
		if (from != null) {
			from.removeItem(item);
		}
		item.insertInto(player);
	}

	@Override
	public void onAsk(Person person, Question question) {
		person.ask(question);
	}

	@Override
	public void onGoto(Place place) {
		player.goTo(place);
		UI.write("you are at " + place.getName());
	}

	@Override
	public void onPut(Item item, ItemContainer into) {
		if (player.hasItem(item))
			into.insertItem(item);
		else
			UI.write("You must own " + item.getName());
	}

}

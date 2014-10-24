package main;

import interfaces.Inspectable;
import items.Knife;
import persons.Generator;
import persons.Person;
import places.Place;
import player.Player;
import ui.UI;
import ui.UIListener;

public class Control implements UIListener {
	private Inspectable inspectables[];
	private Player player;

	public static void main(String[] args) {
		new Control();
	}

	public Control() {
		Person persons[] = Generator.generate();
		Place pub = new Place("Pub");
		for (Person p : persons) {
			pub.addPerson(p);
		}
		
		Knife knife = new Knife("knife");
		pub.addItem(knife);

		player = new Player();
		player.goTo(pub);		

		UI.addUIListener(this);
		UI.write("You are in the Pub.");
		while (true)
			UI.waitForInput();
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
		UI.write("There is no \""+name+"\"");
	}

	private void updateInspectables() {
		inspectables = new Inspectable[1 + player.getPlace().getInspectables().length];
		inspectables[0] = player.getPlace();
		int i = 1;
		for (Inspectable insp : player.getPlace().getInspectables()) {
			inspectables[i++] = insp;
		}
	}

}

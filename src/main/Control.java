package main;

import interfaces.Inspectable;
import interfaces.Useable;
import items.*;
import persons.Generator;
import persons.Person;
import places.Place;
import player.Player;
import ui.UI;
import ui.UIListener;

public class Control implements UIListener {
	private Inspectable inspectables[];
	private Useable useables[];
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
		Pencil pencil = new Pencil("pencil");
		pub.addItem(pencil);
		Notepad notepad = new Notepad("notepad");
		pub.addItem(notepad);
		Watch watch = new Watch("watch");
		pub.addItem(watch);

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
		
	}
	
	private void updateInspectables() {
		inspectables = new Inspectable[2 + player.getPlace().getInspectables().length];
		inspectables[0] = player.getPlace();
		inspectables[1] = player;
		int i = 2;
		for (Inspectable insp : player.getPlace().getInspectables()) {
			inspectables[i++] = insp;
		}
	}

	private void updateUseables() {
		useables = new Useable[player.getPlace().getUseables().length];
		int i = 0;
		for (Useable u : player.getPlace().getUseables()) {
			useables[i++] = u;
		}
	}

}

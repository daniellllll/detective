package main;

import persons.Generator;
import persons.Person;
import ui.UI;

public class Control {

	public static void main(String[] args) {
//		UI.write("gib was ein:");
//		String input = UI.read();
//		UI.write("du hast eingegeben: " + input);
		Generator.generate();
	}

}

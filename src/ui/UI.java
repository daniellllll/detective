package ui;

import interfaces.Inspectable;
import interfaces.ItemContainer;
import items.Item;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import main.Environment;
import persons.Person;
import persons.questions.*;
import places.Place;
import time.Time;

public class UI {

	private static UIListener listener;
	private static boolean outputEnabled = true;

	public static void enableOutput() {
		outputEnabled = true;
	}

	public static void disableOutput() {
		outputEnabled = false;
	}

	public static void write(String text) {
		if (outputEnabled)
			System.out.println(text);
	}

	public static boolean ask(String question) {
		UI.write(question);
		if (UI.read().equals("y"))
			return true;
		return false;
	}

	public static void waitForInput() {
		System.out.print("> ");
		String input = read();
		String cmd = null;
		int cmdend = input.indexOf(" ");
		if (cmdend == -1) {
			cmd = input;
			input = null;
		} else {
			cmd = input.substring(0, cmdend);
			input = input.substring(cmdend + 1);
		}

		switch (cmd) {
		case "inspect":
			if (input == null) {
				listener.onInspect(Environment.getActPlace());
				break;
			}
			Inspectable insp = getInspectable(input);
			if (insp == null)
				UI.write("There is no \"" + input + "\"");
			else
				listener.onInspect(insp);
			break;
		case "use":
			int forbegin = input.indexOf(" for ");
			String item1 = input.substring(0, forbegin);
			String item2 = input.substring(forbegin + 5);
			listener.onUse(item1, item2);
			break;
		case "take":
			int indexOfFrom = input.indexOf(" from ");
			if (indexOfFrom != -1) {
				String strItem1 = input.substring(0, indexOfFrom);
				String strItem2 = input.substring(indexOfFrom + 6);
				Item itemA = getItemFrom(strItem1, strItem2);
				Item itemB = getItem(strItem2);
				if (itemA == null) {
					UI.write("There is no " + strItem1 + ".");
					return;
				}
				if (itemB == null) {
					UI.write("There is no " + strItem2 + ".");
					return;
				}
				listener.onTake(itemA, itemB);
			} else {
				Item item = getItem(input);
				if (item == null) {
					UI.write("There is no " + input + ".");
					return;
				}
				listener.onTake(item, null);
			}
			break;
		case "put":
			int indexOfPut = input.indexOf(" into ");
			if (indexOfPut != -1) {
				String strItem1 = input.substring(0, indexOfPut);
				String strItem2 = input.substring(indexOfPut + 6);
				Item itemA = getItem(strItem1);
				ItemContainer itemB = getItemContainer(strItem2);
				if (itemA == null) {
					UI.write("There is no " + strItem1 + ".");
					return;
				}
				if (itemB == null) {
					UI.write("There is no " + strItem2 + ".");
					return;
				}
				listener.onPut(itemA, itemB);
			} else {
				UI.write("Wrong command!");
			}
			break;
		case "goto":
			Place place = getPlace(input);
			if (place == null) {
				UI.write("This place isn't reachable!");
				return;
			}
			listener.onGoto(place);
			break;
		case "ask":
			Person person = getPerson(input);
			if (person == null) {
				UI.write("This Person isn't here!");
				return;
			}
			String question = read();

			// Relationship question
			if (question.startsWith("do you know")) {
				Person b = getPerson(question.substring(12));
				if (b == null) {
					UI.write("This person isn't here!");
					return;
				}
				Question q = new RelationshipQuestion(person, b);
				listener.onAsk(person, q);
			}

			// Place question
			else if (question.startsWith("where were you at")) {
				Time time = new Time(question.substring(18));
				Question q = new PlaceQuestion(person, time);
				listener.onAsk(person, q);
			}

			// Residence question
			else if (question.equals("where do you live")) {
				Question q = new ResidenceQuestion(person);
				listener.onAsk(person, q);
			}

			// Workplace question
			else if (question.equals("where do you work")) {
				Question q = new WorkplaceQuestion(person);
				listener.onAsk(person, q);
			}

			// Fingerprint question
			else if (question.equals("can i take your fingerprint")) {
				Question q = new FingerprintQuestion(person);
				listener.onAsk(person, q);
			}

			else {
				write("Unknown question.");
				return;
			}
			break;
		}
	}

	public static String read() {
		try {
			BufferedReader bufferRead = new BufferedReader(
					new InputStreamReader(System.in));
			return bufferRead.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void setUIListener(UIListener uilistener) {
		listener = uilistener;
	}

	private static boolean compare(String strA, String strB) {
		if (strA.length() > strB.length())
			return false;
		strA = strA.toLowerCase();
		strB = strB.toLowerCase();
		if (strA.equals(strB))
			return true;
		if (strB.substring(0, strA.length()).equals(strA)) {
			return ask("did you mean " + strB + "? (y/n)");
		}
		return false;
	}

	private static Place getPlace(String name) {
		if (compare(name, Environment.getActPlace().getName())) {
			return Environment.getActPlace();
		}
		for (Place p : Environment.getActPlace().getReachablePlaces()) {
			if (compare(name, p.getName())) {
				return p;
			}
		}
		return null;
	}

	private static Person getPerson(String name) {
		for (Person p : Environment.getActPlace().getPersons()) {
			if (compare(name, p.getName())) {
				return p;
			}
		}
		return null;
	}

	private static Inspectable getInspectable(String name) {
		for (Inspectable insp : Environment.getInspectables()) {
			if (compare(name, insp.getName())) {
				return insp;
			}
		}
		return null;
	}

	private static Item getItem(String name) {
		for (Inspectable insp : Environment.getInspectables()) {
			if (insp instanceof Item) {
				if (compare(name, insp.getName())) {
					return (Item) insp;
				}
			}
		}
		return null;
	}

	private static ItemContainer getItemContainer(String name) {
		Item item = getItem(name);
		if (item != null)
			return item;
		return getPlace(name);
	}

	private static Item getItemFrom(String item, String from) {
		for (Inspectable insp : Environment.getInspectables()) {
			if (insp instanceof Item) {
				if (compare(from, insp.getName())) {
					for (Item content : ((Item) insp).getItems()) {
						if (compare(item, content.getName())) {
							return content;
						}
					}
				}
			}
		}
		return null;
	}
}

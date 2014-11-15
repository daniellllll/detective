package ui;

import interfaces.Inspectable;

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

	public static void write(String text) {
		System.out.println(text);
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
			for (Inspectable insp : Environment.getInspectables()) {
				if (insp.getName().equals(input)) {
					listener.onInspect(insp);
					return;
				}
			}
			UI.write("There is no \"" + input + "\"");
			break;
		case "use":
			int forbegin = input.indexOf("for");
			String item1 = input.substring(0, forbegin - 1);
			String item2 = input.substring(forbegin + 4);
			listener.onUse(item1, item2);
			break;
		case "take":
			listener.onTake(input);
			break;
		case "goto":
			Place p = Environment.getPlace(input);
			if (p == null) {
				UI.write("This place isn't reachable!");
				return;
			}
			listener.onGoto(p);
			break;
		case "ask":
			Person person = Environment.getPerson(input);
			if (person == null) {
				UI.write("This Person isn't here!");
				return;
			}
			String question = read();

			// Relationship question
			if (question.startsWith("do you know")) {
				Person b = Environment.getPerson(question.substring(12));
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
}

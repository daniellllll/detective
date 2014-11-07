package ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import main.Environment;
import persons.Person;
import persons.questions.PlaceQuestion;
import persons.questions.Question;
import persons.questions.RelationshipQuestion;
import persons.questions.ResidenceQuestion;
import time.Time;

public class UI {

	private static UIListener listener;
	private static Environment env;

	public static void write(String text) {
		System.out.println(text);
	}

	public static void waitForInput() {
		System.out.print("> ");
		String input = read();
		int cmdend = input.indexOf(" ");
		String cmd = input.substring(0, cmdend);
		input = input.substring(cmdend + 1);
		switch (cmd) {
		case "inspect":
			listener.onInspect(input);
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
		case "ask":
			Person person = env.getPerson(input);
			if (person == null) {
				UI.write("This Person isn't here!");
				return;
			}
			String question = read();

			// Relationship question
			if (question.startsWith("do you know")) {
				Person b = env.getPerson(question.substring(12));
				if (b == null) {
					UI.write("This person isn't here!");
					return;
				}
				Question q = new RelationshipQuestion(person, b);
				listener.onAsk(person, q);
			}

			// Place question
			if (question.startsWith("where were you at")) {
				Time time = new Time(question.substring(18));
				Question q = new PlaceQuestion(person, time);
				listener.onAsk(person, q);
			}
			
			// Residence question
			if (question.equals("where do you live")){
				Question q = new ResidenceQuestion(person);
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

	public static void setEnvironment(Environment environment) {
		env = environment;
	}
}

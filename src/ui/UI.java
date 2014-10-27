package ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import persons.Person.QuestionType;

public class UI {

	private static UIListener listener;

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
			String question = read();
			QuestionType type = null;
			if (question.startsWith("do you know")) {
				question = question.substring(12);
				type = QuestionType.DO_YOU_KNOW;
			} else {
				write("Unknown question.");
				return;
			}
			listener.onAsk(input, type, question);
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

	public static void addUIListener(UIListener uilistener) {
		listener = uilistener;
	}
}

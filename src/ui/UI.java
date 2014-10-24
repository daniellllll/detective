package ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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
			String item1 = input.substring(0, forbegin-1);
			String item2 = input.substring(forbegin+4);
			listener.onUse(item1, item2);
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

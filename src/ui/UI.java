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
		}
	}

	private static String read() {
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

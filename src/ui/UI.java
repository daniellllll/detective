package ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class UI {

	public void write(String text) {
		System.out.println(text);
	}

	public String read() {
		try {
			BufferedReader bufferRead = new BufferedReader(
					new InputStreamReader(System.in));
			return bufferRead.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}

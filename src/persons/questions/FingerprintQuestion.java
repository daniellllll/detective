package persons.questions;

import items.Fingerprint;
import items.FingerprintFile;
import items.Item;
import main.Environment;
import persons.Person;
import ui.UI;

public class FingerprintQuestion implements Question {
	private Person person;

	public FingerprintQuestion(Person person) {
		this.person = person;
	}

	@Override
	public void answer() {
		UI.write("OK, here is my fingerprint.");
		Fingerprint fp = person.createFingerprint();
		fp.insertInto(Environment.getPlayer());
		if (UI.ask("Do you want to insert the fingerprint into your fingerprint file? (y/n)")) {
			for (Item item : Environment.getPlayer().getItems()) {
				if (item instanceof FingerprintFile) {
					fp.insertInto(item);
					break;
				}
			}
		}
	}

}

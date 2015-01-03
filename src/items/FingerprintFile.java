package items;

import ui.UI;

public class FingerprintFile extends Item {

	public FingerprintFile(String name) {
		super(name);
	}

	@Override
	public void inspect() {
		if (items.size() == 0) {
			UI.write("It's a fingerprint file. You can use it to collect fingerprints from items. Therefore you have to put fingerprints, which you took from items, into the file.");
			return;
		}
		int count = 1;
		for(Item fingerprint : items){
			UI.write(count++ + ". ) " + fingerprint.getName());
		}
	}

	@Override
	public void use(Item item) {
		UI.write("This won't work.");
	}

}

package items;

import player.Player;
import ui.UI;

public class Notepad extends Item {

	private String notes;

	public Notepad(String name) {
		super(name);
		notes = "";
	}

	@Override
	public void inspect() {
		UI.write(notes);

	}

	@Override
	public void use(Item item) {		
		if (item instanceof Pencil) {
			if (item.container instanceof Player && container instanceof Player) {
				UI.write("Make notes!");
				String note = UI.read();
				notes += note + "\n\n";
			} else {
				UI.write("You should own the items you want to use!");
			}
		} else {
			super.use(item);
		}
		
	}

}

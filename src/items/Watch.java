package items;

import time.Time;
import ui.UI;

public class Watch extends Item{

	public Watch(String name) {
		super(name);
	}

	@Override
	public void inspect() {		
		UI.write(Time.now().toString());
	}

}

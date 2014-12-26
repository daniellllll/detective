package items;

import ui.UI;

public class Gun extends Item{

	public Gun(String name) {
		super(name);
	}	

	@Override
	public void inspect() {
		UI.write("A Gun.");
	}

	@Override
	public void use(Item item) {
		// TODO Auto-generated method stub
		
	}

}

package items;

import ui.UI;

public class Knife extends Item{

	public Knife(String name) {
		super(name);
	}	

	@Override
	public void inspect() {
		UI.write("This knife looks sharp");
	}

	@Override
	public void use(Item item) {
		// TODO Auto-generated method stub
		
	}

}

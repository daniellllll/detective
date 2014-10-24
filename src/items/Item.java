package items;

import interfaces.Inspectable;
import interfaces.Useable;

public abstract class Item implements Inspectable, Useable{
	protected String name;
	
	public Item(String name){
		this.name = name;
	}
	
	@Override
	public String getName() {
		return name;
	}
	
}

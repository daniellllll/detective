package items;

import interfaces.Inspectable;

public abstract class Item implements Inspectable{
	protected String name;
	
	public Item(String name){
		this.name = name;
	}
	
	public abstract void insert(Item item);
	
	public abstract void use(Item item);
	
}

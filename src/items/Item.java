package items;

public abstract class Item {
	protected String name;
	
	public Item(String name){
		this.name = name;
	}
	
	public abstract void insert(Item item);
	
	public abstract void use(Item item);
	
	public abstract void inspect();
}

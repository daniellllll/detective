package ui;

public interface UIListener {

	public void onInspect(String name);

	public void onUse(String item1, String item2);

	public void onTake(String name);
}

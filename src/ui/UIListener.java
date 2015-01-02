package ui;

import interfaces.Inspectable;
import items.Item;
import persons.Person;
import persons.questions.Question;
import places.Place;

public interface UIListener {

	public void onInspect(Inspectable inspectable);

	public void onUse(String item1, String item2);

	public void onTake(Item item, Item from);
	
	public void onPut(Item item, Item into);

	public void onGoto(Place place);

	public void onAsk(Person person, Question question);
}

package ui;

import persons.Person;
import persons.questions.Question;

public interface UIListener {

	public void onInspect(String name);

	public void onUse(String item1, String item2);

	public void onTake(String name);
	
	public void onAsk(Person person, Question question);
}

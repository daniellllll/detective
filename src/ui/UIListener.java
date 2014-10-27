package ui;

import persons.Person.QuestionType;

public interface UIListener {

	public void onInspect(String name);

	public void onUse(String item1, String item2);

	public void onTake(String name);
	
	public void onAsk(String name, QuestionType type, String question);
}

package persons.questions;

import persons.Person;
import ui.UI;

public class ResidenceQuestion implements Question{

	private Person person;
	
	public ResidenceQuestion(Person person){
		this.person = person;
	}
	
	@Override
	public void answer() {
		UI.write("I live at " + person.getResidence().getName());
	}

}

package persons.questions;

import persons.Person;
import ui.UI;

public class WorkplaceQuestion implements Question{

	private Person person;
	
	public WorkplaceQuestion(Person person){
		this.person = person;
	}
	
	@Override
	public void answer() {
		UI.write("I work at the " + person.getWorkplace().getName());
	}

}

package items;

import persons.Person;
import ui.UI;

public class Fingerprint extends Item {
	private Person person;
	private boolean assigned;

	public Fingerprint(String name, Person person) {
		super(name);
		this.person = person;
		this.assigned = false;
	}

	public Person getPerson() {
		return person;
	}
	
	public void setAssigned(){
		assigned = true;
	}

	@Override
	public void inspect() {
		if(assigned)
			UI.write("This fingerpringt belongs to "+ person.getName()+".");
		else
			UI.write("An unknown fingerprint.");
	}

}

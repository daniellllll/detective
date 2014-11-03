package persons.questions;

import persons.Person;
import persons.Person.Gender;
import ui.UI;

public class RelationshipQuestion implements Question {
	private Person a, b;

	public RelationshipQuestion(Person a, Person b) {
		this.a = a;
		this.b = b;
	}

	@Override
	public void answer() {
		switch (a.getRelationship(b)) {
		case FATHER:
			UI.write("Yes, he's my father.");
			break;
		case MOTHER:
			UI.write("Yes, she's my mother.");
			break;
		case SIBLING:
			if (b.getGender() == Gender.male) {
				UI.write("Yes, he's my brother.");
			} else {
				UI.write("Yes, she's my sister.");
			}
			break;
		default:
			UI.write("I don't know this person.");
			break;
		}
	}
}

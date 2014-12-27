package places;

import java.util.ArrayList;
import java.util.List;

import main.Environment;
import persons.Person;
import places.interfaces.Residence;
import ui.UI;

public class Cabin extends Place implements Residence {
	private List<Person> residents;

	public Cabin(String name) {
		super(name);
		residents = new ArrayList<Person>();
	}

	@Override
	public Person[] getResidents() {
		return (Person[]) residents.toArray();
	}

	@Override
	public void inspect() {
		if (Environment.getActPlace() != this) {
			UI.write("It's a small cabin. The doorbell panel says \n\"Here lives");
			int count = 0;
			for (Person p : residents) {
				count++;
				if (residents.size() == 1) {
					UI.write(p.getName() + ".\"");
				} else if (count == residents.size()) {
					UI.write("and " + p.getName() + ".\"");
				} else {
					UI.write(p.getName() + ",");
				}
			}
		} else {
			super.inspect();
		}
	}

	@Override
	public void addResident(Person resident) {
		residents.add(resident);
	}

}

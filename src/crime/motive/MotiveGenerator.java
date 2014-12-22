package crime.motive;

import persons.Person;

public abstract class MotiveGenerator {
	protected Person offender;
	protected Person victim;
	
	public Person getOffender(){
		return offender;
	}
	
	public Person getVictim(){
		return victim;
	}
}
